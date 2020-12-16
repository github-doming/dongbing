package c.x.platform.admin.feng.upload.sys_lob_info.action;
import c.x.platform.admin.feng.upload.sys_lob_info.entity.SysLobInfo;
import c.x.platform.admin.feng.upload.sys_lob_info.service.SysLobInfoService;
import c.x.platform.root.common.action.BaseAction;
public class SysLobInfoFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysLobInfoService service = new SysLobInfoService();
			SysLobInfo s = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", s);
		}
		return "index";
	}
}
