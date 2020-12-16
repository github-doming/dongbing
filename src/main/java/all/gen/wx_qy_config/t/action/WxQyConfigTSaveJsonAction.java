package all.gen.wx_qy_config.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_qy_config.t.entity.WxQyConfigT;
import all.gen.wx_qy_config.t.service.WxQyConfigTService;
import all.gen.wx_qy_config.t.vo.WxQyConfigTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxQyConfigTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxQyConfigTService service = new WxQyConfigTService();
		WxQyConfigT entity = null;
		String id = request.getParameter("id");
		entity = (WxQyConfigT) RequestThreadLocal.doRequest2EntityByJson(WxQyConfigTVo.class, WxQyConfigT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
