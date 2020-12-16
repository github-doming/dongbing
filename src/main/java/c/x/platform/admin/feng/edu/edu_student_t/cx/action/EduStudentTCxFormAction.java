package c.x.platform.admin.feng.edu.edu_student_t.cx.action;

import c.x.platform.admin.feng.edu.edu_student_t.cx.entity.EduStudentTCx;
import c.x.platform.admin.feng.edu.edu_student_t.cx.service.EduStudentTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduStudentTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduStudentTCxService service = new EduStudentTCxService();
			EduStudentTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
