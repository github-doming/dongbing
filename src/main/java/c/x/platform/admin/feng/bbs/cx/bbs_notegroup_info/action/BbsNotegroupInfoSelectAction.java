package c.x.platform.admin.feng.bbs.cx.bbs_notegroup_info.action;
import c.x.platform.admin.feng.bbs.cx.bbs_notegroup_info.entity.BbsNotegroupInfo;
import c.x.platform.admin.feng.bbs.cx.bbs_notegroup_info.service.BbsNotegroupInfoService;
import c.x.platform.root.common.action.BaseAction;
public class BbsNotegroupInfoSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		BbsNotegroupInfoService service = new BbsNotegroupInfoService();
		String parent_id = request.getParameter("parent_id");
		BbsNotegroupInfo s = service.find(parent_id);
		request.setAttribute("s", s);
		return "index";
	}
}
