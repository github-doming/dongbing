package c.x.platform.admin.feng.edu.edu_class_teacher.cx.action;

import c.x.platform.admin.feng.edu.edu_class_teacher.cx.service.EduClassTeacherCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduClassTeacherCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduClassTeacherCxService service = new EduClassTeacherCxService();
		service.delAll(ids);
		return "index";
	}
}
