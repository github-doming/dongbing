package all.gen.sys_quartz_config.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_quartz_config.t.entity.SysQuartzConfigT;
import all.gen.sys_quartz_config.t.service.SysQuartzConfigTService;
import all.gen.sys_quartz_config.t.vo.SysQuartzConfigTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzConfigTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzConfigTService service = new SysQuartzConfigTService();
		SysQuartzConfigT entity = null;
		String id = request.getParameter("sys_quartz_config.sysQuartzConfigId");
		entity = (SysQuartzConfigT) RequestThreadLocal.doRequest2Entity(SysQuartzConfigTVo.class,SysQuartzConfigT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
