package all.gen.sys_group.t.action;
import all.gen.sys_group.t.service.SysGroupTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysGroupTService service = new SysGroupTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
