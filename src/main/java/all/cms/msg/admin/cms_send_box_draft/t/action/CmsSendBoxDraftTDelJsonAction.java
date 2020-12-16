package all.cms.msg.admin.cms_send_box_draft.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.cms.msg.admin.cms_send_box_draft.t.service.CmsSendBoxDraftTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsSendBoxDraftTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsSendBoxDraftTService service = new CmsSendBoxDraftTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
