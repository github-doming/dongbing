package all.archit.complex.ey_kaida.layout.main.action;
import c.x.platform.root.common.action.BaseAction;
public class LeftAction extends BaseAction {
	public LeftAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		return "index";
	}
}
