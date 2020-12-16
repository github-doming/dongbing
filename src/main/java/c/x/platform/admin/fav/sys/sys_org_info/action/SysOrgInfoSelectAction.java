package c.x.platform.admin.fav.sys.sys_org_info.action;
import c.x.platform.admin.fav.sys.sys_org_info.entity.SysOrgInfo;
import c.x.platform.admin.fav.sys.sys_org_info.service.SysOrgInfoService;
import c.x.platform.root.common.action.BaseAction;
public class SysOrgInfoSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysOrgInfoService service = new SysOrgInfoService();
		String parent_id = request.getParameter("parent_id");
		SysOrgInfo s = service.find(parent_id);
		request.setAttribute("s", s);
		return "index";
	}
}
