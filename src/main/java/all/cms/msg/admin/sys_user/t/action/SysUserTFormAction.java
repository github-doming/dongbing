package all.cms.msg.admin.sys_user.t.action;

import all.cms.msg.admin.sys_user.t.service.SysUserTService;
import all.gen.sys_user.t.entity.SysUserT;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysUserTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysUserTService service = new SysUserTService();
			SysUserT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
