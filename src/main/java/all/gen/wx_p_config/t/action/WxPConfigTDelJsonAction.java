package all.gen.wx_p_config.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_p_config.t.service.WxPConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPConfigTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxPConfigTService service = new WxPConfigTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
