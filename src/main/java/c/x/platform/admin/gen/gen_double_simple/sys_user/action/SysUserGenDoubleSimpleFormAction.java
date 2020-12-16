package  c.x.platform.admin.gen.gen_double_simple.sys_user.action;

import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.string.StringUtil;

public class SysUserGenDoubleSimpleFormAction  extends BaseAction {

	@Override
	public String execute() throws Exception {
	
		c.x.platform.admin.gen.gen_double_simple.sys_user.service.SysUserGenDoubleSimpleService sSysUserGenDoubleSimpleService = new c.x.platform.admin.gen.gen_double_simple.sys_user.service.SysUserGenDoubleSimpleService();
		c.x.platform.admin.gen.gen_double_simple.sys_org.service.SysOrgGenDoubleSimpleService fSysOrgGenDoubleSimpleService = new c.x.platform.admin.gen.gen_double_simple.sys_org.service.SysOrgGenDoubleSimpleService();

		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		// log.trace("id=" + id);

		if (id == null) {

		}

		if (id != null) {
			// 本身
			c.x.platform.admin.gen.gen_double_simple.sys_user.entity.SysUserGenDoubleSimple s = sSysUserGenDoubleSimpleService.find(id);

			request.setAttribute("s", s);

		}

		// 树节点id
		// {
		// first$tree$id
		String first$tree$id = (String) request.getParameter("first$tree$id");

		request.setAttribute("value_first$tree$id", first$tree$id);

		// 选择上一级菜单
		if (StringUtil.isBlank(first$tree$id)) {

		} else {

			first$tree$id = first$tree$id.trim();

			c.x.platform.admin.gen.gen_double_simple.sys_org.entity.SysOrgGenDoubleSimple p = fSysOrgGenDoubleSimpleService.find(first$tree$id);

			request.setAttribute("p", p);

			// 树名称

			request.setAttribute("value_first$tree$name", p.getName());

		}

		// }
		// 树节点id

		return CommViewEnum.Default.toString();
	}
}
