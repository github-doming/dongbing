package all.task.tms.project_user.app_user.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import c.a.config.SysConfig;
import all.task.tms.project_user.app_user.service.AppUserProjectUserService;
public class AppUserProjectUserListJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String treeId = (String) request.getParameter("treeId");
		Map<String, Object> map = new HashMap<String, Object>();
		// 分页
		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		String sortField = BeanThreadLocal.find(request, "sortField", "");
		String sortOrder = BeanThreadLocal.find(request, "sortOrder", "");
		request.setAttribute("sortField", sortField);
		request.setAttribute("sortOrder", sortOrder);
		Integer pageIndex = BeanThreadLocal.find(request,"page", 1);
		Integer pageSize = BeanThreadLocal.find(request,"rows", 10);
		AppUserProjectUserService service = new AppUserProjectUserService();
		PageCoreBean<Map<String, Object>> basePage = null;
		if (StringUtil.isNotBlank(treeId)) {
			basePage = service.find(treeId, sortFieldName, sortOrderName, pageIndex, pageSize);
		} else {
			basePage = service.find(sortFieldName, sortOrderName, pageIndex, pageSize);
		}
		List<Map<String, Object>> listMap = basePage.getList();
		// map.put("data", listMap);
		map.put("rows", listMap);
		map.put("total", basePage.getTotalCount());
		return this.returnJson(map);
	}
}
