package all.gen.alipay_config.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.alipay_config.t.entity.AlipayConfigT;
import all.gen.alipay_config.t.service.AlipayConfigTService;
import all.gen.alipay_config.t.vo.AlipayConfigTVo;
import c.x.platform.root.common.action.BaseAction;
public class AlipayConfigTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AlipayConfigTService service = new AlipayConfigTService();
		AlipayConfigT entity = null;
		String id = request.getParameter("id");
		entity = (AlipayConfigT) RequestThreadLocal.doRequest2EntityByJson(AlipayConfigTVo.class, AlipayConfigT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
