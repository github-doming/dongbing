package all.gen.sys_area.t.action;

import all.gen.sys_area.t.entity.SysAreaT;
import all.gen.sys_area.t.service.SysAreaTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;
import c.a.util.core.enums.bean.CommViewEnum;
public class SysAreaTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysAreaTService service = new SysAreaTService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		if (id == null) {
			// 选择上一级菜单
			String parent_id = (String) request.getParameter("parent_id");
			if (StringUtil.isBlank(parent_id)) {
			} else {
				parent_id = parent_id.trim();
				SysAreaT p = service.find(parent_id);
				request.setAttribute("p", p);
			}
		}
		if (id != null) {
			id = id.trim();
			// 本身
			SysAreaT s = service.find(id);
			request.setAttribute("s", s);
			// 选择上一级菜单
			String parentId = null;
			SysAreaT p = null;
			// 上一级
			parentId = s.getParent();
			p = service.find(parentId);
			request.setAttribute("p", p);
		}
		return CommViewEnum.Default.toString();
	}
}
