package all.gen.app_verify_mobile.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_verify_mobile.t.entity.AppVerifyMobileT;
import all.gen.app_verify_mobile.t.service.AppVerifyMobileTService;
import all.gen.app_verify_mobile.t.vo.AppVerifyMobileTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyMobileTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppVerifyMobileTService service = new AppVerifyMobileTService();
		AppVerifyMobileT entity = null;
		String id = request.getParameter("app_verify_mobile.appVerifyMobileId");
		entity = (AppVerifyMobileT) RequestThreadLocal.doRequest2Entity(AppVerifyMobileTVo.class,AppVerifyMobileT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
