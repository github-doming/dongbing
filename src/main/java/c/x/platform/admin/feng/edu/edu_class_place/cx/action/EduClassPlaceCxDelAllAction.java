package c.x.platform.admin.feng.edu.edu_class_place.cx.action;

import c.x.platform.admin.feng.edu.edu_class_place.cx.service.EduClassPlaceCxService;
import c.x.platform.root.common.action.BaseAction;

public class EduClassPlaceCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		EduClassPlaceCxService service = new EduClassPlaceCxService();
		service.delAll(ids);
		return "index";
	}
}
