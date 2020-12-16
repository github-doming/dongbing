package all.app_admin.app.account_user.action;

import all.app_admin.app.account_user.service.AppAccountUserService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_account.t.vo.AppAccountTVo;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class AppAccountUserSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppAccountUserService service = new AppAccountUserService();
		AppAccountT entity = null;
		String id = request.getParameter("id");
		entity = (AppAccountT) RequestThreadLocal.doRequest2EntityByJson(AppAccountTVo.class, AppAccountT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
