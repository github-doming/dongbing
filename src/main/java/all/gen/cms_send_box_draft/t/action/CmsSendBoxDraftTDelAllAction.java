package all.gen.cms_send_box_draft.t.action;
import all.gen.cms_send_box_draft.t.service.CmsSendBoxDraftTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsSendBoxDraftTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsSendBoxDraftTService service = new CmsSendBoxDraftTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
