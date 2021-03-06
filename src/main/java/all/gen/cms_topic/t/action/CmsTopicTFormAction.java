package all.gen.cms_topic.t.action;

import all.gen.cms_topic.t.entity.CmsTopicT;
import all.gen.cms_topic.t.service.CmsTopicTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsTopicTService service = new CmsTopicTService();
			CmsTopicT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
