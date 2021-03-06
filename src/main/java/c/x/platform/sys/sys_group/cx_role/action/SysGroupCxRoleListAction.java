package c.x.platform.sys.sys_group.cx_role.action;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import all.gen.sys_group.t.entity.SysGroupT;
import c.x.platform.sys.sys_group.cx_role.service.SysGroupCxRoleService;
import c.x.platform.root.common.action.BaseRoleAction;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.bean.BeanThreadLocal;
public class SysGroupCxRoleListAction extends BaseRoleAction {

	@Override
	public String execute() throws Exception {

		// 排序

		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request
				.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);

		// 分页
		Integer pageNo = BeanThreadLocal.findThreadLocal().get().find(
				request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(
				request.getParameter(SysConfig.pageSizeName), 10);

		SysGroupCxRoleService service = new SysGroupCxRoleService();
		PageCoreBean<Map<String, Object>> pageBean = service.find(sortOrderName,
				sortFieldName, pageNo, pageSize);

		List<Map<String, Object>> listMap = pageBean.getList();

		request.setAttribute("cPage", pageBean);
		request.setAttribute("list", listMap);

		return "index";
	}
}
