package c.x.platform.admin.feng.edu.edu_class_student.cx.action;

import c.x.platform.admin.feng.edu.edu_class_student.cx.entity.EduClassStudentCx;
import c.x.platform.admin.feng.edu.edu_class_student.cx.service.EduClassStudentCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduClassStudentCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduClassStudentCxService service = new EduClassStudentCxService();
			EduClassStudentCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
