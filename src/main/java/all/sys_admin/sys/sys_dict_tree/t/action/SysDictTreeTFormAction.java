package all.sys_admin.sys.sys_dict_tree.t.action;

import all.sys_admin.sys.sys_dict_tree.t.entity.SysDictTreeT;
import all.sys_admin.sys.sys_dict_tree.t.service.SysDictTreeTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;
import c.a.util.core.enums.bean.CommViewEnum;
public class SysDictTreeTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysDictTreeTService service = new SysDictTreeTService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		if (id == null) {
			// 选择上一级菜单
			String parent_id = (String) request.getParameter("parent_id");
			if (StringUtil.isBlank(parent_id)) {
			} else {
				parent_id = parent_id.trim();
				SysDictTreeT p = service.find(parent_id);
				request.setAttribute("p", p);
			}
		}
		if (id != null) {
			id = id.trim();
			// 本身
			SysDictTreeT s = service.find(id);
			request.setAttribute("s", s);
			// 选择上一级菜单
			String parentId = null;
			SysDictTreeT p = null;
			// 上一级
			parentId = s.getParent();
			p = service.find(parentId);
			request.setAttribute("p", p);
		}
		return CommViewEnum.Default.toString();
	}
}
