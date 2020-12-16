package all.gen.alipay_log_event.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.alipay_log_event.t.service.AlipayLogEventTService;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogEventTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AlipayLogEventTService service = new AlipayLogEventTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
