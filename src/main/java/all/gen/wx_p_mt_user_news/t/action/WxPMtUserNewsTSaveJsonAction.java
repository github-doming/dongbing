package all.gen.wx_p_mt_user_news.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_mt_user_news.t.entity.WxPMtUserNewsT;
import all.gen.wx_p_mt_user_news.t.service.WxPMtUserNewsTService;
import all.gen.wx_p_mt_user_news.t.vo.WxPMtUserNewsTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtUserNewsTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMtUserNewsTService service = new WxPMtUserNewsTService();
		WxPMtUserNewsT entity = null;
		String id = request.getParameter("id");
		entity = (WxPMtUserNewsT) RequestThreadLocal.doRequest2EntityByJson(WxPMtUserNewsTVo.class, WxPMtUserNewsT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
