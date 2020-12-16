package all.task.tms.tms_function.t.action;

import all.task.tms.tms_function.t.entity.TmsFunctionT;
import all.task.tms.tms_function.t.service.TmsFunctionTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsFunctionTSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsFunctionTService service = new TmsFunctionTService();
		String parent_id = request.getParameter("parent_id");
		TmsFunctionT s = service.find(parent_id);
		request.setAttribute("s", s);
		return CommViewEnum.Default.toString();
	}
}
