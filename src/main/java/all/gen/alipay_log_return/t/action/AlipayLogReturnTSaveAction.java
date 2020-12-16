package all.gen.alipay_log_return.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.alipay_log_return.t.entity.AlipayLogReturnT;
import all.gen.alipay_log_return.t.service.AlipayLogReturnTService;
import all.gen.alipay_log_return.t.vo.AlipayLogReturnTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogReturnTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AlipayLogReturnTService service = new AlipayLogReturnTService();
		AlipayLogReturnT entity = null;
		String id = request.getParameter("alipay_log_return.alipayLogReturnId");
		entity = (AlipayLogReturnT) RequestThreadLocal.doRequest2Entity(AlipayLogReturnTVo.class,AlipayLogReturnT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
