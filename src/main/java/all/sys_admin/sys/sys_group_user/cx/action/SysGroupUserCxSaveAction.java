package all.sys_admin.sys.sys_group_user.cx.action;

import all.gen.sys_group_user.t.entity.SysGroupUserT;
import all.gen.sys_group_user.t.vo.SysGroupUserTVo;
import all.sys_admin.sys.sys_group_user.cx.service.SysGroupUserService;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;

public class SysGroupUserCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_group_user.ID_");

		SysGroupUserT entity = (SysGroupUserT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysGroupUserTVo.class, SysGroupUserT.class, request);

		SysGroupUserService service = new SysGroupUserService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
