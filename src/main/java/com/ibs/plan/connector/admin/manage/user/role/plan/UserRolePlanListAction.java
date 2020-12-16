package com.ibs.plan.connector.admin.manage.user.role.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 角色方案列表
 * @Author: admin1
 * @Date: 2020/6/22 15:50
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/role/plan/list")
public class UserRolePlanListAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String roleId = StringTool.getString(dataMap, "roleId", "");
		try {
			IbspExpRoleService expRoleService = new IbspExpRoleService();
			IbspExpRole expRole = expRoleService.find(roleId);
			if (expRole == null) {
				bean.putEnum(CodeEnum.IBS_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			IbspPlanService planService = new IbspPlanService();
			List<Map<String, Object>> planInfo = planService.findPlanList();
			if (StringTool.notEmpty(roleId)) {
				for (Map<String, Object> plan : planInfo) {
					if (StringTool.isEmpty(expRole.getPlanCodes())) {
						plan.put("hasPlan", false);
						continue;
					}
					if (expRole.getPlanCodes().contains(plan.get("PLAN_CODE_").toString())) {
						plan.put("hasPlan", true);
					} else {
						plan.put("hasPlan", false);
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
