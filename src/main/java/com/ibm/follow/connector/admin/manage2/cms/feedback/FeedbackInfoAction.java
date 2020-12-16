package com.ibm.follow.connector.admin.manage2.cms.feedback;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_feedback.service.IbmSysFeedbackService;
import com.ibm.follow.servlet.cloud.ibm_sys_feedback_result.service.IbmSysFeedbackResultService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 查看用户反馈
 * @Author: wwj
 * @Date: 2020/2/28 13:07
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/feedback/info")
public class FeedbackInfoAction extends CommAdminAction {


    @Override
    public Object run() throws Exception {

        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        String feedbackCode = StringTool.getString(dataMap.get("feedbackCode"), "").trim();
        if (StringTool.isEmpty(feedbackCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean.toJsonString();
        }

        try {
            Map<String, Object> feedBackInfo = new IbmSysFeedbackService().findByCode(feedbackCode);
            feedBackInfo.put("stateCh",changeState(feedBackInfo.get("state").toString()));

            List<Map<String, Object>> feedBackResults = new IbmSysFeedbackResultService().findByCode(feedbackCode);
            for (Map<String,Object> map : feedBackResults){
               map.put("stateCh",changeState(map.get("state").toString()));
            }
            Map<String, Object> data = new HashMap<>(2);
            data.put("feedBackInfo", feedBackInfo);
            data.put("feedBackResults", feedBackResults);
            bean.success(data);

        } catch (Exception e) {
            log.error("根据编码查询反馈信息出错！", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

    private String changeState(String state){
        switch (state){
            case "AUDIT_PASS" : return "修改中";
            case "MODIFY_FINIS" : return IbmStateEnum.MODIFY_FINIS.getMsg();
            case "MODIFY_FAIL" : return IbmStateEnum.MODIFY_FAIL.getMsg();
            case "FINISHED" : return IbmStateEnum.FINISHED.getMsg();
            default:  return "未审核";

        }
    }
}

