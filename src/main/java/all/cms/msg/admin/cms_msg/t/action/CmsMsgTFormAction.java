package all.cms.msg.admin.cms_msg.t.action;

import all.cms.msg.admin.cms_msg.t.service.CmsMsgTService;
import all.gen.cms_msg.t.entity.CmsMsgT;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsMsgTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsMsgTService service = new CmsMsgTService();
			CmsMsgT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
