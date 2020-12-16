package all.gen.app_log.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_log.t.entity.AppLogT;
import all.gen.app_log.t.service.AppLogTService;
import all.gen.app_log.t.vo.AppLogTVo;
import c.x.platform.root.common.action.BaseAction;
public class AppLogTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppLogTService service = new AppLogTService();
		AppLogT entity = null;
		String id = request.getParameter("id");
		entity = (AppLogT) RequestThreadLocal.doRequest2EntityByJson(AppLogTVo.class, AppLogT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
