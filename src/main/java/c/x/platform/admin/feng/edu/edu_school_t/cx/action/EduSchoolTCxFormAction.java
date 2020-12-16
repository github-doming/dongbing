package c.x.platform.admin.feng.edu.edu_school_t.cx.action;

import c.x.platform.admin.feng.edu.edu_school_t.cx.entity.EduSchoolTCx;
import c.x.platform.admin.feng.edu.edu_school_t.cx.service.EduSchoolTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduSchoolTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduSchoolTCxService service = new EduSchoolTCxService();
			EduSchoolTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
