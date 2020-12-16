package all.gen.cms_msg_topic.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_msg_topic.t.service.CmsMsgTopicTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsMsgTopicTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsMsgTopicTService service = new CmsMsgTopicTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
