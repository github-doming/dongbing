package c.x.platform.admin.feng.bbs.buy.bbs_post_info.action;

import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;

public class BbsPostInfoActionView extends BaseAction {

	@Override
	public String execute() throws Exception {

		c.x.platform.admin.feng.bbs.buy.bbs_post_info.service.BbsPostInfoService sBbsPostInfoService = new c.x.platform.admin.feng.bbs.buy.bbs_post_info.service.BbsPostInfoService();
		c.x.platform.admin.feng.bbs.buy.bbs_forum_info.service.BbsForumInfoService fBbsForumInfoService = new c.x.platform.admin.feng.bbs.buy.bbs_forum_info.service.BbsForumInfoService();

		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		// BaseLog.trace("id=" + id);

		if (id == null) {

		}

		if (id != null) {
			// 本身
			c.x.platform.admin.feng.bbs.buy.bbs_post_info.entity.BbsPostInfo s = sBbsPostInfoService
					.find(id);

			request.setAttribute("s", s);

		}

		// 树节点id
		// {
		// first$tree$id
		String first$tree$id = (String) request.getParameter("first$tree$id");

		request.setAttribute("value_first$tree$id", first$tree$id);

		// 选择上一级菜单
		if (StringUtil.isBlank(first$tree$id)) {

		} else {

			first$tree$id = first$tree$id.trim();

			c.x.platform.admin.feng.bbs.buy.bbs_forum_info.entity.BbsForumInfo p = fBbsForumInfoService
					.find(first$tree$id);

			request.setAttribute("p", p);

			// 树名称

			request.setAttribute("value_first$tree$name", p.getName());

		}

		// }
		// 树节点id

		return "index";
	}
}
