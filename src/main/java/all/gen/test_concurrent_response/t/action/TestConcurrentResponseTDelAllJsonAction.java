package all.gen.test_concurrent_response.t.action;
import all.gen.test_concurrent_response.t.service.TestConcurrentResponseTService;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentResponseTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		TestConcurrentResponseTService service = new TestConcurrentResponseTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
