package all.gen.tms_task.t.action;
import c.a.config.SysConfig;
import all.gen.tms_task.t.service.TmsTaskTService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsTaskTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String ids = request.getParameter("id");
		TmsTaskTService service = new TmsTaskTService();
		service.delAll(ids.split(","));
			// 跳转
		return CommViewEnum.Default.toString();
	}
}
