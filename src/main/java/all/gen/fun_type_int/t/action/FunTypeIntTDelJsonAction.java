package all.gen.fun_type_int.t.action;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.gen.fun_type_int.t.service.FunTypeIntTService;
import c.a.config.SysConfig;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeIntTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		FunTypeIntTService service = new FunTypeIntTService();
		service.del(id);
		
		return this.returnJson(true, "删除成功");
		
		

	}
}
