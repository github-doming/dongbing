package all.gen.sys_group_user.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_group_user.t.entity.SysGroupUserT;
import all.gen.sys_group_user.t.service.SysGroupUserTService;
import all.gen.sys_group_user.t.vo.SysGroupUserTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupUserTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysGroupUserTService service = new SysGroupUserTService();
		SysGroupUserT entity = null;
		String id = request.getParameter("id");
		entity = (SysGroupUserT) RequestThreadLocal.doRequest2EntityByJson(SysGroupUserTVo.class, SysGroupUserT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
