package all.task.tms.tms_project.t.action;
import c.a.config.SysConfig;
import all.task.tms.tms_project.t.service.TmsProjectTService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsProjectTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String ids = request.getParameter("id");
		TmsProjectTService service = new TmsProjectTService();
		service.delAll(ids.split(","));
			// 跳转
		return CommViewEnum.Default.toString();
	}
}
