package all.cms.msg.admin.cms_msg_topic.t.action;
import all.cms.msg.admin.cms_msg_topic.t.service.CmsMsgTopicTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsMsgTopicTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsMsgTopicTService service = new CmsMsgTopicTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
