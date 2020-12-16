package c.x.platform.admin.feng.edu.edu_place_t.cx.action;

import c.x.platform.admin.feng.edu.edu_place_t.cx.service.EduPlaceTCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduPlaceTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduPlaceTCxService service = new EduPlaceTCxService();
		service.delAll(ids);
		return "index";
	}
}
