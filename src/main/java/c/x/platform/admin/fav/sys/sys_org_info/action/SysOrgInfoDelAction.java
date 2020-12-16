package c.x.platform.admin.fav.sys.sys_org_info.action;
import c.x.platform.admin.fav.sys.sys_org_info.service.SysOrgInfoService;
import c.x.platform.root.common.action.BaseAction;
public class SysOrgInfoDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysOrgInfoService service = new SysOrgInfoService();
		service.del(id);
		return "index";
	}
}
