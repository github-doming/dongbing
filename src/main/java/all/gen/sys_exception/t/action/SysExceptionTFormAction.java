package all.gen.sys_exception.t.action;

import all.gen.sys_exception.t.entity.SysExceptionT;
import all.gen.sys_exception.t.service.SysExceptionTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysExceptionTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysExceptionTService service = new SysExceptionTService();
			SysExceptionT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
