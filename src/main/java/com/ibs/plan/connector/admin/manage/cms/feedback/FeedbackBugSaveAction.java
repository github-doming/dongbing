package com.ibs.plan.connector.admin.manage.cms.feedback;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_sys_feedback.entity.IbspSysFeedback;
import com.ibs.plan.module.cloud.ibsp_sys_feedback.service.IbspSysFeedbackService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Description: 保存BUG反馈信息
 * @Author: admin1
 * @Date: 2020/3/30 13:38
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/manage/feedback/bug/save", method = HttpConfig.Method.POST)
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
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			String feedbackCode = getFeedbackCode();
			IbspSysFeedback entity = new IbspSysFeedback();
			entity.setFeedbackCode(feedbackCode);
			entity.setFeedbackInfo(feedbackInfo);
			entity.setFeedbackTitle(getTitle(feedbackType, feedbackInfo));
			entity.setFeedbackType("BUG");
			entity.setUserName(appUserId);
			entity.setCreateTime(new Date());
			entity.setCreateTimeLong(System.currentTimeMillis());
			entity.setState(IbsStateEnum.DEF.getMsg());
			new IbspSysFeedbackService().save(entity);

			Map<String, String> codeMSg = new HashMap<>(1);
			codeMSg.put("feedbackCode", feedbackCode);
			bean.success(codeMSg);
		} catch (Exception e) {
			log.error(" 保存BUG反馈错误", e);
			throw e;
		}
		return bean;
	}

	private String getFeedbackCode() {
		String code = String.valueOf(System.currentTimeMillis());
		return code.substring(0, 10) + new Random().nextInt(10);
	}

	private String getTitle(String feedbackType, String info) {
		if (StringTool.notEmpty(feedbackType)) {
			return feedbackType;
		}
		if (info.length() <= 20) {
			return info;
		}
		return info.substring(0, 20);
	}

}

