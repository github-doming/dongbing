package c.x.platform.admin.feng.bbs.buy.bbs_post_info.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;

public class BbsPostInfoSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("bbs_post_info.id");

		c.x.platform.admin.feng.bbs.buy.bbs_post_info.entity.BbsPostInfo s = (c.x.platform.admin.feng.bbs.buy.bbs_post_info.entity.BbsPostInfo) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(
						c.x.platform.admin.feng.bbs.buy.bbs_post_info.vo.BbsPostInfoVo.class,
						c.x.platform.admin.feng.bbs.buy.bbs_post_info.entity.BbsPostInfo.class,
						request);

		c.x.platform.admin.feng.bbs.buy.bbs_post_info.service.BbsPostInfoService service = new c.x.platform.admin.feng.bbs.buy.bbs_post_info.service.BbsPostInfoService();

		// 第3表
		// {
		c.x.platform.admin.feng.bbs.buy.bbs_forum_post.service.BbsForumPostService tBbsForumPostService = new c.x.platform.admin.feng.bbs.buy.bbs_forum_post.service.BbsForumPostService();
		c.x.platform.admin.feng.bbs.buy.bbs_forum_post.entity.BbsForumPost tBbsForumPost = new c.x.platform.admin.feng.bbs.buy.bbs_forum_post.entity.BbsForumPost();
		// 树的老的ID
		String name_first$tree$id = request.getParameter("name_first$tree$id");
		// 树的新的ID
		String bbs_forum_info$parent = request
				.getParameter("bbs_forum_info.parent");

		request.setAttribute("first$tree$id", bbs_forum_info$parent);
		// }
		// 第3表

		if (StringUtil.isBlank(id)) {
			id = service.save(s);

			// 第3表保存

			tBbsForumPost.setForum_id(Long.parseLong(bbs_forum_info$parent));
			tBbsForumPost.setPost_id(Long.parseLong(id));

			tBbsForumPostService.save(tBbsForumPost);

		} else {
			service.update(s);

			// 第3表删除
			tBbsForumPostService.del(name_first$tree$id, id);

			// 第3表保存

			tBbsForumPost.setForum_id(Long.parseLong(bbs_forum_info$parent));
			tBbsForumPost.setPost_id(Long.parseLong(id));

			tBbsForumPostService.save(tBbsForumPost);

		}

		return "index";
	}
}
