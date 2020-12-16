package all.task.tms.tms_project.t.action;

import all.task.tms.tms_project.t.entity.TmsProjectT;
import all.task.tms.tms_project.t.service.TmsProjectTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsProjectTSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsProjectTService service = new TmsProjectTService();
		String parent_id = request.getParameter("parent_id");
		TmsProjectT s = service.find(parent_id);
		request.setAttribute("s", s);
		return CommViewEnum.Default.toString();
	}
}
