package all.gen.alipay_config.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.alipay_config.t.service.AlipayConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class AlipayConfigTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AlipayConfigTService service = new AlipayConfigTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
