package c.x.platform.admin.feng.edu.edu_search_t.cx.action;

import c.x.platform.admin.feng.edu.edu_search_t.cx.service.EduSearchTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduSearchTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduSearchTCxService service = new EduSearchTCxService();
		service.delAll(ids);
		return "index";
	}
}
