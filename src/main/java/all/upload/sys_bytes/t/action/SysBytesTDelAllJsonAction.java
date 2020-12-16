package all.upload.sys_bytes.t.action;
import all.upload.sys_bytes.t.service.SysBytesTService;
import c.a.config.SysConfig;
import c.x.platform.root.common.action.BaseAction;
public class SysBytesTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysBytesTService service = new SysBytesTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
