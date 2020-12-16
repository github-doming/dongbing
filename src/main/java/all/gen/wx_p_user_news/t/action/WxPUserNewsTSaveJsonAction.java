package all.gen.wx_p_user_news.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_user_news.t.entity.WxPUserNewsT;
import all.gen.wx_p_user_news.t.service.WxPUserNewsTService;
import all.gen.wx_p_user_news.t.vo.WxPUserNewsTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxPUserNewsTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPUserNewsTService service = new WxPUserNewsTService();
		WxPUserNewsT entity = null;
		String id = request.getParameter("id");
		entity = (WxPUserNewsT) RequestThreadLocal.doRequest2EntityByJson(WxPUserNewsTVo.class, WxPUserNewsT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
