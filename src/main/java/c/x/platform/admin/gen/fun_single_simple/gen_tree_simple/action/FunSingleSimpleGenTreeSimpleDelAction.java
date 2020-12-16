package c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.action;
import c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.service.FunSingleSimpleGenTreeSimpleService;
import c.x.platform.root.common.action.BaseAction;
public class FunSingleSimpleGenTreeSimpleDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		FunSingleSimpleGenTreeSimpleService service = new FunSingleSimpleGenTreeSimpleService();
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
