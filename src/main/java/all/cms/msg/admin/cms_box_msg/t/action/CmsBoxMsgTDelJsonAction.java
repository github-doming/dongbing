package all.cms.msg.admin.cms_box_msg.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.cms.msg.admin.cms_box_msg.t.service.CmsBoxMsgTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxMsgTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsBoxMsgTService service = new CmsBoxMsgTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
