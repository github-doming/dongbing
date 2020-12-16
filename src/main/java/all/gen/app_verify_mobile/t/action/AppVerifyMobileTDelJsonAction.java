package all.gen.app_verify_mobile.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_verify_mobile.t.service.AppVerifyMobileTService;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyMobileTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppVerifyMobileTService service = new AppVerifyMobileTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
