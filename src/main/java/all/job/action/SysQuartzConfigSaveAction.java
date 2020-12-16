package all.job.action;
import all.gen.sys_quartz_config.t.entity.SysQuartzConfigT;
import all.gen.sys_quartz_config.t.vo.SysQuartzConfigTVo;
import all.job.service.SysQuartzConfigService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzConfigSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzConfigService service = new SysQuartzConfigService();
		SysQuartzConfigT entity = null;

		String id = request.getParameter("sys_quartz_config.sysQuartzConfigId");
		entity = (SysQuartzConfigT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysQuartzConfigTVo.class, SysQuartzConfigT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();

	}
}
