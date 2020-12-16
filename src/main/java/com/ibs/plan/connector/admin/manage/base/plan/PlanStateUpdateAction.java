package com.ibs.plan.connector.admin.manage.base.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_plan.entity.IbspPlan;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 更新方案状态 开启/禁用
 * @Author: admin1
 * @Date: 2020/6/22 11:39
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/plan/update", method = HttpConfig.Method.POST)
public class PlanStateUpdateAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		//方案id
		String planId = StringTool.getString(dataMap, "PLAN_ID_", "");
		//状态
		String state = StringTool.getString(dataMap, "STATE_", "");
		if (StringTool.isEmpty(planId, state)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbspPlanService planService = new IbspPlanService();
			IbspPlan plan = planService.find(planId);
			if (plan == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			if (!plan.getState().equals(state)) {
				//更新状态 ibsp_plan_game,ibsp_plan_hm,ibsp_plan_user
				planService.updatePlanState(planId, state);
				plan.setState(state);
				planService.update(plan);
			}

			bean.success();
		} catch (Exception e) {
			log.error(" 更新方案状态错误", e);
			throw e;
		}
		return bean;
	}
}
