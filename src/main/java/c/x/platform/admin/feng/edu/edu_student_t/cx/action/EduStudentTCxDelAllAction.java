package c.x.platform.admin.feng.edu.edu_student_t.cx.action;

import c.x.platform.admin.feng.edu.edu_student_t.cx.service.EduStudentTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduStudentTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduStudentTCxService service = new EduStudentTCxService();
		service.delAll(ids);
		return "index";
	}
}
