package c.x.platform.admin.feng.edu.edu_class_teacher.cx.action;

import c.x.platform.admin.feng.edu.edu_class_teacher.cx.entity.EduClassTeacherCx;
import c.x.platform.admin.feng.edu.edu_class_teacher.cx.service.EduClassTeacherCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduClassTeacherCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduClassTeacherCxService service = new EduClassTeacherCxService();
			EduClassTeacherCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
