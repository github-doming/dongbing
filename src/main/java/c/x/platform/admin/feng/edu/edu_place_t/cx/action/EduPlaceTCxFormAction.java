package c.x.platform.admin.feng.edu.edu_place_t.cx.action;

import c.x.platform.admin.feng.edu.edu_place_t.cx.entity.EduPlaceTCx;
import c.x.platform.admin.feng.edu.edu_place_t.cx.service.EduPlaceTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduPlaceTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduPlaceTCxService service = new EduPlaceTCxService();
			EduPlaceTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
