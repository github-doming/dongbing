package all.archit.complex.gy_kaida.layout.main.action;
import c.x.platform.root.common.action.BaseAction;
public class MainAction extends BaseAction {
	public MainAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		System.out.println("main");
		// 当前用户名称
		String current_user_name = this.findCurrentSysUser().getSysUserName();
		request.setAttribute("current_user_name", current_user_name);
		return "index";
	}
}
