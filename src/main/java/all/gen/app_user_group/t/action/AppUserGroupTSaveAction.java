package all.gen.app_user_group.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_user_group.t.entity.AppUserGroupT;
import all.gen.app_user_group.t.service.AppUserGroupTService;
import all.gen.app_user_group.t.vo.AppUserGroupTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppUserGroupTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppUserGroupTService service = new AppUserGroupTService();
		AppUserGroupT entity = null;
		String id = request.getParameter("app_user_group.appUserGroupId");
		entity = (AppUserGroupT) RequestThreadLocal.doRequest2Entity(AppUserGroupTVo.class,AppUserGroupT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
