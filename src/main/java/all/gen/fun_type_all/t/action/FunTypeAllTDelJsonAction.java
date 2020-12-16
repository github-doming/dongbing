package all.gen.fun_type_all.t.action;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.gen.fun_type_all.t.service.FunTypeAllTService;
import c.a.config.SysConfig;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeAllTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		FunTypeAllTService service = new FunTypeAllTService();
		service.del(id);
		
		return this.returnJson(true, "删除成功");
		
		

	}
}
