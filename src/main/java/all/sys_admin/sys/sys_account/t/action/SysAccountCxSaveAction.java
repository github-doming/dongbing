package all.sys_admin.sys.sys_account.t.action;
import all.gen.sys_account.t.entity.SysAccountT;
import all.gen.sys_account.t.vo.SysAccountTVo;
import all.sys_admin.sys.sys_account.t.service.SysAccountService;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountCxSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_account.sysAccountId");
		SysAccountT entity = (SysAccountT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysAccountTVo.class, SysAccountT.class, request);
		SysAccountService service = new SysAccountService();
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
