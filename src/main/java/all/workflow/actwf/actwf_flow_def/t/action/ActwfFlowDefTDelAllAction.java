package all.workflow.actwf.actwf_flow_def.t.action;
import all.workflow.actwf.actwf_flow_def.t.service.ActwfFlowDefTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFlowDefTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		ActwfFlowDefTService service = new ActwfFlowDefTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
