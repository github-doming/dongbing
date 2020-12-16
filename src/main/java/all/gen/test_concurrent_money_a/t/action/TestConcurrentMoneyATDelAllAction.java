package all.gen.test_concurrent_money_a.t.action;
import all.gen.test_concurrent_money_a.t.service.TestConcurrentMoneyATService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentMoneyATDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		TestConcurrentMoneyATService service = new TestConcurrentMoneyATService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
