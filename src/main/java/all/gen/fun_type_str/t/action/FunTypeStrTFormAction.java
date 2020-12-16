package all.gen.fun_type_str.t.action;

import all.gen.fun_type_str.t.entity.FunTypeStrT;
import all.gen.fun_type_str.t.service.FunTypeStrTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeStrTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			FunTypeStrTService service = new FunTypeStrTService();
			FunTypeStrT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
