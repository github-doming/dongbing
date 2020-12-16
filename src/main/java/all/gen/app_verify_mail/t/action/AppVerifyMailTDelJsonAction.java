package all.gen.app_verify_mail.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_verify_mail.t.service.AppVerifyMailTService;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyMailTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppVerifyMailTService service = new AppVerifyMailTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
