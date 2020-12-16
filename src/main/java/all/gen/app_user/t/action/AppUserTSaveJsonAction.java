package all.gen.app_user.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_user.t.entity.AppUserT;
import all.gen.app_user.t.service.AppUserTService;
import all.gen.app_user.t.vo.AppUserTVo;
import c.x.platform.root.common.action.BaseAction;
public class AppUserTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppUserTService service = new AppUserTService();
		AppUserT entity = null;
		String id = request.getParameter("id");
		entity = (AppUserT) RequestThreadLocal.doRequest2EntityByJson(AppUserTVo.class, AppUserT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
