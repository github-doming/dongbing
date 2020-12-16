package c.x.platform.admin.gen.gen_double_simple.sys_user.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.admin.gen.gen_double_simple.sys_user.service.SysUserGenDoubleSimpleService;
import  c.x.platform.root.common.action.BaseAction;

public class SysUserGenDoubleSimpleDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		SysUserGenDoubleSimpleService service = new SysUserGenDoubleSimpleService();
		service.delAll(ids);
		return CommViewEnum.Default.toString();
	}
}
