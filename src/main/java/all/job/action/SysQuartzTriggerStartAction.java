package all.job.action;
import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.job.service.SysQuartzTriggerService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzTriggerStartAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzTriggerService service = new SysQuartzTriggerService	();
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysQuartzTriggerT entity = service.find(id);
			service.start(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
