package all.pc.api.action;
import all.app.common.action.AppAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class PcApiAction extends AppAction {
	@Override
	public String run() throws Exception {
		return CommViewEnum.Default.toString();
	}
}
