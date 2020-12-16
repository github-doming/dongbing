package com.ibm.old.v1.admin.app_user.w.action;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
/**
 * @Description: 查询所有用户
 * @Author: zjj
 * @Date: 2019-08-13 14:31
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppUserListAction extends BaseAppAction {
	@Override public String run() throws Exception {

		// 分页
		Integer pageIndex = BeanThreadLocal.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.find(request.getParameter(SysConfig.pageSizeName), 10);
		String userName = BeanThreadLocal.find(request.getParameter("userName"), "");

		AppUserService service = new AppUserService();
		PageCoreBean pageCore = service.find(userName,pageIndex, pageSize);

		request.setAttribute("cPage", pageCore);
		request.setAttribute("list", pageCore.getList());
		request.setAttribute("userName", userName);

		return CommViewEnum.Default.toString();
	}
}
