package all.task.tms.tms_task.t.action;
import java.util.ArrayList;
import java.util.List;
import all.task.tms.tms_task.t.service.TmsTaskTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TmsTaskTDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		TmsTaskTService service = new TmsTaskTService();
		service.del(id);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
