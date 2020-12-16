package all.gen.fun_type_long.t.action;
import all.gen.fun_type_long.t.entity.FunTypeLongT;
import all.gen.fun_type_long.t.service.FunTypeLongTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeLongTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			FunTypeLongTService service = new FunTypeLongTService();
			FunTypeLongT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
