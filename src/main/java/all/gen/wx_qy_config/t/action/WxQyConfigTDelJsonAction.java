package all.gen.wx_qy_config.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_qy_config.t.service.WxQyConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class WxQyConfigTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxQyConfigTService service = new WxQyConfigTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
