package all.gen.sys_request_token.t.action;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.gen.sys_request_token.t.service.SysRequestTokenTService;
import c.a.config.SysConfig;
import c.x.platform.root.common.action.BaseAction;
public class SysRequestTokenTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysRequestTokenTService service = new SysRequestTokenTService();
		service.del(id);
		
		return this.returnJson(true, "删除成功");
		
		

	}
}
