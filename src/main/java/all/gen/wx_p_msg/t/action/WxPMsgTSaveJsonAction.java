package all.gen.wx_p_msg.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_msg.t.entity.WxPMsgT;
import all.gen.wx_p_msg.t.service.WxPMsgTService;
import all.gen.wx_p_msg.t.vo.WxPMsgTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxPMsgTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMsgTService service = new WxPMsgTService();
		WxPMsgT entity = null;
		String id = request.getParameter("id");
		entity = (WxPMsgT) RequestThreadLocal.doRequest2EntityByJson(WxPMsgTVo.class, WxPMsgT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
