package all.gen.app_verify_account.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_verify_account.t.entity.AppVerifyAccountT;
import all.gen.app_verify_account.t.service.AppVerifyAccountTService;
import all.gen.app_verify_account.t.vo.AppVerifyAccountTVo;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyAccountTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppVerifyAccountTService service = new AppVerifyAccountTService();
		AppVerifyAccountT entity = null;
		String id = request.getParameter("id");
		entity = (AppVerifyAccountT) RequestThreadLocal.doRequest2EntityByJson(AppVerifyAccountTVo.class, AppVerifyAccountT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
