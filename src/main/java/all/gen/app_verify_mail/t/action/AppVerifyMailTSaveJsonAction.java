package all.gen.app_verify_mail.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_verify_mail.t.entity.AppVerifyMailT;
import all.gen.app_verify_mail.t.service.AppVerifyMailTService;
import all.gen.app_verify_mail.t.vo.AppVerifyMailTVo;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyMailTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppVerifyMailTService service = new AppVerifyMailTService();
		AppVerifyMailT entity = null;
		String id = request.getParameter("id");
		entity = (AppVerifyMailT) RequestThreadLocal.doRequest2EntityByJson(AppVerifyMailTVo.class, AppVerifyMailT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
