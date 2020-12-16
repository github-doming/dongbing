package c.x.platform.portal.sys.menu.action;
import c.a.config.SysConfig;
import c.a.tools.crud.action.TransactionAction;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.portal.sys.menu.service.SysMenuInfoService;
public class SysMenuInfoDelAction extends TransactionAction {
	public SysMenuInfoDelAction() {
		transaction = true;
	}
	@Override
	public JsonTcpBean executeTransaction() throws Exception {
		String returnStr = SysConfig.configValueTrue;
		return this.returnJsonTcpBean(returnStr);
	}
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysMenuInfoService service = new SysMenuInfoService();
		service.del(id);
		return "index";
	}
}
