package c.x.platform.sys.sys_account.cx.action;
import all.gen.sys_account.t.entity.SysAccountT;
import all.gen.sys_account.t.vo.SysAccountTVo;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import c.x.platform.sys.sys_account.cx.service.SysAccountCxService;
public class SysAccountCxSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_account.sysAccountId");
		SysAccountT entity = (SysAccountT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysAccountTVo.class, SysAccountT.class, request);
		SysAccountCxService service = new SysAccountCxService();
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
