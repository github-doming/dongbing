package all.sys_admin.sys.dict.sys_dict_tree.action;

import all.sys_admin.sys.dict.sys_dict_tree.entity.SysDictTreeT;
import all.sys_admin.sys.dict.sys_dict_tree.service.SysDictTreeTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class SysDictTreeTSelectAction  extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysDictTreeTService service = new SysDictTreeTService();
		String parent_id = request.getParameter("parent_id");
		SysDictTreeT s = service.find(parent_id);
		request.setAttribute("s", s);
		return CommViewEnum.Default.toString();
	}
}
