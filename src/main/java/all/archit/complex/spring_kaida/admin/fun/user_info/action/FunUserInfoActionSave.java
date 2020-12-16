package all.archit.complex.spring_kaida.admin.fun.user_info.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.archit.complex.spring_kaida.admin.fun.user_info.entity.SpringKaidaFunUserInfo;
import all.archit.complex.spring_kaida.admin.fun.user_info.service.FunUserInfoService;
import all.archit.complex.spring_kaida.admin.fun.user_info.vo.FunUserInfoVo;
public class FunUserInfoActionSave extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_user_info.id");

		SpringKaidaFunUserInfo s = (SpringKaidaFunUserInfo) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(FunUserInfoVo.class,
						SpringKaidaFunUserInfo.class, request);
		FunUserInfoService service = new FunUserInfoService();
		if (StringUtil.isBlank(id)) {
			service.insert(s);
		} else {
			service.update(s);
		}
		return "index";
	}
}
