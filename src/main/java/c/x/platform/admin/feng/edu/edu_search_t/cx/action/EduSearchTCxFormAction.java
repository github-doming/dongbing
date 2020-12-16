package c.x.platform.admin.feng.edu.edu_search_t.cx.action;

import c.x.platform.admin.feng.edu.edu_search_t.cx.entity.EduSearchTCx;
import c.x.platform.admin.feng.edu.edu_search_t.cx.service.EduSearchTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduSearchTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduSearchTCxService service = new EduSearchTCxService();
			EduSearchTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
