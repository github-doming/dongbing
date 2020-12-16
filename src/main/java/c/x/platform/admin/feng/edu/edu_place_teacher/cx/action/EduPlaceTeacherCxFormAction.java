package c.x.platform.admin.feng.edu.edu_place_teacher.cx.action;

import c.x.platform.admin.feng.edu.edu_place_teacher.cx.entity.EduPlaceTeacherCx;
import c.x.platform.admin.feng.edu.edu_place_teacher.cx.service.EduPlaceTeacherCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduPlaceTeacherCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduPlaceTeacherCxService service = new EduPlaceTeacherCxService();
			EduPlaceTeacherCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
