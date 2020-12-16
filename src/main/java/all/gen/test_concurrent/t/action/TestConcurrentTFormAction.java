package all.gen.test_concurrent.t.action;

import all.gen.test_concurrent.t.entity.TestConcurrentT;
import all.gen.test_concurrent.t.service.TestConcurrentTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			TestConcurrentTService service = new TestConcurrentTService();
			TestConcurrentT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
