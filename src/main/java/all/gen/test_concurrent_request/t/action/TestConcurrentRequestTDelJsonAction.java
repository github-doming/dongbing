package all.gen.test_concurrent_request.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.test_concurrent_request.t.service.TestConcurrentRequestTService;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentRequestTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		TestConcurrentRequestTService service = new TestConcurrentRequestTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
