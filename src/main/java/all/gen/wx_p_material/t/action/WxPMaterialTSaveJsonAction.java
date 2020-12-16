package all.gen.wx_p_material.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_material.t.entity.WxPMaterialT;
import all.gen.wx_p_material.t.service.WxPMaterialTService;
import all.gen.wx_p_material.t.vo.WxPMaterialTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxPMaterialTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMaterialTService service = new WxPMaterialTService();
		WxPMaterialT entity = null;
		String id = request.getParameter("id");
		entity = (WxPMaterialT) RequestThreadLocal.doRequest2EntityByJson(WxPMaterialTVo.class, WxPMaterialT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
