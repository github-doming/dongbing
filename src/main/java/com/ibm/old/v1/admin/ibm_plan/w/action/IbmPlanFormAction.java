package com.ibm.old.v1.admin.ibm_plan.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_plan.t.entity.IbmPlanT;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import org.doming.core.tools.StringTool;
/**
 * @Description: 新增页面
 * @Author: zjj
 * @Date: 2019-08-13 13:50
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmPlanFormAction extends BaseAppAction {
	@Override public String run() throws Exception {
		String id = request.getParameter("id");
		if (StringTool.isEmpty(id)) {
			return CommViewEnum.Default.toString();
		}
		IbmPlanTService planTService = new IbmPlanTService();
		IbmPlanT plan = planTService.find(id);
		request.setAttribute("s", plan);
		request.setAttribute("id", id);
		return CommViewEnum.Default.toString();
	}
}
