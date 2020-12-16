package com.ibm.old.v1.admin.ibm_plan_user.w.action;

import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.BaseAction;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;

import java.util.List;

/**
 * 
 * 
 * @Description: 查询所有盘口用户
 * @date 2019年4月9日15:59:33
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmPlanUserWListAction extends BaseAction{

	@Override
	public String execute() throws Exception {
		// 排序
		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);

		// 分页
		Integer pageNo = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				10);
		String planName = BeanThreadLocal.findThreadLocal().get().find(request.getParameter("PLAN_NAME_"), "");
		
		IbmPlanUserTService planTService = new IbmPlanUserTService();
		PageCoreBean pageCore = planTService.find(planName,sortFieldName, sortOrderName, pageNo, pageSize);

		List listMap = pageCore.getList();

		request.setAttribute("cPage", pageCore);
		request.setAttribute("list", listMap);
		request.setAttribute("planName", planName);

		return CommViewEnum.Default.toString();
	}

}
