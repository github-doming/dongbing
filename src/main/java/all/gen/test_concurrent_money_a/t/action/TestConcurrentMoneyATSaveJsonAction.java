package all.gen.test_concurrent_money_a.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.test_concurrent_money_a.t.entity.TestConcurrentMoneyAT;
import all.gen.test_concurrent_money_a.t.service.TestConcurrentMoneyATService;
import all.gen.test_concurrent_money_a.t.vo.TestConcurrentMoneyATVo;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentMoneyATSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TestConcurrentMoneyATService service = new TestConcurrentMoneyATService();
		TestConcurrentMoneyAT entity = null;
		String id = request.getParameter("id");
		entity = (TestConcurrentMoneyAT) RequestThreadLocal.doRequest2EntityByJson(TestConcurrentMoneyATVo.class, TestConcurrentMoneyAT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
