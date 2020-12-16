package c.x.platform.admin.feng.bbs.cx.bbs_note_info.action;
import c.x.platform.admin.feng.bbs.cx.bbs_note_info.service.BbsNoteInfoService;
import c.x.platform.root.common.action.BaseAction;
public class BbsNoteInfoDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		BbsNoteInfoService service = new BbsNoteInfoService();
		service.delAll(ids);
		return "index";
	}
}
