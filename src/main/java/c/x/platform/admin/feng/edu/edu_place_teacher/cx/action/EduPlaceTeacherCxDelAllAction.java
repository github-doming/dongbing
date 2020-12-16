package c.x.platform.admin.feng.edu.edu_place_teacher.cx.action;

import c.x.platform.admin.feng.edu.edu_place_teacher.cx.service.EduPlaceTeacherCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduPlaceTeacherCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduPlaceTeacherCxService service = new EduPlaceTeacherCxService();
		service.delAll(ids);
		return "index";
	}
}
