package all.sys_admin.sys.sys_account.t.action;
import all.gen.sys_account.t.entity.SysAccountT;
import all.sys_admin.sys.sys_account.t.service.SysAccountService;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountCxFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysAccountService service = new SysAccountService();
			SysAccountT account = service.find(id);
			request.setAttribute("id", id);
			//decode密码
			String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
			String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
					"");
			if ("true".equals(commLocalASE)) {
				String password = CommASEUtil.decode(commLocalASE_key, account.getPassword().trim()).trim();
				account.setPassword(password);
			}
			request.setAttribute("s", account);
		}
		return CommViewEnum.Default.toString();
	}
}
