package all.archit.complex.spring_kaida.admin.fun.user_info.action;
import java.util.ArrayList;
import java.util.List;
import c.x.platform.root.common.action.BaseAction;
import all.archit.complex.spring_kaida.admin.fun.user_info.service.FunUserInfoService;
public class FunUserInfoActionDel extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		FunUserInfoService service = new FunUserInfoService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		return "index";
	}
}
