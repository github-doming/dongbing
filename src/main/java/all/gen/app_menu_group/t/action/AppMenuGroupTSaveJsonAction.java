package all.gen.app_menu_group.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_menu_group.t.entity.AppMenuGroupT;
import all.gen.app_menu_group.t.service.AppMenuGroupTService;
import all.gen.app_menu_group.t.vo.AppMenuGroupTVo;
import c.x.platform.root.common.action.BaseAction;
public class AppMenuGroupTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppMenuGroupTService service = new AppMenuGroupTService();
		AppMenuGroupT entity = null;
		String id = request.getParameter("id");
		entity = (AppMenuGroupT) RequestThreadLocal.doRequest2EntityByJson(AppMenuGroupTVo.class, AppMenuGroupT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
