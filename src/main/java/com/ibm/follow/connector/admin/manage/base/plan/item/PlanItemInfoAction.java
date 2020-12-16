package com.ibm.follow.connector.admin.manage.base.plan.item;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.vr_plan.entity.VrPlan;
import com.ibm.follow.servlet.cloud.vr_plan.service.VrPlanService;
import com.ibm.follow.servlet.cloud.vr_plan_item.service.VrPlanItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 方案详情信息
 * @Author: admin1
 * @Date: 2020/6/22 13:57
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/plan/item/info",method = HttpConfig.Method.GET)
public class PlanItemInfoAction extends CommAdminAction {

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
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			 Map<String,Object> planItemInfo =new VrPlanItemService().findPlanItemInfo(plan.getPlanCode());

			bean.setData(planItemInfo);
			bean.success();
		} catch (Exception e) {
			log.error("获取方案详情信息错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
