package all.gen.wx_p_mt_article.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_p_mt_article.t.service.WxPMtArticleTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtArticleTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxPMtArticleTService service = new WxPMtArticleTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
