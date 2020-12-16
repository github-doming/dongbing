package all.gen.sys_request_token.t.action;
import all.gen.sys_request_token.t.service.SysRequestTokenTService;
import c.a.config.SysConfig;
import c.x.platform.root.common.action.BaseAction;
public class SysRequestTokenTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysRequestTokenTService service = new SysRequestTokenTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
