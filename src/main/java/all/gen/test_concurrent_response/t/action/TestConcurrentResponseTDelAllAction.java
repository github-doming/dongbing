package all.gen.test_concurrent_response.t.action;
import all.gen.test_concurrent_response.t.service.TestConcurrentResponseTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentResponseTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		TestConcurrentResponseTService service = new TestConcurrentResponseTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
