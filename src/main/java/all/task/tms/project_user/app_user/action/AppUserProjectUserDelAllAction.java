package all.task.tms.project_user.app_user.action;

import all.task.tms.project_user.app_user.service.AppUserProjectUserService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class AppUserProjectUserDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		AppUserProjectUserService service = new AppUserProjectUserService();
		service.delAll(ids);
			return CommViewEnum.Default.toString();
	}
}
