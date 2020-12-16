package c.x.platform.admin.feng.bbs.cx.bbs_note_info.action;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;
public class BbsNoteInfoFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		c.x.platform.admin.feng.bbs.cx.bbs_note_info.service.BbsNoteInfoService sBbsNoteInfoService = new c.x.platform.admin.feng.bbs.cx.bbs_note_info.service.BbsNoteInfoService();
		c.x.platform.admin.feng.bbs.cx.bbs_notegroup_info.service.BbsNotegroupInfoService fBbsNotegroupInfoService = new c.x.platform.admin.feng.bbs.cx.bbs_notegroup_info.service.BbsNotegroupInfoService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		// BaseLog.trace("id=" + id);
		if (id == null) {
		}
		if (id != null) {
			// 本身
			c.x.platform.admin.feng.bbs.cx.bbs_note_info.entity.BbsNoteInfo s = sBbsNoteInfoService
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
			c.x.platform.admin.feng.bbs.cx.bbs_notegroup_info.entity.BbsNotegroupInfo p = fBbsNotegroupInfoService
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
