package all.workflow.actwf.actwf_business_def.t.action;

import all.workflow.actwf.actwf_business_def.t.entity.ActwfBusinessDefT;
import all.workflow.actwf.actwf_business_def.t.service.ActwfBusinessDefTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActwfBusinessDefTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			ActwfBusinessDefTService service = new ActwfBusinessDefTService();
			ActwfBusinessDefT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
