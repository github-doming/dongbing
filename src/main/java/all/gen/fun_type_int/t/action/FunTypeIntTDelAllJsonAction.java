package all.gen.fun_type_int.t.action;
import all.gen.fun_type_int.t.service.FunTypeIntTService;
import c.a.config.SysConfig;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeIntTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		FunTypeIntTService service = new FunTypeIntTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
