package all.workflow.actwf.actwf_flow_def.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.workflow.actwf.actwf_flow_def.t.service.ActwfFlowDefTService;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFlowDefTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		ActwfFlowDefTService service = new ActwfFlowDefTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
