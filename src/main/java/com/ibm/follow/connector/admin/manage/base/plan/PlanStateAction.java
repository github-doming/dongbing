package com.ibm.follow.connector.admin.manage.base.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.vr_plan.entity.VrPlan;
import com.ibm.follow.servlet.cloud.vr_plan.service.VrPlanService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 更新方案状态 开启/禁用
 * @Author: admin1
 * @Date: 2020/6/22 11:39
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/plan/state", method = HttpConfig.Method.POST)
public class PlanStateAction extends CommAdminAction {

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
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			VrPlanService planService = new VrPlanService();
			VrPlan plan = planService.find(planId);
			if (plan == null) {
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//更新状态 vr_plan_game,vr_plan_hm,
			planService.updatePlanState(planId, state);
			plan.setState(state);
			planService.update(plan);

			bean.success();
		} catch (Exception e) {
			log.error(" 更新方案状态错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
