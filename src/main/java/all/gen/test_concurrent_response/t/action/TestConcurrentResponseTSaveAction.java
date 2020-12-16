package all.gen.test_concurrent_response.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.test_concurrent_response.t.entity.TestConcurrentResponseT;
import all.gen.test_concurrent_response.t.service.TestConcurrentResponseTService;
import all.gen.test_concurrent_response.t.vo.TestConcurrentResponseTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentResponseTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TestConcurrentResponseTService service = new TestConcurrentResponseTService();
		TestConcurrentResponseT entity = null;
		String id = request.getParameter("test_concurrent_response.testConcurrentResponseId");
		entity = (TestConcurrentResponseT) RequestThreadLocal.doRequest2Entity(TestConcurrentResponseTVo.class,TestConcurrentResponseT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
