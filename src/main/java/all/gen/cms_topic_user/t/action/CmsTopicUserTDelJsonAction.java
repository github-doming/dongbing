package all.gen.cms_topic_user.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_topic_user.t.service.CmsTopicUserTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicUserTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsTopicUserTService service = new CmsTopicUserTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
