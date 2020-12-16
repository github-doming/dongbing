package all.gen.wx_p_mo_text.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_p_mo_text.t.service.WxPMoTextTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMoTextTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxPMoTextTService service = new WxPMoTextTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
