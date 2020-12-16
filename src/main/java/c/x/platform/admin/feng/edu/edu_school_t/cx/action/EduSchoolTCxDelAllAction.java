package c.x.platform.admin.feng.edu.edu_school_t.cx.action;

import c.x.platform.admin.feng.edu.edu_school_t.cx.service.EduSchoolTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduSchoolTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduSchoolTCxService service = new EduSchoolTCxService();
		service.delAll(ids);
		return "index";
	}
}
