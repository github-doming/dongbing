package all.ui.cyui.layout;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class MainAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String type = this.findCurrentUserType();
		if ("sys".equals(type)) {
			return CommViewEnum.SYS.toString();
		}
		if ("app".equals(type)) {
			return CommViewEnum.APP.toString();
		}
		return null;
	}

}
