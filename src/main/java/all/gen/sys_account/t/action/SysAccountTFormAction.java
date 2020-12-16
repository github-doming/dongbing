package all.gen.sys_account.t.action;

import all.gen.sys_account.t.entity.SysAccountT;
import all.gen.sys_account.t.service.SysAccountTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysAccountTService service = new SysAccountTService();
			SysAccountT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
