package c.x.platform.admin.feng.fas.cx.fas_subject_info.action;
import java.util.ArrayList;
import java.util.List;
import c.x.platform.admin.feng.fas.cx.fas_subject_info.service.FasSubjectInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FasSubjectInfoDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		FasSubjectInfoService service = new FasSubjectInfoService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		return "index";
	}
}
