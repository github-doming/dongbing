package all.task.tms.tms_function.t.action;
import c.a.config.SysConfig;
import all.task.tms.tms_function.t.service.TmsFunctionTService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class TmsFunctionTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String ids = request.getParameter("id");
		TmsFunctionTService service = new TmsFunctionTService();
		service.delAll(ids.split(","));
			// 跳转
		return CommViewEnum.Default.toString();
	}
}
