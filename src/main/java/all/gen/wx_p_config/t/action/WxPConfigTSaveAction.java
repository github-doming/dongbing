package all.gen.wx_p_config.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_config.t.entity.WxPConfigT;
import all.gen.wx_p_config.t.service.WxPConfigTService;
import all.gen.wx_p_config.t.vo.WxPConfigTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPConfigTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPConfigTService service = new WxPConfigTService();
		WxPConfigT entity = null;
		String id = request.getParameter("wx_p_config.wxPConfigId");
		entity = (WxPConfigT) RequestThreadLocal.doRequest2Entity(WxPConfigTVo.class,WxPConfigT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
