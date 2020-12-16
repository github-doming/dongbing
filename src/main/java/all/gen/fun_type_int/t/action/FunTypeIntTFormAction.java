package all.gen.fun_type_int.t.action;
import all.gen.fun_type_int.t.entity.FunTypeIntT;
import all.gen.fun_type_int.t.service.FunTypeIntTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeIntTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			FunTypeIntTService service = new FunTypeIntTService();
			FunTypeIntT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
