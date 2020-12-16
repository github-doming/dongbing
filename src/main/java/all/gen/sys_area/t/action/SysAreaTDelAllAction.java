package all.gen.sys_area.t.action;
import c.a.config.SysConfig;
import all.gen.sys_area.t.service.SysAreaTService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class SysAreaTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String ids = request.getParameter("id");
		SysAreaTService service = new SysAreaTService();
		service.delAll(ids.split(","));
			// 跳转
		return CommViewEnum.Default.toString();
	}
}
