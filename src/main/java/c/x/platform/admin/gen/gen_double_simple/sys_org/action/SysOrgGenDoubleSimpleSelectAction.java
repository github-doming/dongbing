package c.x.platform.admin.gen.gen_double_simple.sys_org.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.admin.gen.gen_double_simple.sys_org.entity.SysOrgGenDoubleSimple;
import c.x.platform.admin.gen.gen_double_simple.sys_org.service.SysOrgGenDoubleSimpleService;
import  c.x.platform.root.common.action.BaseAction;

public class SysOrgGenDoubleSimpleSelectAction  extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysOrgGenDoubleSimpleService service = new SysOrgGenDoubleSimpleService();
		String parent_id = request.getParameter("parent_id");
		SysOrgGenDoubleSimple s = service.find(parent_id);
		request.setAttribute("s", s);
		return CommViewEnum.Default.toString();
	}
}
