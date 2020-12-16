package com.ibs.plan.connector.admin.manage.cms.feedback;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import com.ibs.plan.module.cloud.ibsp_sys_feedback.service.IbspSysFeedbackService;
import com.ibs.plan.module.cloud.ibsp_sys_feedback_result.entity.IbspSysFeedbackResult;
import com.ibs.plan.module.cloud.ibsp_sys_feedback_result.service.IbspSysFeedbackResultService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 处理反馈信息
 * @Author: admin1
 * @Date: 2020/2/28 13:07
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/feedback/update", method = HttpConfig.Method.POST)
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
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		// overrule  pass
		try {
			// 获取用户权限信息
			Map<String, Object> userRole = new AuthorityService().findUserRole(appUserId);
			int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);

			IbspSysFeedbackResultService resultService = new IbspSysFeedbackResultService();
			String changeState;
			//反馈提交状态 开发人员提交的状态只要修改完成
			// 操作员提交的状态（通过，驳回）需要根据该反馈当前状态进行判断
			if (permGrade < 50) {
				changeState = IbsStateEnum.MODIFY_FINIS.name();
			} else {
				Map<String, Object> feedbackInfo = new IbspSysFeedbackService().findByCode(feedbackCode);
				String lastState = StringTool.getString(feedbackInfo, "state", "");
				changeState = getState(state, lastState);
				if (!IbsStateEnum.MODIFY_FAIL.name().equals(changeState)) {
					new IbspSysFeedbackService().updateFeedback(pk, changeState);
				}
			}

			IbspSysFeedbackResult entity = new IbspSysFeedbackResult();
			entity.setFeedbackCode(feedbackCode);
			entity.setFeedbackResults(resultMsg);
			entity.setState(changeState);
			entity.setUpdateUser(appUserId);
			entity.setCreateTime(new Date());
			entity.setCreateTimeLong(System.currentTimeMillis());
			resultService.save(entity);
			bean.success();
		} catch (Exception e) {
			log.error("更新反馈信息出错！", e);
			throw e;
		}
		return bean;

	}

	private String getState(String state, String lastState) {
		String changeState = "";
		if ("PASS".equals(state)) {
			if (IbsStateEnum.DEF.name().equals(lastState)) {
				changeState = IbsStateEnum.AUDIT_PASS.name();
			} else if (IbsStateEnum.MODIFY_FINIS.name().equals(lastState)) {
				changeState = IbsStateEnum.FINISHED.name();
			}
		} else if ("OVERRULE".equals(state)) {
			if (IbsStateEnum.DEF.name().equals(lastState)) {
				changeState = IbsStateEnum.FINISHED.name();
			} else if (IbsStateEnum.MODIFY_FINIS.name().equals(lastState)) {
				changeState = IbsStateEnum.MODIFY_FAIL.name();
			}
		}

		return changeState;
	}
}

