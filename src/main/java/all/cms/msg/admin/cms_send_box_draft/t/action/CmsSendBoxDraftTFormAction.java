package all.cms.msg.admin.cms_send_box_draft.t.action;

import all.cms.msg.admin.cms_send_box_draft.t.service.CmsSendBoxDraftTService;
import all.gen.cms_send_box_draft.t.entity.CmsSendBoxDraftT;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsSendBoxDraftTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsSendBoxDraftTService service = new CmsSendBoxDraftTService();
			CmsSendBoxDraftT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
