package c.x.platform.sys.sys_group_user.cx.action;

import all.gen.sys_group_user.t.entity.SysGroupUserT;
import all.gen.sys_group_user.t.vo.SysGroupUserTVo;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import c.x.platform.sys.sys_group_user.cx.service.SysGroupUserCxService;

public class SysGroupUserCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_group_user.ID_");

		SysGroupUserT entity = (SysGroupUserT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysGroupUserTVo.class, SysGroupUserT.class, request);

		SysGroupUserCxService service = new SysGroupUserCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
