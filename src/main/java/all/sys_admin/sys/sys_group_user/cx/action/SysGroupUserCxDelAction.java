package all.sys_admin.sys.sys_group_user.cx.action;

import java.util.ArrayList;
import java.util.List;

import all.sys_admin.sys.sys_group_user.cx.service.SysGroupUserService;
import c.x.platform.root.common.action.BaseAction;

public class SysGroupUserCxDelAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysGroupUserService service = new SysGroupUserService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		return "index";
	}
}
