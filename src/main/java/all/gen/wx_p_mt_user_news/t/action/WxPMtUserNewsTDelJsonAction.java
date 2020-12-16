package all.gen.wx_p_mt_user_news.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_p_mt_user_news.t.service.WxPMtUserNewsTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtUserNewsTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxPMtUserNewsTService service = new WxPMtUserNewsTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
