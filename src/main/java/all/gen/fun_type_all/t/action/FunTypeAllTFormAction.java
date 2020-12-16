package all.gen.fun_type_all.t.action;
import all.gen.fun_type_all.t.entity.FunTypeAllT;
import all.gen.fun_type_all.t.service.FunTypeAllTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeAllTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			FunTypeAllTService service = new FunTypeAllTService();
			FunTypeAllT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
