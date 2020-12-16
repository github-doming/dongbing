package c.x.platform.admin.fav.sys.sys_org_info.action;
import c.x.platform.admin.fav.sys.sys_org_info.entity.SysOrgInfo;
import c.x.platform.admin.fav.sys.sys_org_info.service.SysOrgInfoService;
import c.x.platform.admin.fav.sys.sys_org_info.vo.SysOrgInfoVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
public class SysOrgInfoSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String parent_id = request.getParameter("sys_org_info.parent_id");
		String id = request.getParameter("sys_org_info.id");

		SysOrgInfo s = (SysOrgInfo) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysOrgInfoVo.class, SysOrgInfo.class, request);
		SysOrgInfoService service = new SysOrgInfoService();
		if (StringUtil.isBlank(id)) {
			service.insert(s);
		} else {
			service.update(s);
		}
		return "index";
	}
}
