package all.gen.sys_file.t.action;
import all.gen.sys_file.t.service.SysFileTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysFileTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysFileTService service = new SysFileTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
