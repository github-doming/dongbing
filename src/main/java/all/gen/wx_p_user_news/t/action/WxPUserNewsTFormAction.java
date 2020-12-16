package all.gen.wx_p_user_news.t.action;

import all.gen.wx_p_user_news.t.entity.WxPUserNewsT;
import all.gen.wx_p_user_news.t.service.WxPUserNewsTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPUserNewsTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPUserNewsTService service = new WxPUserNewsTService();
			WxPUserNewsT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
