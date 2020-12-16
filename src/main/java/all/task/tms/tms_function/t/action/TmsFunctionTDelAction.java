package all.task.tms.tms_function.t.action;
import java.util.ArrayList;
import java.util.List;
import all.task.tms.tms_function.t.service.TmsFunctionTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TmsFunctionTDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		TmsFunctionTService service = new TmsFunctionTService();
		service.del(id);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
