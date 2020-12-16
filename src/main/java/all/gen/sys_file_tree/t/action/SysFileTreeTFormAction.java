package all.gen.sys_file_tree.t.action;

import all.gen.sys_file_tree.t.entity.SysFileTreeT;
import all.gen.sys_file_tree.t.service.SysFileTreeTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysFileTreeTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysFileTreeTService service = new SysFileTreeTService();
			SysFileTreeT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
