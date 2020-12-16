package all.gen.cms_post.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_post.t.service.CmsPostTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsPostTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsPostTService service = new CmsPostTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
