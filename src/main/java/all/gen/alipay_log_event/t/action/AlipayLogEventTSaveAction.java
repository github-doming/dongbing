package all.gen.alipay_log_event.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.alipay_log_event.t.entity.AlipayLogEventT;
import all.gen.alipay_log_event.t.service.AlipayLogEventTService;
import all.gen.alipay_log_event.t.vo.AlipayLogEventTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogEventTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AlipayLogEventTService service = new AlipayLogEventTService();
		AlipayLogEventT entity = null;
		String id = request.getParameter("alipay_log_event.alipayLogEventId");
		entity = (AlipayLogEventT) RequestThreadLocal.doRequest2Entity(AlipayLogEventTVo.class,AlipayLogEventT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
