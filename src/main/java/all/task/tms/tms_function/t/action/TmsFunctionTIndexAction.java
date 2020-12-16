package all.task.tms.tms_function.t.action;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsFunctionTIndexAction extends BaseAction {
	@Override
	public String execute() throws Exception {
			return CommViewEnum.Default.toString();
	}
}
