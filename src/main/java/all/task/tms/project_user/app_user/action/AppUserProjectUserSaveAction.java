package all.task.tms.project_user.app_user.action;
import all.task.tms.project_user.app_user.entity.AppUserProjectUser;
import all.task.tms.project_user.app_user.service.AppUserProjectUserService;
import all.task.tms.project_user.app_user.vo.AppUserProjectUserVo;
import all.task.tms.project_user.tms_project_user.entity.TmsProjectUserProjectUser;
import all.task.tms.project_user.tms_project_user.service.TmsProjectUserProjectUserService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class AppUserProjectUserSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		// String id = request.getParameter("app_user.id");
		// String id = request.getParameter("appUserId");
		// String id = request.getParameter("id");
		String id = request.getParameter("app_user.appUserId");
		AppUserProjectUser entity = (AppUserProjectUser) RequestThreadLocal.doRequest2Entity(AppUserProjectUserVo.class,
				AppUserProjectUser.class, request);
		AppUserProjectUserService service = new AppUserProjectUserService();
		// 第3表
		// {
		TmsProjectUserProjectUserService tTmsProjectUserProjectUserService = new all.task.tms.project_user.tms_project_user.service.TmsProjectUserProjectUserService();
		TmsProjectUserProjectUser tTmsProjectUserProjectUser = new all.task.tms.project_user.tms_project_user.entity.TmsProjectUserProjectUser();
		// 树的老的ID
		String name_first$tree$id = request.getParameter("name_first$tree$id");
		// 树的新的ID
		String tms_project$parent = request.getParameter("tms_project.parent");
		request.setAttribute("first$tree$id", tms_project$parent);
		// }
		// 第3表
		if (StringUtil.isBlank(id)) {
			id = service.save(entity);
			// 第3表保存
			tTmsProjectUserProjectUser.setTmsProjectId(tms_project$parent);
			tTmsProjectUserProjectUser.setUserId(id);
			tTmsProjectUserProjectUser.setState(TaskStateEnum.OPEN.toString());
			tTmsProjectUserProjectUserService.save(tTmsProjectUserProjectUser);
		} else {
			//service.update(entity);
			// 第3表删除
			tTmsProjectUserProjectUserService.del(name_first$tree$id, id);
			// 第3表保存
			tTmsProjectUserProjectUser.setTmsProjectId(tms_project$parent);
			tTmsProjectUserProjectUser.setUserId(id);
			tTmsProjectUserProjectUser.setState(TaskStateEnum.OPEN.toString());
			tTmsProjectUserProjectUserService.save(tTmsProjectUserProjectUser);
		}
		return CommViewEnum.Default.toString();
	}
}
