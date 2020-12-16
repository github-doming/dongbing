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
 * @Description: 方案表单
 * @Author: admin1
 * @Date: 2020/6/20 14:00
 * @Version: v1.0
 */

@ActionMapping(value = "/ibm/admin/manage/plan/form",method = HttpConfig.Method.GET)
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
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			VrPlan plan = new VrPlanService().find(planId);
			if (plan == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
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
