package all.gen.alipay_log_notify.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.alipay_log_notify.t.service.AlipayLogNotifyTService;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogNotifyTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AlipayLogNotifyTService service = new AlipayLogNotifyTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
