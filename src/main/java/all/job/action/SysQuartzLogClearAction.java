package all.job.action;
import all.gen.sys_quartz_log.t.service.SysQuartzLogTService;
import all.job.service.SysQuartzLogService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzLogClearAction extends BaseAction {
	@Override
	public String execute() throws Exception {

		SysQuartzLogService service = new SysQuartzLogService();

		service.clearPhysical();

		// 跳转
		return CommViewEnum.Default.toString();
	}
}
