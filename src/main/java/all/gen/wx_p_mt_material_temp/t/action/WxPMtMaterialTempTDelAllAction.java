package all.gen.wx_p_mt_material_temp.t.action;
import all.gen.wx_p_mt_material_temp.t.service.WxPMtMaterialTempTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtMaterialTempTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPMtMaterialTempTService service = new WxPMtMaterialTempTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
