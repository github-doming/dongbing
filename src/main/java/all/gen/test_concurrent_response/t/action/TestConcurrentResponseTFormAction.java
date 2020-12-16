package all.gen.test_concurrent_response.t.action;

import all.gen.test_concurrent_response.t.entity.TestConcurrentResponseT;
import all.gen.test_concurrent_response.t.service.TestConcurrentResponseTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentResponseTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			TestConcurrentResponseTService service = new TestConcurrentResponseTService();
			TestConcurrentResponseT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
