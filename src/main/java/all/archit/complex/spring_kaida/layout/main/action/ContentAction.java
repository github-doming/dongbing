package all.archit.complex.spring_kaida.layout.main.action;
import c.x.platform.root.common.action.BaseAction;
public class ContentAction extends BaseAction {
	public ContentAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		return "index";
	}
}
