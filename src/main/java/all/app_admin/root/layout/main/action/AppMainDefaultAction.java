package all.app_admin.root.layout.main.action;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppMainDefaultAction extends BaseAction {
	public AppMainDefaultAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		AppMainAction mainAction = new AppMainAction();
		mainAction.setRequest(request);
		mainAction.setResponse(response);
		mainAction.execute();
		return CommViewEnum.Default.toString();
	}

}
