package all.workflow.actwf.actwf_business_def.t.action;
import all.workflow.actwf.actwf_business_def.t.service.ActwfBusinessDefTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActwfBusinessDefTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		ActwfBusinessDefTService service = new ActwfBusinessDefTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
