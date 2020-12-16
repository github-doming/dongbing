package com.ibm.old.v1.admin.ibm_plan.w.action;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
/**
 * @Description: 查询所有方案
 * @Author: zjj
 * @Date: 2019-08-13 13:45
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmPlanListAction extends BaseAppAction {
	@Override public String run() throws Exception {
		// 分页
		Integer pageIndex = BeanThreadLocal.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.find(request.getParameter(SysConfig.pageSizeName), 10);
		String planName = BeanThreadLocal.findThreadLocal().get().find(request.getParameter("planName"), "");

		IbmPlanTService planTService = new IbmPlanTService();
		PageCoreBean pageCore = planTService.find(planName,pageIndex, pageSize);

		request.setAttribute("cPage", pageCore);
		request.setAttribute("list", pageCore.getList());
		request.setAttribute("planName", planName);

		return CommViewEnum.Default.toString();
	}
}
