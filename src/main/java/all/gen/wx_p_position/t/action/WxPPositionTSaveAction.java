package all.gen.wx_p_position.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_position.t.entity.WxPPositionT;
import all.gen.wx_p_position.t.service.WxPPositionTService;
import all.gen.wx_p_position.t.vo.WxPPositionTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPPositionTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPPositionTService service = new WxPPositionTService();
		WxPPositionT entity = null;
		String id = request.getParameter("wx_p_position.wxPPositionId");
		entity = (WxPPositionT) RequestThreadLocal.doRequest2Entity(WxPPositionTVo.class,WxPPositionT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
