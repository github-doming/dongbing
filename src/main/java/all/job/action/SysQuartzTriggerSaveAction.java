package all.job.action;
import all.gen.sys_quartz_job.t.entity.SysQuartzJobT;
import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.gen.sys_quartz_trigger.t.vo.SysQuartzTriggerTVo;
import all.job.service.SysQuartzJobService;
import all.job.service.SysQuartzTriggerService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzTriggerSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzTriggerService service = new SysQuartzTriggerService();
		SysQuartzTriggerT entity = null;
		String id = request.getParameter("sys_quartz_trigger.sysQuartzTriggerId");
		entity = (SysQuartzTriggerT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(SysQuartzTriggerTVo.class, SysQuartzTriggerT.class,
				request);
		// 保存计划
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		// job
		SysQuartzJobService sysQuartzJobService = new SysQuartzJobService();
		SysQuartzJobT sysQuartzJobT = new SysQuartzJobT();
		sysQuartzJobT.setJobClassName(entity.getJobClassName());
		sysQuartzJobT.setJobName(entity.getJobName());
		sysQuartzJobT.setSysJobName(entity.getJobName());
		sysQuartzJobService.saveOrUpdate(sysQuartzJobT);

		return CommViewEnum.Default.toString();

	}
}
