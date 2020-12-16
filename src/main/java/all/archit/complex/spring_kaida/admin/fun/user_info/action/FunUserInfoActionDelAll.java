package all.archit.complex.spring_kaida.admin.fun.user_info.action;
import all.archit.complex.spring_kaida.admin.fun.user_info.service.FunUserInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FunUserInfoActionDelAll extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		FunUserInfoService service = new FunUserInfoService();
		service.delAll(ids);
		return "index";
	}
}
