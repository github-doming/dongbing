package all.gen.test_concurrent.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.test_concurrent.t.entity.TestConcurrentT;
import all.gen.test_concurrent.t.service.TestConcurrentTService;
import all.gen.test_concurrent.t.vo.TestConcurrentTVo;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TestConcurrentTService service = new TestConcurrentTService();
		TestConcurrentT entity = null;
		String id = request.getParameter("id");
		entity = (TestConcurrentT) RequestThreadLocal.doRequest2EntityByJson(TestConcurrentTVo.class, TestConcurrentT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
