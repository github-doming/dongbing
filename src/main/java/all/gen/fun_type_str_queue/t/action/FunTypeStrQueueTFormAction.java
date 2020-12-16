package all.gen.fun_type_str_queue.t.action;

import all.gen.fun_type_str_queue.t.entity.FunTypeStrQueueT;
import all.gen.fun_type_str_queue.t.service.FunTypeStrQueueTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeStrQueueTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			FunTypeStrQueueTService service = new FunTypeStrQueueTService();
			FunTypeStrQueueT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
