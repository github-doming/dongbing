package all.workflow.actwf.actwf_form_def.t.action;
import all.workflow.actwf.actwf_form_def.t.service.ActwfFormDefTService;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFormDefTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		ActwfFormDefTService service = new ActwfFormDefTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
