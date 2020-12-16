package all.job.action;
import all.gen.sys_quartz_config.t.entity.SysQuartzConfigT;
import all.gen.sys_quartz_config.t.service.SysQuartzConfigTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.job.QuartzUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzConfigFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysQuartzConfigTService service = new SysQuartzConfigTService();
			SysQuartzConfigT entity = service.find(id);

			entity.setInstandbyMode(String.valueOf(
					QuartzUtil.findInstance().doSchedulerIsInStandbyMode()));
			entity.setStarted(String
					.valueOf(QuartzUtil.findInstance().doSchedulerIsStart()));

			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
