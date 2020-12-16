package all.gen.fun_type_str.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.fun_type_str.t.service.FunTypeStrTService;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeStrTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		FunTypeStrTService service = new FunTypeStrTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
