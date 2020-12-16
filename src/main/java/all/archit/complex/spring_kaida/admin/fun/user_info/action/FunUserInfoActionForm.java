package all.archit.complex.spring_kaida.admin.fun.user_info.action;
import all.archit.complex.spring_kaida.admin.fun.user_info.entity.SpringKaidaFunUserInfo;
import all.archit.complex.spring_kaida.admin.fun.user_info.service.FunUserInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FunUserInfoActionForm extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			FunUserInfoService service = new FunUserInfoService();
			SpringKaidaFunUserInfo s = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", s);
		}
		return "index";
	}
}
