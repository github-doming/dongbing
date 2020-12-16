package c.x.platform.sys.sys_user.cx.action;

import java.util.ArrayList;
import java.util.List;

import c.x.platform.sys.sys_user.cx.service.SysUserCxService;
import c.x.platform.root.common.action.BaseAction;

public class SysUserCxDelAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysUserCxService service = new SysUserCxService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		return "index";
	}
}
