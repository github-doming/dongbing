package c.x.platform.portal.layout.index.action;

import c.a.tools.mvc.action.MvcAction;
import c.a.util.core.json.JsonTcpBean;

public class IndexAction extends MvcAction {
	@Override
	public JsonTcpBean executeMvc() throws Exception {
		return this.returnJsonTcpBean("index");
	}
}
