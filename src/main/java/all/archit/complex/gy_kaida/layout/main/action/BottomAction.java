package all.archit.complex.gy_kaida.layout.main.action;
import c.x.platform.root.common.action.BaseAction;
public class BottomAction extends BaseAction {
	public BottomAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		return "index";
	}
}
