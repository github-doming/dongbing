package com.ibs.plan.connector.admin.manage.cms.feedback;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_sys_feedback.service.IbspSysFeedbackService;
import com.ibs.plan.module.cloud.ibsp_sys_feedback_result.service.IbspSysFeedbackResultService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 查看用户反馈
 * @Author: admin1
 * @Date: 2020/2/28 13:07
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/feedback/info", method = HttpConfig.Method.GET)
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
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}

		try {
			Map<String, Object> feedBackInfo = new IbspSysFeedbackService().findByCode(feedbackCode);
			feedBackInfo.put("stateCh", changeState(feedBackInfo.get("state").toString()));

			List<Map<String, Object>> feedBackResults = new IbspSysFeedbackResultService().findByCode(feedbackCode);
			for (Map<String, Object> map : feedBackResults) {
				map.put("stateCh", changeState(map.get("state").toString()));
			}
			Map<String, Object> data = new HashMap<>(2);
			data.put("feedBackInfo", feedBackInfo);
			data.put("feedBackResults", feedBackResults);
			bean.success(data);

		} catch (Exception e) {
			log.error("根据编码查询反馈信息出错！", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private String changeState(String state) {
		switch (state) {
			case "AUDIT_PASS":
				return "修改中";
			case "MODIFY_FINIS":
				return "修改完成";
			case "MODIFY_FAIL":
				return "修改未通过";
			case "FINISHED":
				return "完成";
			default:
				return "未审核";

		}
	}
}

