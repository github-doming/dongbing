package all.gen.sys_pk.t.action;

import all.gen.sys_pk.t.entity.SysPkT;
import all.gen.sys_pk.t.service.SysPkTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysPkTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysPkTService service = new SysPkTService();
			SysPkT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
