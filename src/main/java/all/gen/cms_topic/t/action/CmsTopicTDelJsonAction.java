package all.gen.cms_topic.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_topic.t.service.CmsTopicTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsTopicTService service = new CmsTopicTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
