package all.gen.app_token.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_token.t.entity.AppTokenT;
import all.gen.app_token.t.service.AppTokenTService;
import all.gen.app_token.t.vo.AppTokenTVo;
import c.x.platform.root.common.action.BaseAction;
public class AppTokenTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppTokenTService service = new AppTokenTService();
		AppTokenT entity = null;
		String id = request.getParameter("id");
		entity = (AppTokenT) RequestThreadLocal.doRequest2EntityByJson(AppTokenTVo.class, AppTokenT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
