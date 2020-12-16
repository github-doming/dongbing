package all.gen.sys_area.t.action;

import all.gen.sys_area.t.entity.SysAreaT;
import all.gen.sys_area.t.service.SysAreaTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class SysAreaTSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysAreaTService service = new SysAreaTService();
		String parent_id = request.getParameter("parent_id");
		SysAreaT s = service.find(parent_id);
		request.setAttribute("s", s);
		return CommViewEnum.Default.toString();
	}
}
