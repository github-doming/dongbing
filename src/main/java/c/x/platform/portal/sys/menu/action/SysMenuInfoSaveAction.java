package c.x.platform.portal.sys.menu.action;
import all.gen.sys_menu.t.entity.SysMenuT;
import c.a.config.SysConfig;
import c.a.tools.crud.action.TransactionAction;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.portal.sys.menu.service.SysMenuInfoService;
public class SysMenuInfoSaveAction extends TransactionAction {
	public SysMenuInfoSaveAction() {
		transaction = true;
	}
	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		String returnStr = SysConfig.configValueTrue;
		return this.returnJsonTcpBean(returnStr);
	}
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_menu_info.id");
		SysMenuT entity = (SysMenuT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysMenuT.class, SysMenuT.class, request);
		SysMenuInfoService service = new SysMenuInfoService();
		if (StringUtil.isBlank(id)) {
			service.insert(entity);
		} else {
			service.update(entity);
		}
		return "index";
	}
}
