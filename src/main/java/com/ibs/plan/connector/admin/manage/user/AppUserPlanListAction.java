package com.ibs.plan.connector.admin.manage.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 获取方案列表
 * @Author: admin1
 * @Date: 2020/6/16 17:01
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/plan/list")
public class AppUserPlanListAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserId = StringTool.getString(dataMap ,"appUserId", "");
		try {
			IbspPlanService planService = new IbspPlanService();
			List<Map<String, Object>> planInfo = planService.findPlanList();
			if (StringTool.notEmpty(appUserId)) {
				String planCodes=new IbspExpUserService().findAvailablePlan(appUserId);
				List<String> userPlan=Arrays.asList(planCodes.split(","));
				for (Map<String, Object> plan : planInfo) {
					plan.put("hasPlan",false);
					if(userPlan.contains(plan.get("PLAN_CODE_").toString())){
						plan.put("hasPlan",true);
					}
				}
			}
			bean.success(planInfo);
		} catch (Exception e) {
			log.error("获取计划列表", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
