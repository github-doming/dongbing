package all.gen.alipay_config.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.alipay_config.t.entity.AlipayConfigT;
import all.gen.alipay_config.t.service.AlipayConfigTService;
import all.gen.alipay_config.t.vo.AlipayConfigTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayConfigTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AlipayConfigTService service = new AlipayConfigTService();
		AlipayConfigT entity = null;
		String id = request.getParameter("alipay_config.alipayConfigId");
		entity = (AlipayConfigT) RequestThreadLocal.doRequest2Entity(AlipayConfigTVo.class,AlipayConfigT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
