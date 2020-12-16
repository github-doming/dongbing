package all.gen.cms_box_msg.t.action;

import all.gen.cms_box_msg.t.entity.CmsBoxMsgT;
import all.gen.cms_box_msg.t.service.CmsBoxMsgTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxMsgTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsBoxMsgTService service = new CmsBoxMsgTService();
			CmsBoxMsgT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
