package com.ibm.follow.connector.admin.manage2.cms.feedback;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_feedback.entity.IbmSysFeedback;
import com.ibm.follow.servlet.cloud.ibm_sys_feedback.service.IbmSysFeedbackService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Description: 保存BUG反馈信息
 * @Author: wwj
 * @Date: 2020/3/30 13:38
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/feedback/bug/save", method = HttpConfig.Method.POST)
public class FeedbackBugSaveAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }


        String feedbackType = dataMap.getOrDefault("feedbackType", "").toString();
        String feedbackInfo = dataMap.getOrDefault("feedbackInfo", "").toString();
        if (StringTool.isEmpty(feedbackInfo)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            String feedbackCode = getFeedbackCode();
            IbmSysFeedback entity = new IbmSysFeedback();
            entity.setFeedbackCode(feedbackCode);
            entity.setFeedbackInfo(feedbackInfo);
            entity.setFeedbackTitle(getTitle(feedbackType,feedbackInfo));
            entity.setFeedbackType("BUG");
            entity.setUserName(appUser.getNickname());
            entity.setCreateTime(new Date());
            entity.setCreateTimeLong(System.currentTimeMillis());
            entity.setState(IbmStateEnum.DEF.getMsg());
            new IbmSysFeedbackService().save(entity);

            Map<String, String> codeMSg = new HashMap<>(1);
            codeMSg.put("feedbackCode", feedbackCode);
            bean.success(codeMSg);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("保存BUG反馈错误"), e);
            throw e;
        }
        return bean.toJsonString();
    }

    private String getFeedbackCode() {
        String code = String.valueOf(System.currentTimeMillis());
        return code.substring(0, 10) + new Random().nextInt(10);
    }

    private String getTitle(String feedbackType,String info) {
        if(StringTool.notEmpty(feedbackType)){
            return feedbackType;
        }
        if (info.length() <= 25) {
            return info;
        }
        return info.substring(0, 25);
    }

}

