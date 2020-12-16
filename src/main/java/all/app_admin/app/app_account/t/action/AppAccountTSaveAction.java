package all.app_admin.app.app_account.t.action;
import all.app_admin.app.app_account.t.service.AppAccountTService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_account.t.vo.AppAccountTVo;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class AppAccountTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppAccountTService service = new AppAccountTService();
		AppAccountT entity = null;
		String id = request.getParameter("app_account.appAccountId");
		entity = (AppAccountT) RequestThreadLocal.doRequest2Entity(AppAccountTVo.class, AppAccountT.class, request);
		String tenantCode = this.findCurrentUserTenantCode();
		entity.setTenantCode(tenantCode);
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalASE)) {
			String password = CommASEUtil.encode(commLocalASE_key, entity.getPassword().trim()).trim();
			entity.setPassword(password);
		}
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
