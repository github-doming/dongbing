package all.gen.wx_p_mt_material_temp.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_mt_material_temp.t.entity.WxPMtMaterialTempT;
import all.gen.wx_p_mt_material_temp.t.service.WxPMtMaterialTempTService;
import all.gen.wx_p_mt_material_temp.t.vo.WxPMtMaterialTempTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtMaterialTempTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMtMaterialTempTService service = new WxPMtMaterialTempTService();
		WxPMtMaterialTempT entity = null;
		String id = request.getParameter("id");
		entity = (WxPMtMaterialTempT) RequestThreadLocal.doRequest2EntityByJson(WxPMtMaterialTempTVo.class, WxPMtMaterialTempT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
