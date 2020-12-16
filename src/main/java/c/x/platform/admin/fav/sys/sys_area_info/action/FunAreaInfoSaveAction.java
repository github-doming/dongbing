package c.x.platform.admin.fav.sys.sys_area_info.action;

import c.x.platform.root.common.action.BaseAction;
import c.x.platform.admin.fav.sys.sys_area_info.entity.FunAreaInfo;
import c.x.platform.admin.fav.sys.sys_area_info.service.FunAreaInfoService;
import c.x.platform.admin.fav.sys.sys_area_info.vo.FunAreaInfoVo;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class FunAreaInfoSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_area_info.id");

		FunAreaInfo s = (FunAreaInfo) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				FunAreaInfoVo.class, FunAreaInfo.class, request);
		FunAreaInfoService service = new FunAreaInfoService();
		if (StringUtil.isBlank(id)) {
			service.insert(s);
		} else {
			service.update(s);
		}
		return "index";
	}
}
