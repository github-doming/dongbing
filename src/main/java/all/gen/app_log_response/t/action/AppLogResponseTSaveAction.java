package all.gen.app_log_response.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_log_response.t.entity.AppLogResponseT;
import all.gen.app_log_response.t.service.AppLogResponseTService;
import all.gen.app_log_response.t.vo.AppLogResponseTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppLogResponseTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppLogResponseTService service = new AppLogResponseTService();
		AppLogResponseT entity = null;
		String id = request.getParameter("app_log_response.appLogResponseId");
		entity = (AppLogResponseT) RequestThreadLocal.doRequest2Entity(AppLogResponseTVo.class,AppLogResponseT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
