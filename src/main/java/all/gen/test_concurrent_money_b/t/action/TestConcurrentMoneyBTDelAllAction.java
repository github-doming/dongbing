package all.gen.test_concurrent_money_b.t.action;
import all.gen.test_concurrent_money_b.t.service.TestConcurrentMoneyBTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentMoneyBTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		TestConcurrentMoneyBTService service = new TestConcurrentMoneyBTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
