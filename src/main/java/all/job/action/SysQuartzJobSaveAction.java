package all.job.action;
import all.gen.sys_quartz_job.t.entity.SysQuartzJobT;
import all.gen.sys_quartz_job.t.vo.SysQuartzJobTVo;
import all.job.service.SysQuartzJobService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzJobSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzJobService sysQuartzJobService = new SysQuartzJobService();
		SysQuartzJobT sysQuartzJobT = null;

		//String id = request.getParameter("sys_quartz_job.sysQuartzJobId");
		sysQuartzJobT = (SysQuartzJobT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(SysQuartzJobTVo.class, SysQuartzJobT.class, request);
		// if (StringUtil.isBlank(id)) {
		// service.save(entity);
		// } else {
		// service.update(entity);
		// }
		sysQuartzJobService.saveOrUpdate(sysQuartzJobT);
		return CommViewEnum.Default.toString();

	}
}
