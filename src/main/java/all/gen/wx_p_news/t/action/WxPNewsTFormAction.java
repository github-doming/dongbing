package all.gen.wx_p_news.t.action;

import all.gen.wx_p_news.t.entity.WxPNewsT;
import all.gen.wx_p_news.t.service.WxPNewsTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPNewsTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPNewsTService service = new WxPNewsTService();
			WxPNewsT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
