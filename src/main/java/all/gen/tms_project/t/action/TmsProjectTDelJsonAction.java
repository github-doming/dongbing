package all.gen.tms_project.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.tms_project.t.service.TmsProjectTService;
import c.x.platform.root.common.action.BaseAction;
public class TmsProjectTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		TmsProjectTService service = new TmsProjectTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
