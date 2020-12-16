package all.ui.easyui.layout.menu;
import c.x.platform.root.common.action.BaseAction;
public class EasyuiMenuListJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String type = this.findCurrentUserType();
		if ("sys".equals(type)) {
			EasyuiSysMenuCommListJsonAction menuAction=new EasyuiSysMenuCommListJsonAction();
			menuAction.setRequest(request);
			menuAction.setResponse(response);
			return menuAction.execute();
		}
		if ("app".equals(type)) {
			EasyuiAppMenuCommListJsonAction menuAction=new EasyuiAppMenuCommListJsonAction();
			menuAction.setRequest(request);
			menuAction.setResponse(response);
			return menuAction.execute();
		}
		return null;
	}
}
