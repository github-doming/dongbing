package all.gen.wx_p_mt_news.t.action;

import all.gen.wx_p_mt_news.t.entity.WxPMtNewsT;
import all.gen.wx_p_mt_news.t.service.WxPMtNewsTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtNewsTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPMtNewsTService service = new WxPMtNewsTService();
			WxPMtNewsT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
