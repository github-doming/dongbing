package com.ibm.old.v1.admin.app_user.w.action;

import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseRoleAction;

/**
 * 
 * 
 * @Description: 查询所有用户信息
 * @date 2019年2月20日 下午2:50:52 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class AppUserWListAction extends BaseRoleAction{

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
		String userName = BeanThreadLocal.findThreadLocal().get().find(request.getParameter("userName"), "");

//		AppUserService service = new AppUserService();
//		PageCoreBean pageCore = service.find(userName, sortFieldName, sortOrderName, pageNo, pageSize);
//
//		List<Map<String, Object>> listMap = pageCore.getList();
//
//		request.setAttribute("cPage", pageCore);
//		request.setAttribute("list", listMap);
//		request.setAttribute("userName", userName);

		return CommViewEnum.Default.toString();
	}

}
