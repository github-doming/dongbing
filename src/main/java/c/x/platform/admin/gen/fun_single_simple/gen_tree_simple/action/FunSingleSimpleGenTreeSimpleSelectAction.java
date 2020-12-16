package c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.action;

import c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.entity.FunSingleSimpleGenTreeSimple;
import c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.service.FunSingleSimpleGenTreeSimpleService;
import  c.x.platform.root.common.action.BaseAction;

public class FunSingleSimpleGenTreeSimpleSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunSingleSimpleGenTreeSimpleService service = new FunSingleSimpleGenTreeSimpleService();
		String parent_id = request.getParameter("parent_id");
		FunSingleSimpleGenTreeSimple s = service.find(parent_id);
		request.setAttribute("s", s);
		return "index";
	}
}
