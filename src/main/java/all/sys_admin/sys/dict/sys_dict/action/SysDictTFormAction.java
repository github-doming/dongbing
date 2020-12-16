package all.sys_admin.sys.dict.sys_dict.action;
import all.sys_admin.sys.dict.sys_dict.entity.SysDictT;
import all.sys_admin.sys.dict.sys_dict.service.SysDictTService;
import all.sys_admin.sys.dict.sys_dict_tree.entity.SysDictTreeT;
import all.sys_admin.sys.dict.sys_dict_tree.service.SysDictTreeTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysDictTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysDictTService sysDictTService = new SysDictTService();
		SysDictTreeTService sysDictTreeTService = new SysDictTreeTService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		// 树节点id
		// {
		// first$tree$id
		String first$tree$id = (String) request.getParameter("first$tree$id");
		request.setAttribute("value_first$tree$id", first$tree$id);
		// 选择上一级菜单
		SysDictTreeT p = null;
		if (StringUtil.isBlank(first$tree$id)) {
		} else {
			first$tree$id = first$tree$id.trim();
			p = sysDictTreeTService.find(first$tree$id);
			request.setAttribute("p", p);
			// 树名称
			request.setAttribute("value_first$tree$name", p.getSysDictTreeName());
		}
		// log.trace("id=" + id);
		SysDictT s = null;
		if (StringUtil.isNotBlank(id)) {
			// 本身
			s = sysDictTService.find(id);
		} else {
			// 本身
			s = new SysDictT();
		}
		// 编码
		if (p != null) {
			s.setSysDictCode(p.getSysDictTreeCode());
		}
		request.setAttribute("s", s);
		// }
		// 树节点id
		return CommViewEnum.Default.toString();
	}
}
