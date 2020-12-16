package all.gen.app_user_group.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_user_group.t.entity.AppUserGroupT;
import all.gen.app_user_group.t.service.AppUserGroupTService;
import all.gen.app_user_group.t.vo.AppUserGroupTVo;
import c.x.platform.root.common.action.BaseAction;
public class AppUserGroupTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppUserGroupTService service = new AppUserGroupTService();
		AppUserGroupT entity = null;
		String id = request.getParameter("id");
		entity = (AppUserGroupT) RequestThreadLocal.doRequest2EntityByJson(AppUserGroupTVo.class, AppUserGroupT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
