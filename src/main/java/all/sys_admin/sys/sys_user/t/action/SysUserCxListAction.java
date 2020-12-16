package all.sys_admin.sys.sys_user.t.action;

import java.util.List;
import java.util.Map;

import all.sys_admin.sys.sys_user.t.service.SysUserService;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.BaseRoleAction;
import c.x.platform.root.login_not.current.CurrentSysUser;
public class SysUserCxListAction extends BaseRoleAction {

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

		SysUserService service = new SysUserService();
		CurrentSysUser currentUser = this.findCurrentSysUser();
		PageCoreBean<Map<String, Object>> pageBean = service.find(currentUser, sortOrderName, sortFieldName, pageNo,
				pageSize);

		List<Map<String, Object>> listMap = pageBean.getList();

		request.setAttribute("cPage", pageBean);
		request.setAttribute("list", listMap);

		return "index";
	}
}
