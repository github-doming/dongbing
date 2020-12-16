package all.job.action;
import all.job.service.SysQuartzTriggerService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzTriggerDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysQuartzTriggerService service = new SysQuartzTriggerService();
		//service.delAll(ids.split(","));
		service.delAllPhysical(ids);
		
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
