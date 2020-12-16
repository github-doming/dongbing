package c.a.tools.mvc.test;

import c.a.tools.mvc.action.MvcAction;
import c.a.util.core.json.JsonTcpBean;

public class TestAction extends MvcAction {

	@Override
	public JsonTcpBean executeMvc() throws Exception {
		return this.returnJsonTcpBean("test");
	}

	@Override
	public JsonTcpBean executeMvcAsynchronous() throws Exception {
		return null;
	}

}
