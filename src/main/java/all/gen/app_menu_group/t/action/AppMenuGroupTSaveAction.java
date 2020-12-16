package all.gen.app_menu_group.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_menu_group.t.entity.AppMenuGroupT;
import all.gen.app_menu_group.t.service.AppMenuGroupTService;
import all.gen.app_menu_group.t.vo.AppMenuGroupTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppMenuGroupTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppMenuGroupTService service = new AppMenuGroupTService();
		AppMenuGroupT entity = null;
		String id = request.getParameter("app_menu_group.appMenuGroupId");
		entity = (AppMenuGroupT) RequestThreadLocal.doRequest2Entity(AppMenuGroupTVo.class,AppMenuGroupT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
