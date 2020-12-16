package all.gen.test_concurrent_money_b.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.test_concurrent_money_b.t.entity.TestConcurrentMoneyBT;
import all.gen.test_concurrent_money_b.t.service.TestConcurrentMoneyBTService;
import all.gen.test_concurrent_money_b.t.vo.TestConcurrentMoneyBTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentMoneyBTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TestConcurrentMoneyBTService service = new TestConcurrentMoneyBTService();
		TestConcurrentMoneyBT entity = null;
		String id = request.getParameter("test_concurrent_money_b.testConcurrentMoneyBId");
		entity = (TestConcurrentMoneyBT) RequestThreadLocal.doRequest2Entity(TestConcurrentMoneyBTVo.class,TestConcurrentMoneyBT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
