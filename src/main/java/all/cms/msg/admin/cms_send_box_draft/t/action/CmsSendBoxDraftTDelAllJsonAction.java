package all.cms.msg.admin.cms_send_box_draft.t.action;
import all.cms.msg.admin.cms_send_box_draft.t.service.CmsSendBoxDraftTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsSendBoxDraftTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsSendBoxDraftTService service = new CmsSendBoxDraftTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
