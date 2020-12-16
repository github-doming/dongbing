package all.gen.sys_quartz_config.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_quartz_config.t.entity.SysQuartzConfigT;
import all.gen.sys_quartz_config.t.service.SysQuartzConfigTService;
import all.gen.sys_quartz_config.t.vo.SysQuartzConfigTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzConfigTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzConfigTService service = new SysQuartzConfigTService();
		SysQuartzConfigT entity = null;
		String id = request.getParameter("id");
		entity = (SysQuartzConfigT) RequestThreadLocal.doRequest2EntityByJson(SysQuartzConfigTVo.class, SysQuartzConfigT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
