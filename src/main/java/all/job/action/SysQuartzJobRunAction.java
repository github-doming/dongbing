package all.job.action;
import all.gen.sys_quartz_job.t.entity.SysQuartzJobT;
import all.job.service.SysQuartzJobService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzJobRunAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		
		SysQuartzJobService service = new SysQuartzJobService();
		
		String id = (String) request.getParameter("id");
		if (id != null) {
			
			SysQuartzJobT entity = service.find(id);
			service.run(entity);
			
			
		}
		return CommViewEnum.Default.toString();
	}
}
