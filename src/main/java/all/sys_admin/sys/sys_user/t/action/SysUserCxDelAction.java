package all.sys_admin.sys.sys_user.t.action;

import java.util.ArrayList;
import java.util.List;

import all.sys_admin.sys.sys_user.t.service.SysUserService;
import c.x.platform.root.common.action.BaseAction;

public class SysUserCxDelAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysUserService service = new SysUserService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		return "index";
	}
}
