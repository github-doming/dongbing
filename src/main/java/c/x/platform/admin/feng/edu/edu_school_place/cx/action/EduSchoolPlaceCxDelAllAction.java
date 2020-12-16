package c.x.platform.admin.feng.edu.edu_school_place.cx.action;

import c.x.platform.admin.feng.edu.edu_school_place.cx.service.EduSchoolPlaceCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduSchoolPlaceCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduSchoolPlaceCxService service = new EduSchoolPlaceCxService();
		service.delAll(ids);
		return "index";
	}
}
