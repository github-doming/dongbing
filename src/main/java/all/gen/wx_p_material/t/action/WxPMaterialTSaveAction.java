package all.gen.wx_p_material.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_material.t.entity.WxPMaterialT;
import all.gen.wx_p_material.t.service.WxPMaterialTService;
import all.gen.wx_p_material.t.vo.WxPMaterialTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMaterialTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMaterialTService service = new WxPMaterialTService();
		WxPMaterialT entity = null;
		String id = request.getParameter("wx_p_material.wxPMaterialId");
		entity = (WxPMaterialT) RequestThreadLocal.doRequest2Entity(WxPMaterialTVo.class,WxPMaterialT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
