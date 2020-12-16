package c.x.platform.admin.feng.edu.edu_school_class.cx.action;

import c.x.platform.admin.feng.edu.edu_school_class.cx.entity.EduSchoolClassCx;
import c.x.platform.admin.feng.edu.edu_school_class.cx.service.EduSchoolClassCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduSchoolClassCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			EduSchoolClassCxService service = new EduSchoolClassCxService();
			EduSchoolClassCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
