package c.x.platform.admin.gen.gen_double_simple.sys_user.action;

import java.util.ArrayList;
import java.util.List;

import c.a.util.core.enums.bean.CommViewEnum;
import  c.x.platform.root.common.action.BaseAction;

public class SysUserGenDoubleSimpleDelAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sysUserId");
		c.x.platform.admin.gen.gen_double_simple.sys_user.service.SysUserGenDoubleSimpleService service = new c.x.platform.admin.gen.gen_double_simple.sys_user.service.SysUserGenDoubleSimpleService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		
		//删除第3表
		// 第3表
		c.x.platform.admin.gen.gen_double_simple.sys_org_user.service.SysOrgUserGenDoubleSimpleService tSysOrgUserGenDoubleSimpleService = new c.x.platform.admin.gen.gen_double_simple.sys_org_user.service.SysOrgUserGenDoubleSimpleService();
		// 第3表删除
		tSysOrgUserGenDoubleSimpleService .del_by_userId(id);
		
		return CommViewEnum.Default.toString();
	}
}
