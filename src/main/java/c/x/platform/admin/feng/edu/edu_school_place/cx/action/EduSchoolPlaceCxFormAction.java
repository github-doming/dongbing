package c.x.platform.admin.feng.edu.edu_school_place.cx.action;

import c.x.platform.admin.feng.edu.edu_school_place.cx.entity.EduSchoolPlaceCx;
import c.x.platform.admin.feng.edu.edu_school_place.cx.service.EduSchoolPlaceCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduSchoolPlaceCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduSchoolPlaceCxService service = new EduSchoolPlaceCxService();
			EduSchoolPlaceCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
