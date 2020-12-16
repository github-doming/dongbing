package all.gen.cms_box_topic.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_box_topic.t.service.CmsBoxTopicTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxTopicTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsBoxTopicTService service = new CmsBoxTopicTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
