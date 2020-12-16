package all.task.tms.project_user.app_user.action;
import java.util.List;
import java.util.Map;

import all.task.tms.project_user.app_user.service.AppUserProjectUserService;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import  c.x.platform.root.common.action.BaseAction;
public class AppUserProjectUserListAction  extends BaseAction {
	@Override
	public String execute() throws Exception {
		// 树节点id
		// {
		// first$tree$id
		String first$tree$id = (String) request.getParameter("first$tree$id");
		// log.trace("first$tree$id="+first$tree$id);
		if (false) {
			String tms_project$parent = request
					.getParameter("tms_project.parent");
			// log.trace("tms_project$parent="+tms_project$parent);
		}
		if (StringUtil.isNotBlank(first$tree$id)) {
			String[] array_first$tree$id = first$tree$id.split("_");
			if (array_first$tree$id.length > 1) {
				// 从树节点加载list列表
				first$tree$id = array_first$tree$id[2];
			} else {
				// 从form表单退回list列表
			}
		} else {
			// 从form表单提交或编辑保存到list列表，或删除到list列表
			first$tree$id = (String) request.getAttribute("first$tree$id");
		}
		// log.trace("first$tree$id="+first$tree$id);
		request.setAttribute("value_first$tree$id", first$tree$id);
		// }
		// 树节点id
		// 排序
		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request
				.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue,sortFieldName);
		// 分页
		Integer pageIndex = BeanThreadLocal.find(request,SysConfig.pageIndexName, 1);
		Integer pageSize = BeanThreadLocal.find(request,SysConfig.pageSizeName, 10);
		AppUserProjectUserService service = new AppUserProjectUserService();
		PageCoreBean<Map<String, Object>> basePage = null;
		if (StringUtil.isNotBlank(first$tree$id)) {
			basePage = service.find(first$tree$id, sortFieldName,
					sortOrderName, pageIndex, pageSize);
		} else {
			basePage = service.find(sortFieldName, sortOrderName, pageIndex, pageSize);
		}
		List<Map<String, Object>> listMap = basePage.getList();
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", listMap);
		return CommViewEnum.Default.toString();
	}
}
