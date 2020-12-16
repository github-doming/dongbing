package all.job.action;
import all.gen.sys_quartz_config.t.entity.SysQuartzConfigT;
import all.job.service.SysQuartzConfigService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzConfigStartAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysQuartzConfigService service = new SysQuartzConfigService();
			SysQuartzConfigT entity = service.find(id);

			service.start(entity);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
