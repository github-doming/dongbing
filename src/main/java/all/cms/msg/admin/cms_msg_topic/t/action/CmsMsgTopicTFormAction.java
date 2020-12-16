package all.cms.msg.admin.cms_msg_topic.t.action;

import all.cms.msg.admin.cms_msg_topic.t.service.CmsMsgTopicTService;
import all.gen.cms_msg_topic.t.entity.CmsMsgTopicT;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsMsgTopicTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsMsgTopicTService service = new CmsMsgTopicTService();
			CmsMsgTopicT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
