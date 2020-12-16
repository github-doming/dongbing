package all.gen.test_concurrent_money_b.t.action;

import all.gen.test_concurrent_money_b.t.entity.TestConcurrentMoneyBT;
import all.gen.test_concurrent_money_b.t.service.TestConcurrentMoneyBTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentMoneyBTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			TestConcurrentMoneyBTService service = new TestConcurrentMoneyBTService();
			TestConcurrentMoneyBT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
