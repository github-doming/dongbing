package c.x.platform.sys.sys_group.cx_role.action;
import all.gen.sys_group.t.entity.SysGroupT;
import all.gen.sys_group.t.vo.SysGroupTVo;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import c.x.platform.sys.sys_group.cx_role.service.SysGroupCxRoleService;
public class SysGroupCxRoleSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_group.sysGroupId");
		SysGroupT entity = (SysGroupT ) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(SysGroupTVo.class,
						SysGroupT.class, request);
		SysGroupCxRoleService service = new SysGroupCxRoleService();
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return "index";
	}
}
