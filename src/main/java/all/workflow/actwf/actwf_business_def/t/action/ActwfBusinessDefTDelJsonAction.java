package all.workflow.actwf.actwf_business_def.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.workflow.actwf.actwf_business_def.t.service.ActwfBusinessDefTService;
import c.x.platform.root.common.action.BaseAction;
public class ActwfBusinessDefTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		ActwfBusinessDefTService service = new ActwfBusinessDefTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
