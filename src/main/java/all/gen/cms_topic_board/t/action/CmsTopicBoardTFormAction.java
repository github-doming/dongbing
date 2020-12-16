package all.gen.cms_topic_board.t.action;

import all.gen.cms_topic_board.t.entity.CmsTopicBoardT;
import all.gen.cms_topic_board.t.service.CmsTopicBoardTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicBoardTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsTopicBoardTService service = new CmsTopicBoardTService();
			CmsTopicBoardT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
