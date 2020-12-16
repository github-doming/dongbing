package all.gen.wx_p_menu.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_menu.t.entity.WxPMenuT;
import all.gen.wx_p_menu.t.service.WxPMenuTService;
import all.gen.wx_p_menu.t.vo.WxPMenuTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMenuTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMenuTService service = new WxPMenuTService();
		WxPMenuT entity = null;
		String id = request.getParameter("wx_p_menu.wxPMenuId");
		entity = (WxPMenuT) RequestThreadLocal.doRequest2Entity(WxPMenuTVo.class,WxPMenuT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
