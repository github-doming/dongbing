package all.gen.test_concurrent_money_b.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.test_concurrent_money_b.t.service.TestConcurrentMoneyBTService;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentMoneyBTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		TestConcurrentMoneyBTService service = new TestConcurrentMoneyBTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
