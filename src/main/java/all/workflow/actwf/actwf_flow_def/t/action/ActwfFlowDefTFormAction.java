package all.workflow.actwf.actwf_flow_def.t.action;

import all.workflow.actwf.actwf_flow_def.t.entity.ActwfFlowDefT;
import all.workflow.actwf.actwf_flow_def.t.service.ActwfFlowDefTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFlowDefTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			ActwfFlowDefTService service = new ActwfFlowDefTService();
			ActwfFlowDefT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
