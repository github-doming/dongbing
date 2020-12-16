package all.gen.fun_type_all.t.action;
import all.gen.fun_type_all.t.service.FunTypeAllTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeAllTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		FunTypeAllTService service = new FunTypeAllTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
