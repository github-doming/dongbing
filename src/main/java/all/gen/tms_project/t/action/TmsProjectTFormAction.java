package all.gen.tms_project.t.action;

import all.gen.tms_project.t.entity.TmsProjectT;
import all.gen.tms_project.t.service.TmsProjectTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TmsProjectTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			TmsProjectTService service = new TmsProjectTService();
			TmsProjectT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
