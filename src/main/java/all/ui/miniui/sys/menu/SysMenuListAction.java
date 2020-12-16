package all.ui.miniui.sys.menu;
import c.x.platform.root.common.action.BaseAction;
public class SysMenuListAction extends BaseAction {

	@Override
	public String execute() throws Exception {

		System.out.println("2 class=" + this.getClass());
		return "index";

	}
}
