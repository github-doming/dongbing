package all.task.tms.tms_task.t.action;

import all.task.tms.tms_task.t.entity.TmsTaskT;
import all.task.tms.tms_task.t.service.TmsTaskTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsTaskTSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsTaskTService service = new TmsTaskTService();
		String parent_id = request.getParameter("parent_id");
		TmsTaskT s = service.find(parent_id);
		request.setAttribute("s", s);
		return CommViewEnum.Default.toString();
	}
}
