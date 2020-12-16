package com.ibs.plan.connector.admin.manage.cms.feedback;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import com.ibs.plan.module.cloud.ibsp_sys_feedback.service.IbspSysFeedbackService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 查询用户反馈
 * @Author: admin1
 * @Date: 2020/2/28 13:07
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/feedback/list")
public class FeedbackListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		// 反馈类型  - BUG -用户反馈（USERS）
		String feedbackType = StringTool.getString(dataMap.get("feedbackType"), "");
		// 标题
		String feedbackTitle = StringTool.getString(dataMap.get("feedbackTitle"), "");
		// 状态
		String state = StringTool.getString(dataMap.get("state"), "");
		// 反馈编码
		String feedbackCode = StringTool.getString(dataMap.get("feedbackCode"), "");
		// 反馈人
		String feedbackUser = StringTool.getString(dataMap.get("feedbackUser"), "");
		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);

		long timeStart = NumberTool.getLong(dataMap.get("timeStart"), 0L);
		long timeEnd = NumberTool.getLong(dataMap.get("timeEnd"), 0L);
		Long startTime = null, endTime = null;
		if (timeStart != 0) {
			startTime = DateTool.getDayStart(new Date(timeStart)).getTime();
			if (timeEnd == 0) {
				timeEnd = System.currentTimeMillis();
			}
			endTime = DateTool.getDayEnd(new Date(timeEnd)).getTime();
		}
		Map<String, Object> data = new HashMap<>(3);
		try {
			// 获取用户权限信息
			Map<String, Object> userRole = new AuthorityService().findUserRole(appUserId);
			int permGrade = NumberTool.getInteger(userRole, "PERMISSION_GRADE_", 200);
			if (permGrade > 80) {
				bean.putEnum(CodeEnum.IBS_403_PERMISSION);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			//50以下开发  50 - 80 操作员
			// 开发者进来看到的是操作员已审核过的反馈信息,(DEF、FAILED)则两个状态不可见
			String queryRange = "";
			if (permGrade < 50) {
				queryRange = "PART";
			}

			PageCoreBean<Map<String, Object>> pageDate = new IbspSysFeedbackService().findUserFeedbackInfo(feedbackType, feedbackTitle,
					feedbackCode, feedbackUser, state, queryRange, startTime, endTime, pageIndex, pageSize);

			data.put("rows", pageDate.getList());
			data.put("index", pageIndex);
			data.put("total", pageDate.getTotalCount());
		} catch (Exception e) {
			log.error("查询BUG反馈信息出错！", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;

	}
}

