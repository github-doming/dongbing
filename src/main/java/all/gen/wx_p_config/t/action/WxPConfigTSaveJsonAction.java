package all.gen.wx_p_config.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_config.t.entity.WxPConfigT;
import all.gen.wx_p_config.t.service.WxPConfigTService;
import all.gen.wx_p_config.t.vo.WxPConfigTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxPConfigTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPConfigTService service = new WxPConfigTService();
		WxPConfigT entity = null;
		String id = request.getParameter("id");
		entity = (WxPConfigT) RequestThreadLocal.doRequest2EntityByJson(WxPConfigTVo.class, WxPConfigT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
