package all.gen.alipay_log_notify.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.alipay_log_notify.t.entity.AlipayLogNotifyT;
import all.gen.alipay_log_notify.t.service.AlipayLogNotifyTService;
import all.gen.alipay_log_notify.t.vo.AlipayLogNotifyTVo;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogNotifyTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AlipayLogNotifyTService service = new AlipayLogNotifyTService();
		AlipayLogNotifyT entity = null;
		String id = request.getParameter("id");
		entity = (AlipayLogNotifyT) RequestThreadLocal.doRequest2EntityByJson(AlipayLogNotifyTVo.class, AlipayLogNotifyT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
