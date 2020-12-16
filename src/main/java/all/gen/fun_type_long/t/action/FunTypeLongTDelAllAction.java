package all.gen.fun_type_long.t.action;
import all.gen.fun_type_long.t.service.FunTypeLongTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeLongTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		FunTypeLongTService service = new FunTypeLongTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
