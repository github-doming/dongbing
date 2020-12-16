package all.gen.app_user.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_user.t.entity.AppUserT;
import all.gen.app_user.t.service.AppUserTService;
import all.gen.app_user.t.vo.AppUserTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppUserTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppUserTService service = new AppUserTService();
		AppUserT entity = null;
		String id = request.getParameter("app_user.appUserId");
		entity = (AppUserT) RequestThreadLocal.doRequest2Entity(AppUserTVo.class,AppUserT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
