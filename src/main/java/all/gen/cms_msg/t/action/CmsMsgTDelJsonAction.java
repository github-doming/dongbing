package all.gen.cms_msg.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_msg.t.service.CmsMsgTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsMsgTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsMsgTService service = new CmsMsgTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
