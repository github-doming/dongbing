package all.gen.sys_request_token.t.action;
import all.gen.sys_request_token.t.service.SysRequestTokenTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysRequestTokenTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysRequestTokenTService service = new SysRequestTokenTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
