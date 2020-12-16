package all.gen.test_concurrent_money_a.t.action;

import all.gen.test_concurrent_money_a.t.entity.TestConcurrentMoneyAT;
import all.gen.test_concurrent_money_a.t.service.TestConcurrentMoneyATService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentMoneyATFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			TestConcurrentMoneyATService service = new TestConcurrentMoneyATService();
			TestConcurrentMoneyAT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
