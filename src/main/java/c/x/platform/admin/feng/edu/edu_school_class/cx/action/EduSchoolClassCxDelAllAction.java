package c.x.platform.admin.feng.edu.edu_school_class.cx.action;

import c.x.platform.admin.feng.edu.edu_school_class.cx.service.EduSchoolClassCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduSchoolClassCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduSchoolClassCxService service = new EduSchoolClassCxService();
		service.delAll(ids);
		return "index";
	}
}
