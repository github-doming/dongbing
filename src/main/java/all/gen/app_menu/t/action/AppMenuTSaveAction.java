package all.gen.app_menu.t.action;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.gen.app_menu.t.entity.AppMenuT;
import all.gen.app_menu.t.service.AppMenuTService;
import all.gen.app_menu.t.vo.AppMenuTVo;
public class AppMenuTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppMenuTService service = new AppMenuTService();
		String parent_id = request.getParameter("app_menu.parent");
		String id = request.getParameter("app_menu.appMenuId");
		AppMenuT entity = (AppMenuT) RequestThreadLocal.doRequest2Entity(
		AppMenuTVo.class, AppMenuT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
