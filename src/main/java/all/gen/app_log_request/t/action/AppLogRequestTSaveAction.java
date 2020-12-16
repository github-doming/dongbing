package all.gen.app_log_request.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_log_request.t.entity.AppLogRequestT;
import all.gen.app_log_request.t.service.AppLogRequestTService;
import all.gen.app_log_request.t.vo.AppLogRequestTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppLogRequestTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppLogRequestTService service = new AppLogRequestTService();
		AppLogRequestT entity = null;
		String id = request.getParameter("app_log_request.appLogRequestId");
		entity = (AppLogRequestT) RequestThreadLocal.doRequest2Entity(AppLogRequestTVo.class,AppLogRequestT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
