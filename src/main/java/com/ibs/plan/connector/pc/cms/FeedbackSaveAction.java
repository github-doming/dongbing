package com.ibs.plan.connector.pc.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_sys_feedback.entity.IbspSysFeedback;
import com.ibs.plan.module.cloud.ibsp_sys_feedback.service.IbspSysFeedbackService;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

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
		IbspUser user = super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String feedbackType = StringTool.getString(dataMap,"feedbackType", "");
		String feedbackInfo = StringTool.getString(dataMap,"feedbackInfo", "");
		if (StringTool.isEmpty(feedbackInfo)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {

			String feedbackCode = getFeedbackCode();
			IbspSysFeedback entity = new IbspSysFeedback();
			entity.setFeedbackCode(feedbackCode);
			entity.setFeedbackInfo(feedbackInfo);
			entity.setFeedbackTitle(getTitle(feedbackType,feedbackInfo));
			entity.setFeedbackType("USERS");
			entity.setUserName(user.getNickname());
			entity.setCreateTime(new Date());
			entity.setCreateTimeLong(System.currentTimeMillis());
			entity.setState(IbsStateEnum.DEF.name());
			new IbspSysFeedbackService().save(entity);

			Map<String, String> codeMsg = new HashMap<>(1);
			codeMsg.put("feedbackCode", feedbackCode);
			bean.success(codeMsg);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN.concat("保存用户反馈错误"), e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
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

