package c.x.platform.admin.feng.bbs.buy.bbs_post_info.action;

import java.util.ArrayList;
import java.util.List;
import c.x.platform.root.common.action.BaseAction;

public class BbsPostInfoDelAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		c.x.platform.admin.feng.bbs.buy.bbs_post_info.service.BbsPostInfoService service = new c.x.platform.admin.feng.bbs.buy.bbs_post_info.service.BbsPostInfoService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);

		// 删除第3表
		// 第3表
		c.x.platform.admin.feng.bbs.buy.bbs_forum_post.service.BbsForumPostService tBbsForumPostService = new c.x.platform.admin.feng.bbs.buy.bbs_forum_post.service.BbsForumPostService();
		// 第3表删除
		tBbsForumPostService.del_by_postId(id);

		return "index";
	}
}
