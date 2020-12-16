package c.x.platform.admin.fav.sys.sys_area_info.action;
import c.x.platform.admin.fav.sys.sys_area_info.entity.FunAreaInfo;
import c.x.platform.admin.fav.sys.sys_area_info.service.FunAreaInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FunAreaInfoFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			FunAreaInfoService service = new FunAreaInfoService();
			FunAreaInfo s = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", s);
		}
		return "index";
	}
}
