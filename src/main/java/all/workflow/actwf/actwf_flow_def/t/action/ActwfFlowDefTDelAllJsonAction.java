package all.workflow.actwf.actwf_flow_def.t.action;
import all.workflow.actwf.actwf_flow_def.t.service.ActwfFlowDefTService;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFlowDefTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		ActwfFlowDefTService service = new ActwfFlowDefTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
