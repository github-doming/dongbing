package all.gen.fun_type_str.t.action;
import all.gen.fun_type_str.t.service.FunTypeStrTService;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeStrTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		FunTypeStrTService service = new FunTypeStrTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
