package all.gen.alipay_log_auth.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.alipay_log_auth.t.service.AlipayLogAuthTService;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogAuthTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AlipayLogAuthTService service = new AlipayLogAuthTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
