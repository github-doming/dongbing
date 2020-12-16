package all.cms.msg.admin.cms_topic_user.t.action;

import all.cms.msg.admin.cms_topic_user.t.service.CmsTopicUserTService;
import all.gen.cms_topic_user.t.entity.CmsTopicUserT;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicUserTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsTopicUserTService service = new CmsTopicUserTService();
			CmsTopicUserT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
