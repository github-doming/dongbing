package c.x.platform.root.layout.main.action;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysMainDefaultAction extends BaseAction {
	public SysMainDefaultAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		SysMainAction mainAction = new SysMainAction();
		mainAction.setRequest(request);
		mainAction.setResponse(response);
		mainAction.execute();
		return CommViewEnum.Default.toString();
	}

}
