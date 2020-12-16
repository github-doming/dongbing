package all.workflow.actwf.actwf_form_def.t.action;

import all.workflow.actwf.actwf_form_def.t.entity.ActwfFormDefT;
import all.workflow.actwf.actwf_form_def.t.service.ActwfFormDefTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFormDefTdesignAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			ActwfFormDefTService service = new ActwfFormDefTService();
			ActwfFormDefT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
