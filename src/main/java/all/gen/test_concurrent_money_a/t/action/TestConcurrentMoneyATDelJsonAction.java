package all.gen.test_concurrent_money_a.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.test_concurrent_money_a.t.service.TestConcurrentMoneyATService;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentMoneyATDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		TestConcurrentMoneyATService service = new TestConcurrentMoneyATService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
