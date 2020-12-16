package all.gen.wx_p_news_article.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_p_news_article.t.service.WxPNewsArticleTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPNewsArticleTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxPNewsArticleTService service = new WxPNewsArticleTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
