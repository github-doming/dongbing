package c.x.platform.admin.feng.upload.sys_lob_info.action;
import c.x.platform.admin.feng.upload.sys_lob_info.service.SysLobInfoService;
import c.x.platform.root.common.action.BaseAction;
public class SysLobInfoDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		SysLobInfoService service = new SysLobInfoService();
		service.delAll(ids);
		return "index";
	}
}
