package all.gen.wx_p_material.t.action;
import all.gen.wx_p_material.t.service.WxPMaterialTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMaterialTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPMaterialTService service = new WxPMaterialTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
