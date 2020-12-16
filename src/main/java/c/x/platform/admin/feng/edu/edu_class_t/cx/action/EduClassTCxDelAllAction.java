package c.x.platform.admin.feng.edu.edu_class_t.cx.action;

import c.x.platform.admin.feng.edu.edu_class_t.cx.service.EduClassTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduClassTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduClassTCxService service = new EduClassTCxService();
		service.delAll(ids);
		return "index";
	}
}
