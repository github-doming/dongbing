package all.gen.app_account.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_account.t.service.AppAccountTService;
import all.gen.app_account.t.vo.AppAccountTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppAccountTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppAccountTService service = new AppAccountTService();
		AppAccountT entity = null;
		String id = request.getParameter("app_account.appAccountId");
		entity = (AppAccountT) RequestThreadLocal.doRequest2Entity(AppAccountTVo.class,AppAccountT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
