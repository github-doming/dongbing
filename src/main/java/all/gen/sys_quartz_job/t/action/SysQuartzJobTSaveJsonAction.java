package all.gen.sys_quartz_job.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_quartz_job.t.entity.SysQuartzJobT;
import all.gen.sys_quartz_job.t.service.SysQuartzJobTService;
import all.gen.sys_quartz_job.t.vo.SysQuartzJobTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzJobTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysQuartzJobTService service = new SysQuartzJobTService();
		SysQuartzJobT entity = null;
		String id = request.getParameter("id");
		entity = (SysQuartzJobT) RequestThreadLocal.doRequest2EntityByJson(SysQuartzJobTVo.class, SysQuartzJobT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
