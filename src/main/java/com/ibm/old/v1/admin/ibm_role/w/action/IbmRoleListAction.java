package com.ibm.old.v1.admin.ibm_role.w.action;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.old.v1.cloud.ibm_role.w.service.IbmRoleWService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
/**
 * @Description: 查询所有角色信息
 * @Author: zjj
 * @Date: 2019-08-13 14:12
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmRoleListAction extends BaseAppAction {
	@Override public String run() throws Exception {
		// 分页
		Integer pageIndex = BeanThreadLocal.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.find(request.getParameter(SysConfig.pageSizeName), 10);
		String roleName = BeanThreadLocal.find(request.getParameter("roleName"), "");


		IbmRoleWService roleWService = new IbmRoleWService();
		PageCoreBean pageCore = roleWService.find(roleName, pageIndex, pageSize);

		request.setAttribute("cPage", pageCore);
		request.setAttribute("list", pageCore.getList());
		request.setAttribute("roleName", roleName);

		return CommViewEnum.Default.toString();
	}
}
