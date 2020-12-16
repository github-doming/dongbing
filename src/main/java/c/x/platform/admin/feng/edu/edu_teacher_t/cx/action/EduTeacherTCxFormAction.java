package c.x.platform.admin.feng.edu.edu_teacher_t.cx.action;

import c.x.platform.admin.feng.edu.edu_teacher_t.cx.entity.EduTeacherTCx;
import c.x.platform.admin.feng.edu.edu_teacher_t.cx.service.EduTeacherTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduTeacherTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduTeacherTCxService service = new EduTeacherTCxService();
			EduTeacherTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
