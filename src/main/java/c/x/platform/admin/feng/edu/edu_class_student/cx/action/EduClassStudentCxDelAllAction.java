package c.x.platform.admin.feng.edu.edu_class_student.cx.action;

import c.x.platform.admin.feng.edu.edu_class_student.cx.service.EduClassStudentCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduClassStudentCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduClassStudentCxService service = new EduClassStudentCxService();
		service.delAll(ids);
		return "index";
	}
}
