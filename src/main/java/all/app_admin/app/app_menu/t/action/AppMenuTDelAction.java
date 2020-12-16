package all.app_admin.app.app_menu.t.action;
import java.util.ArrayList;
import java.util.List;
import all.app_admin.app.app_menu.t.service.AppMenuTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppMenuTDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppMenuTService service = new AppMenuTService();
		service.del(id);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
