package c.x.platform.admin.feng.bbs.cx.bbs_note_info.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class BbsNoteInfoSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("bbs_note_info.id");

		c.x.platform.admin.feng.bbs.cx.bbs_note_info.entity.BbsNoteInfo s = (c.x.platform.admin.feng.bbs.cx.bbs_note_info.entity.BbsNoteInfo) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(
						c.x.platform.admin.feng.bbs.cx.bbs_note_info.vo.BbsNoteInfoVo.class,
						c.x.platform.admin.feng.bbs.cx.bbs_note_info.entity.BbsNoteInfo.class,
						request);
		c.x.platform.admin.feng.bbs.cx.bbs_note_info.service.BbsNoteInfoService service = new c.x.platform.admin.feng.bbs.cx.bbs_note_info.service.BbsNoteInfoService();
		// 第3表
		// {
		c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.service.BbsNotegroupNoteService tBbsNotegroupNoteService = new c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.service.BbsNotegroupNoteService();
		c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.entity.BbsNotegroupNote tBbsNotegroupNote = new c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.entity.BbsNotegroupNote();
		// 树的老的ID
		String name_first$tree$id = request.getParameter("name_first$tree$id");
		// 树的新的ID
		String bbs_notegroup_info$parent = request
				.getParameter("bbs_notegroup_info.parent");
		request.setAttribute("first$tree$id", bbs_notegroup_info$parent);
		// }
		// 第3表
		if (StringUtil.isBlank(id)) {
			id = service.insert(s);
			// 第3表保存
			tBbsNotegroupNote.setNotegroup_id(Long
					.parseLong(bbs_notegroup_info$parent));
			tBbsNotegroupNote.setNote_id(Long.parseLong(id));
			tBbsNotegroupNoteService.insert(tBbsNotegroupNote);
		} else {
			service.update(s);
			// 第3表删除
			tBbsNotegroupNoteService.del(name_first$tree$id, id);
			// 第3表保存
			tBbsNotegroupNote.setNotegroup_id(Long
					.parseLong(bbs_notegroup_info$parent));
			tBbsNotegroupNote.setNote_id(Long.parseLong(id));
			tBbsNotegroupNoteService.insert(tBbsNotegroupNote);
		}
		return "index";
	}
}
