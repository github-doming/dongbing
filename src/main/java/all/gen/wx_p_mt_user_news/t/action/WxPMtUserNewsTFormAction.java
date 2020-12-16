package all.gen.wx_p_mt_user_news.t.action;

import all.gen.wx_p_mt_user_news.t.entity.WxPMtUserNewsT;
import all.gen.wx_p_mt_user_news.t.service.WxPMtUserNewsTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtUserNewsTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPMtUserNewsTService service = new WxPMtUserNewsTService();
			WxPMtUserNewsT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
