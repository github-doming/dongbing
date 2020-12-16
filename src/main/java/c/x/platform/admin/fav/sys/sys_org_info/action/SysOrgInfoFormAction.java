package c.x.platform.admin.fav.sys.sys_org_info.action;
import c.x.platform.admin.fav.sys.sys_org_info.entity.SysOrgInfo;
import c.x.platform.admin.fav.sys.sys_org_info.service.SysOrgInfoService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;
public class SysOrgInfoFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysOrgInfoService service = new SysOrgInfoService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		if (id == null) {
			// 选择上一级菜单
			String parent_id = (String) request.getParameter("parent_id");
			if (StringUtil.isBlank(parent_id)) {
			} else {
				parent_id = parent_id.trim();
				SysOrgInfo p = service.find(parent_id);
				request.setAttribute("p", p);
			}
		}
		if (id != null) {
			id = id.trim();
			// 本身
			SysOrgInfo s = service.find(id);
			request.setAttribute("s", s);
			// 选择上一级菜单
			Integer parentIdStr = null;
			SysOrgInfo p = null;
			// 上一级
			parentIdStr = s.getParent();
			p = service.find(parentIdStr.toString());
			request.setAttribute("p", p);
		}
		return "index";
	}
}
