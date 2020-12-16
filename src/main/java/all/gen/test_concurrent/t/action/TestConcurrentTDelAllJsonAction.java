package all.gen.test_concurrent.t.action;
import all.gen.test_concurrent.t.service.TestConcurrentTService;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		TestConcurrentTService service = new TestConcurrentTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
