package all.sys_admin.sys.sys_dict_tree.t.action;
import c.a.config.SysConfig;
import all.sys_admin.sys.sys_dict_tree.t.service.SysDictTreeTService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class SysDictTreeTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String ids = request.getParameter("id");
		SysDictTreeTService service = new SysDictTreeTService();
		service.delAll(ids.split(","));
			// 跳转
		return CommViewEnum.Default.toString();
	}
}
