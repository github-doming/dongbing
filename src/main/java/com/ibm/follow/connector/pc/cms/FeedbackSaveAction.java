package com.ibm.follow.connector.pc.cms;

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
 * @Description: 保存用户反馈信息
 * @Author: wwj
 * @Date: 2020/3/30 13:38
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/feedback/users/save")
public class FeedbackSaveAction extends CommCoreAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String feedbackType = StringTool.getString(dataMap,"feedbackType", "");
		String feedbackInfo = StringTool.getString(dataMap,"feedbackInfo", "");
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
			entity.setFeedbackType("USERS");
			entity.setUserName(appUser.getNickname());
			entity.setCreateTime(new Date());
			entity.setCreateTimeLong(System.currentTimeMillis());
			entity.setState(IbmStateEnum.DEF.name());
			new IbmSysFeedbackService().save(entity);

			Map<String, String> codeMsg = new HashMap<>(1);
			codeMsg.put("feedbackCode", feedbackCode);
			bean.success(codeMsg);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("保存用户反馈错误"), e);
			bean.error(e.getMessage());
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

