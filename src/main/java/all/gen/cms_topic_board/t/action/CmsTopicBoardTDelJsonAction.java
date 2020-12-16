package all.gen.cms_topic_board.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_topic_board.t.service.CmsTopicBoardTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicBoardTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsTopicBoardTService service = new CmsTopicBoardTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
