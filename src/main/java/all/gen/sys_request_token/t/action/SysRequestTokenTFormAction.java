package all.gen.sys_request_token.t.action;
import all.gen.sys_request_token.t.entity.SysRequestTokenT;
import all.gen.sys_request_token.t.service.SysRequestTokenTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysRequestTokenTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysRequestTokenTService service = new SysRequestTokenTService();
			SysRequestTokenT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
