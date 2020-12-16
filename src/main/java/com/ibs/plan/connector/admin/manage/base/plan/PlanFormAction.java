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
 * @Description: 方案表单
 * @Author: admin1
 * @Date: 2020/6/20 14:00
 * @Version: v1.0
 */

@ActionMapping(value = "/ibs/sys/manage/plan/form",method = HttpConfig.Method.GET)
public class PlanFormAction extends CommAdminAction {
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
		if(StringTool.isEmpty(planId)){
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbspPlan plan = new IbspPlanService().find(planId);
			if (plan == null) {
				bean.putEnum(CodeEnum.IBS_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			bean.setData(plan);
			bean.success();
		} catch (Exception e) {
			log.error("获取盘口修改表单页面信息错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
