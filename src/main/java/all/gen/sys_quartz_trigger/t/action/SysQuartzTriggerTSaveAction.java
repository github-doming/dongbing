package all.gen.sys_quartz_trigger.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.gen.sys_quartz_trigger.t.service.SysQuartzTriggerTService;
import all.gen.sys_quartz_trigger.t.vo.SysQuartzTriggerTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzTriggerTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzTriggerTService service = new SysQuartzTriggerTService();
		SysQuartzTriggerT entity = null;
		String id = request.getParameter("sys_quartz_trigger.sysQuartzTriggerId");
		entity = (SysQuartzTriggerT) RequestThreadLocal.doRequest2Entity(SysQuartzTriggerTVo.class,SysQuartzTriggerT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
