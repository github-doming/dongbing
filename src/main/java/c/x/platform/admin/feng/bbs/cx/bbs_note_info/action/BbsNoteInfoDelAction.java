package c.x.platform.admin.feng.bbs.cx.bbs_note_info.action;
import java.util.ArrayList;
import java.util.List;
import c.x.platform.root.common.action.BaseAction;
public class BbsNoteInfoDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		c.x.platform.admin.feng.bbs.cx.bbs_note_info.service.BbsNoteInfoService service = new c.x.platform.admin.feng.bbs.cx.bbs_note_info.service.BbsNoteInfoService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		// 删除第3表
		// 第3表
		c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.service.BbsNotegroupNoteService tBbsNotegroupNoteService = new c.x.platform.admin.feng.bbs.cx.bbs_notegroup_note.service.BbsNotegroupNoteService();
		// 第3表删除
		tBbsNotegroupNoteService.del_by_userId(id);
		return "index";
	}
}
