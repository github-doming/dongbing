package all.gen.alipay_log_event.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.alipay_log_event.t.entity.AlipayLogEventT;
import all.gen.alipay_log_event.t.service.AlipayLogEventTService;
import all.gen.alipay_log_event.t.vo.AlipayLogEventTVo;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogEventTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AlipayLogEventTService service = new AlipayLogEventTService();
		AlipayLogEventT entity = null;
		String id = request.getParameter("id");
		entity = (AlipayLogEventT) RequestThreadLocal.doRequest2EntityByJson(AlipayLogEventTVo.class, AlipayLogEventT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
