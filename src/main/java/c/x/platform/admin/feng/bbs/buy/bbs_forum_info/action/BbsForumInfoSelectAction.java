package c.x.platform.admin.feng.bbs.buy.bbs_forum_info.action;

import c.x.platform.admin.feng.bbs.buy.bbs_forum_info.entity.BbsForumInfo;
import c.x.platform.admin.feng.bbs.buy.bbs_forum_info.service.BbsForumInfoService;
import c.x.platform.root.common.action.BaseAction;

public class BbsForumInfoSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		BbsForumInfoService service = new BbsForumInfoService();
		String parent_id = request.getParameter("parent_id");
		BbsForumInfo s = service.find(parent_id);
		request.setAttribute("s", s);
		return "index";
	}
}
