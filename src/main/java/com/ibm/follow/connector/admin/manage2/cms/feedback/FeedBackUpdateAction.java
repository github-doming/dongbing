package com.ibm.follow.connector.admin.manage2.cms.feedback;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_sys_feedback.entity.IbmSysFeedback;
import com.ibm.follow.servlet.cloud.ibm_sys_feedback.service.IbmSysFeedbackService;
import com.ibm.follow.servlet.cloud.ibm_sys_feedback_result.entity.IbmSysFeedbackResult;
import com.ibm.follow.servlet.cloud.ibm_sys_feedback_result.service.IbmSysFeedbackResultService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 处理反馈信息
 * @Author: wwj
 * @Date: 2020/2/28 13:07
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/feedback/update", method = HttpConfig.Method.POST)
public class FeedBackUpdateAction extends CommAdminAction {


    @Override
    public Object run() throws Exception {

        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String resultMsg = StringTool.getString(dataMap.get("resultMsg"), "");
        String pk = StringTool.getString(dataMap.get("pk"), "");
        String state = StringTool.getString(dataMap.get("state"), "");
        String feedbackCode = StringTool.getString(dataMap.get("feedbackCode"), "");
        if (StringTool.isEmpty(resultMsg, pk, state, feedbackCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }
        // overrule  pass
        try {
            // 获取用户权限信息
            Map<String, Object> userRole = new AuthorityService().findUserRole(adminUserId);
            int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);

            IbmSysFeedbackResultService resultService = new IbmSysFeedbackResultService();
            String changeState ;
            //反馈提交状态 开发人员提交的状态只要修改完成
            // 操作员提交的状态（通过，驳回）需要根据该反馈当前状态进行判断
            if(permGrade<50){
                changeState = IbmStateEnum.MODIFY_FINIS.name();
            }else{
                Map<String,Object> feedbackInfo = new IbmSysFeedbackService().findByCode(feedbackCode);
                String lastState = StringTool.getString(feedbackInfo,"state","");
                changeState = getState(state,lastState);
                if(!IbmStateEnum.MODIFY_FAIL.name().equals(changeState) ){
                    new IbmSysFeedbackService().updateFeedback(pk, changeState);
                }
            }

            IbmSysFeedbackResult entity = new IbmSysFeedbackResult();
            entity.setFeedbackCode(feedbackCode);
            entity.setFeedbackResults(resultMsg);
            entity.setState(changeState);
            entity.setUpdateUser(adminUser.getUserName());
            entity.setCreateTime(new Date());
            entity.setCreateTimeLong(System.currentTimeMillis());
            resultService.save(entity);
            bean.success();
        } catch (Exception e) {
            log.error("更新反馈信息出错！", e);
            throw e;
        }
        return bean.toJsonString();

    }

    private String getState(String state,String lastState){
        String changeState = "";
        if("PASS".equals(state)){
            if(IbmStateEnum.DEF.name().equals(lastState)){
                changeState = IbmStateEnum.AUDIT_PASS.name();
            }else if(IbmStateEnum.MODIFY_FINIS.name().equals(lastState)){
                changeState = IbmStateEnum.FINISHED.name();
            }
        }else if("OVERRULE".equals(state)){
            if(IbmStateEnum.DEF.name().equals(lastState)){
                changeState = IbmStateEnum.FINISHED.name();
            }else if(IbmStateEnum.MODIFY_FINIS.name().equals(lastState)){
                changeState = IbmStateEnum.MODIFY_FAIL.name();
            }
        }

        return changeState;
    }
}

