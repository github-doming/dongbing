package c.x.platform.admin.feng.edu.edu_teacher_t.cx.action;

import c.x.platform.admin.feng.edu.edu_teacher_t.cx.service.EduTeacherTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduTeacherTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduTeacherTCxService service = new EduTeacherTCxService();
		service.delAll(ids);
		return "index";
	}
}
