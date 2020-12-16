package com.ibs.plan.connector.admin.manage.base.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_plan.entity.IbspPlan;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 方案修改
 * @Author: admin1
 * @Date: 2020/6/20 14:10
 * @Version: v1.0
 */

@ActionMapping(value = "/ibs/sys/manage/plan/edit", method = HttpConfig.Method.POST)
public class PlanEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口id
		String planId = StringTool.getString(dataMap, "PLAN_ID_", "");
		//方案类型
		String planType = StringTool.getString(dataMap, "PLAN_TYPE_", "");
		//方案价值
		double planWorth = NumberTool.getDouble(dataMap.get("PLAN_WORTH_"), -1);
		//方案表名
		String planTableName = StringTool.getString(dataMap, "planTableName", "");
		//序号
		int sn = NumberTool.getInteger(dataMap.get("SN_"), -1);
		//备注
		String desc = StringTool.getString(dataMap, "DESC_", "");
		//方案状态
		String state = StringTool.getString(dataMap, "STATE_", "");
		if (StringTool.isEmpty(planId, planType,planWorth,planTableName,sn,state,desc)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}

		try {
			IbspPlanService planService = new IbspPlanService();
			IbspPlan plan = planService.find(planId);
			if (plan == null) {
				bean.putEnum(CodeEnum.IBS_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			// TODO 方案类型 更新对应用户 免费/付费
			if (StringTool.notEmpty(planType)&& !plan.getPlanType().equals(NumberTool.getInteger(planType))) {
				plan.setPlanType(planType);
			}
			plan.setPlanWorthT(planWorth);
			plan.setPlanItemTableName(planTableName);
			plan.setDesc(desc);
			plan.setSn(sn);
			plan.setUpdateTimeLong(System.currentTimeMillis());
			if (!plan.getState().equals(state)) {
				//更新状态 ibsp_plan_game,ibsp_plan_hm,ibsp_plan_user
				planService.updatePlanState(planId, state);
				plan.setState(state);
			}
			planService.update(plan);

			bean.success();
		} catch (Exception e) {
			log.error(" 方案修改错误", e);
			throw e;
		}
		return bean;
	}


}
