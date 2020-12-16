package all.task.tms.project_user.tms_project.action;

import all.task.tms.project_user.tms_project.entity.TmsProjectProjectUser;
import all.task.tms.project_user.tms_project.service.TmsProjectProjectUserService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsProjectProjectUserSelectAction  extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsProjectProjectUserService service = new TmsProjectProjectUserService();
		String parent_id = request.getParameter("parent_id");
		TmsProjectProjectUser s = service.find(parent_id);
		request.setAttribute("s", s);
		return CommViewEnum.Default.toString();
	}
}
