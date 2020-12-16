package all.task.tms.tms_project.t.action;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsProjectTIndexAction extends BaseAction {
	@Override
	public String execute() throws Exception {
			return CommViewEnum.Default.toString();
	}
}
