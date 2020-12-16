package all.gen.cms_box_topic.t.action;

import all.gen.cms_box_topic.t.entity.CmsBoxTopicT;
import all.gen.cms_box_topic.t.service.CmsBoxTopicTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxTopicTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsBoxTopicTService service = new CmsBoxTopicTService();
			CmsBoxTopicT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
