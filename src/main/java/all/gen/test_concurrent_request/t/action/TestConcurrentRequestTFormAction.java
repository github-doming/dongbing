package all.gen.test_concurrent_request.t.action;

import all.gen.test_concurrent_request.t.entity.TestConcurrentRequestT;
import all.gen.test_concurrent_request.t.service.TestConcurrentRequestTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentRequestTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			TestConcurrentRequestTService service = new TestConcurrentRequestTService();
			TestConcurrentRequestT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
