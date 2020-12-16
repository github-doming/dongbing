package all.gen.app_group.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_group.t.entity.AppGroupT;
import all.gen.app_group.t.service.AppGroupTService;
import all.gen.app_group.t.vo.AppGroupTVo;
import c.x.platform.root.common.action.BaseAction;
public class AppGroupTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppGroupTService service = new AppGroupTService();
		AppGroupT entity = null;
		String id = request.getParameter("id");
		entity = (AppGroupT) RequestThreadLocal.doRequest2EntityByJson(AppGroupTVo.class, AppGroupT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
