package all.gen.wx_p_user.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_user.t.entity.WxPUserT;
import all.gen.wx_p_user.t.service.WxPUserTService;
import all.gen.wx_p_user.t.vo.WxPUserTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxPUserTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPUserTService service = new WxPUserTService();
		WxPUserT entity = null;
		String id = request.getParameter("id");
		entity = (WxPUserT) RequestThreadLocal.doRequest2EntityByJson(WxPUserTVo.class, WxPUserT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
