package all.gen.alipay_log_auth.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.alipay_log_auth.t.entity.AlipayLogAuthT;
import all.gen.alipay_log_auth.t.service.AlipayLogAuthTService;
import all.gen.alipay_log_auth.t.vo.AlipayLogAuthTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogAuthTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AlipayLogAuthTService service = new AlipayLogAuthTService();
		AlipayLogAuthT entity = null;
		String id = request.getParameter("alipay_log_auth.alipayLogNotifyId");
		entity = (AlipayLogAuthT) RequestThreadLocal.doRequest2Entity(AlipayLogAuthTVo.class,AlipayLogAuthT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
