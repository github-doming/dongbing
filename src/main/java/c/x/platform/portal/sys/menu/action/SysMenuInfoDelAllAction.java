package c.x.platform.portal.sys.menu.action;
import c.a.config.SysConfig;
import c.a.tools.crud.action.TransactionAction;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.portal.sys.menu.service.SysMenuInfoService;
public class SysMenuInfoDelAllAction extends TransactionAction {
	public SysMenuInfoDelAllAction() {
		transaction = true;
	}
	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		String returnStr = SysConfig.configValueTrue;
		return this.returnJsonTcpBean(returnStr);
	}
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		SysMenuInfoService service = new SysMenuInfoService();
		if (false) {
			for (String id : ids) {
				service.del(id);
			}
		}
		service.delAll(ids);
		return "index";
	}
}
