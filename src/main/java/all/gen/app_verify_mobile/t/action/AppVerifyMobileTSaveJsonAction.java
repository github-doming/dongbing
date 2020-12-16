package all.gen.app_verify_mobile.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_verify_mobile.t.entity.AppVerifyMobileT;
import all.gen.app_verify_mobile.t.service.AppVerifyMobileTService;
import all.gen.app_verify_mobile.t.vo.AppVerifyMobileTVo;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyMobileTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppVerifyMobileTService service = new AppVerifyMobileTService();
		AppVerifyMobileT entity = null;
		String id = request.getParameter("id");
		entity = (AppVerifyMobileT) RequestThreadLocal.doRequest2EntityByJson(AppVerifyMobileTVo.class, AppVerifyMobileT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
