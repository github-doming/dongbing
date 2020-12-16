package all.gen.wx_p_user.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_user.t.entity.WxPUserT;
import all.gen.wx_p_user.t.service.WxPUserTService;
import all.gen.wx_p_user.t.vo.WxPUserTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPUserTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPUserTService service = new WxPUserTService();
		WxPUserT entity = null;
		String id = request.getParameter("wx_p_user.wxPUserId");
		entity = (WxPUserT) RequestThreadLocal.doRequest2Entity(WxPUserTVo.class,WxPUserT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
