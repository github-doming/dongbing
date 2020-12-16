package all.gen.wx_p_menu.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_p_menu.t.service.WxPMenuTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMenuTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxPMenuTService service = new WxPMenuTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
