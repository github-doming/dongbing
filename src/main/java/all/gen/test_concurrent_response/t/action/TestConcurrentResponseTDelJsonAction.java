package all.gen.test_concurrent_response.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.test_concurrent_response.t.service.TestConcurrentResponseTService;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentResponseTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		TestConcurrentResponseTService service = new TestConcurrentResponseTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
