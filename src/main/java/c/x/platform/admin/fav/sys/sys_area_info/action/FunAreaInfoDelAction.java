package c.x.platform.admin.fav.sys.sys_area_info.action;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import c.x.platform.admin.fav.sys.sys_area_info.service.FunAreaInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FunAreaInfoDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		FunAreaInfoService service = new FunAreaInfoService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		return "index";
	}
}
