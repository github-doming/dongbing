package all.gen.sys_quartz_trigger.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_quartz_trigger.t.entity.SysQuartzTriggerT;
import all.gen.sys_quartz_trigger.t.service.SysQuartzTriggerTService;
import all.gen.sys_quartz_trigger.t.vo.SysQuartzTriggerTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzTriggerTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzTriggerTService service = new SysQuartzTriggerTService();
		SysQuartzTriggerT entity = null;
		String id = request.getParameter("id");
		entity = (SysQuartzTriggerT) RequestThreadLocal.doRequest2EntityByJson(SysQuartzTriggerTVo.class, SysQuartzTriggerT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
