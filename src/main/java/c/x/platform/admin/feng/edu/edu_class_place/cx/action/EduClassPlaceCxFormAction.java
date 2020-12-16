package c.x.platform.admin.feng.edu.edu_class_place.cx.action;

import c.x.platform.admin.feng.edu.edu_class_place.cx.entity.EduClassPlaceCx;
import c.x.platform.admin.feng.edu.edu_class_place.cx.service.EduClassPlaceCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduClassPlaceCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduClassPlaceCxService service = new EduClassPlaceCxService();
			EduClassPlaceCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
