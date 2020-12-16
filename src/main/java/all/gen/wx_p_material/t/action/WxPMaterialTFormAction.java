package all.gen.wx_p_material.t.action;

import all.gen.wx_p_material.t.entity.WxPMaterialT;
import all.gen.wx_p_material.t.service.WxPMaterialTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMaterialTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPMaterialTService service = new WxPMaterialTService();
			WxPMaterialT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
