package all.gen.app_config.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_config.t.entity.AppConfigT;
import all.gen.app_config.t.service.AppConfigTService;
import all.gen.app_config.t.vo.AppConfigTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppConfigTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppConfigTService service = new AppConfigTService();
		AppConfigT entity = null;
		String id = request.getParameter("app_config.appConfigId");
		entity = (AppConfigT) RequestThreadLocal.doRequest2Entity(AppConfigTVo.class,AppConfigT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
