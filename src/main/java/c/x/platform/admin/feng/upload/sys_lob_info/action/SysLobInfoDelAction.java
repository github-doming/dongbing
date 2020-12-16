package c.x.platform.admin.feng.upload.sys_lob_info.action;
import java.util.ArrayList;
import java.util.List;
import c.x.platform.admin.feng.upload.sys_lob_info.service.SysLobInfoService;
import c.x.platform.root.common.action.BaseAction;
public class SysLobInfoDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysLobInfoService service = new SysLobInfoService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		return "index";
	}
}
