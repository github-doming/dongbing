package all.gen.sys_quartz_log.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_quartz_log.t.entity.SysQuartzLogT;
import all.gen.sys_quartz_log.t.service.SysQuartzLogTService;
import all.gen.sys_quartz_log.t.vo.SysQuartzLogTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzLogTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzLogTService service = new SysQuartzLogTService();
		SysQuartzLogT entity = null;
		String id = request.getParameter("sys_quartz_log.sysQuartzLogId");
		entity = (SysQuartzLogT) RequestThreadLocal.doRequest2Entity(SysQuartzLogTVo.class,SysQuartzLogT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
