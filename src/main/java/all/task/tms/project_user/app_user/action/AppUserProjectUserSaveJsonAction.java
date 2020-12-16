package all.task.tms.project_user.app_user.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.task.tms.project_user.app_user.entity.AppUserProjectUser;
import all.task.tms.project_user.app_user.service.AppUserProjectUserService;
import all.task.tms.project_user.app_user.vo.AppUserProjectUserVo;
public class AppUserProjectUserSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppUserProjectUserService service = new AppUserProjectUserService();
		AppUserProjectUser entity = null;
		String id = request.getParameter("id");
		entity = (AppUserProjectUser) RequestThreadLocal.doRequest2EntityByJson(AppUserProjectUserVo.class, AppUserProjectUser.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
