package all.gen.tms_project.t.action;
import all.gen.tms_project.t.service.TmsProjectTService;
import c.x.platform.root.common.action.BaseAction;
public class TmsProjectTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		TmsProjectTService service = new TmsProjectTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
