package all.app_admin.app.app_group.t.action;
import all.app_admin.app.app_group.t.service.AppGroupTService;
import all.gen.app_group.t.entity.AppGroupT;
import all.gen.app_group.t.vo.AppGroupTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class AppGroupTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppGroupTService service = new AppGroupTService();
		AppGroupT entity = null;
		String id = request.getParameter("app_group.appGroupId");
		entity = (AppGroupT) RequestThreadLocal.doRequest2Entity(AppGroupTVo.class,AppGroupT.class, request);
		String tenantCode=this.findCurrentUserTenantCode();
		entity.setTenantCode(tenantCode);
		entity.setState(UserStateEnum.OPEN.getCode());
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
