package all.gen.wx_p_msg.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_p_msg.t.service.WxPMsgTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMsgTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxPMsgTService service = new WxPMsgTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
