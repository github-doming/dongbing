package all.workflow.actwf.actwf_form_def.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.workflow.actwf.actwf_form_def.t.service.ActwfFormDefTService;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFormDefTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		ActwfFormDefTService service = new ActwfFormDefTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
