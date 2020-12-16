package c.x.platform.admin.feng.fas.cx.fas_business_info.action;
import c.x.platform.admin.feng.fas.cx.fas_business_info.entity.FasBusinessInfo;
import c.x.platform.admin.feng.fas.cx.fas_business_info.service.FasBusinessInfoService;
import c.x.platform.admin.feng.fas.cx.fas_business_info.vo.FasBusinessInfoVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
public class FasBusinessInfoSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("fas_business_info.id");

		FasBusinessInfo s = (FasBusinessInfo) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				FasBusinessInfoVo.class, FasBusinessInfo.class, request);
		FasBusinessInfoService service = new FasBusinessInfoService();
		if (StringUtil.isBlank(id)) {
			service.insert(s);
		} else {
			service.update(s);
		}
		return "index";
	}
}
