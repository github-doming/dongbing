package c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.action;

import c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.entity.FunSingleSimpleGenTreeSimple;
import c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.service.FunSingleSimpleGenTreeSimpleService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.string.StringUtil;

public class FunSingleSimpleGenTreeSimpleFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunSingleSimpleGenTreeSimpleService service = new FunSingleSimpleGenTreeSimpleService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		if (id == null) {
			// 选择上一级菜单
			String parent_id = (String) request.getParameter("parent_id");
			if (StringUtil.isBlank(parent_id)) {
			} else {
				parent_id = parent_id.trim();
				FunSingleSimpleGenTreeSimple p = service.find(parent_id);
				request.setAttribute("p", p);
			}
		}
		if (id != null) {
			id = id.trim();
			// 本身
			FunSingleSimpleGenTreeSimple s = service.find(id);
			request.setAttribute("s", s);
			// 选择上一级菜单
			String parentId = null;
			FunSingleSimpleGenTreeSimple p = null;
			// 上一级
			parentId = s.getParent();
			p = service.find(parentId);
			request.setAttribute("p", p);
		}
		return CommViewEnum.Default.toString();
	}
}
