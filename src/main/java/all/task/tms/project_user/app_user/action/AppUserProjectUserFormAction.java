package  all.task.tms.project_user.app_user.action;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;
import c.a.util.core.enums.bean.CommViewEnum;
public class AppUserProjectUserFormAction  extends BaseAction {
	@Override
	public String execute() throws Exception {
		all.task.tms.project_user.app_user.service.AppUserProjectUserService sAppUserProjectUserService = new all.task.tms.project_user.app_user.service.AppUserProjectUserService();
		all.task.tms.project_user.tms_project.service.TmsProjectProjectUserService fTmsProjectProjectUserService = new all.task.tms.project_user.tms_project.service.TmsProjectProjectUserService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		// log.trace("id=" + id);
		if (StringUtil.isNotBlank(id)) {
			// 本身
			all.task.tms.project_user.app_user.entity.AppUserProjectUser s = sAppUserProjectUserService.find(id);
			request.setAttribute("s", s);
		}
		// 树节点id
		// {
		// first$tree$id
		String first$tree$id = (String) request.getParameter("first$tree$id");
		request.setAttribute("value_first$tree$id", first$tree$id);
		// 选择上一级菜单
		if (StringUtil.isBlank(first$tree$id)) {
		} else {
			first$tree$id = first$tree$id.trim();
			all.task.tms.project_user.tms_project.entity.TmsProjectProjectUser p = fTmsProjectProjectUserService.find(first$tree$id);
			request.setAttribute("p", p);
			// 树名称
			request.setAttribute("value_first$tree$name", p.getProjectName());
		}
		// }
		// 树节点id
		return CommViewEnum.Default.toString();
	}
}
