package all.gen.wx_p_position.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_p_position.t.service.WxPPositionTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPPositionTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxPPositionTService service = new WxPPositionTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
