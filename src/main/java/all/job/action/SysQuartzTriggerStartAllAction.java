package all.job.action;
import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.job.service.SysQuartzTriggerService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzTriggerStartAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		SysQuartzTriggerService service = new SysQuartzTriggerService();
        for (String id : ids) {
            SysQuartzTriggerT entity = service.find(id);
            service.start(entity);
        }
		return CommViewEnum.Default.toString();
	}
}
