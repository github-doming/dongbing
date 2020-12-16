package all.gen.test_concurrent_request.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.test_concurrent_request.t.entity.TestConcurrentRequestT;
import all.gen.test_concurrent_request.t.service.TestConcurrentRequestTService;
import all.gen.test_concurrent_request.t.vo.TestConcurrentRequestTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentRequestTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TestConcurrentRequestTService service = new TestConcurrentRequestTService();
		TestConcurrentRequestT entity = null;
		String id = request.getParameter("test_concurrent_request.testConcurrentRequestId");
		entity = (TestConcurrentRequestT) RequestThreadLocal.doRequest2Entity(TestConcurrentRequestTVo.class,TestConcurrentRequestT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
