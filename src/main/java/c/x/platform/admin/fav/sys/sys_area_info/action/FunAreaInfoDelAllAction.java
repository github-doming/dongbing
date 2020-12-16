package c.x.platform.admin.fav.sys.sys_area_info.action;
import c.x.platform.admin.fav.sys.sys_area_info.service.FunAreaInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FunAreaInfoDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		FunAreaInfoService service = new FunAreaInfoService();
		service.delAll(ids);
		return "index";
	}
}
