package all.gen.sys_quartz_log.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_quartz_log.t.entity.SysQuartzLogT;
import all.gen.sys_quartz_log.t.service.SysQuartzLogTService;
import all.gen.sys_quartz_log.t.vo.SysQuartzLogTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzLogTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzLogTService service = new SysQuartzLogTService();
		SysQuartzLogT entity = null;
		String id = request.getParameter("id");
		entity = (SysQuartzLogT) RequestThreadLocal.doRequest2EntityByJson(SysQuartzLogTVo.class, SysQuartzLogT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
