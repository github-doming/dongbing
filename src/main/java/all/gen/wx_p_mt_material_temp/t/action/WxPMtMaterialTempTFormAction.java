package all.gen.wx_p_mt_material_temp.t.action;

import all.gen.wx_p_mt_material_temp.t.entity.WxPMtMaterialTempT;
import all.gen.wx_p_mt_material_temp.t.service.WxPMtMaterialTempTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtMaterialTempTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPMtMaterialTempTService service = new WxPMtMaterialTempTService();
			WxPMtMaterialTempT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
