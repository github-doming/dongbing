package c.x.platform.admin.gen.gen_double_simple.sys_user.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import  c.x.platform.root.common.action.BaseAction;

public class SysUserGenDoubleSimpleSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_user.id");
		

		c.x.platform.admin.gen.gen_double_simple.sys_user.entity.SysUserGenDoubleSimple entity = (c.x.platform.admin.gen.gen_double_simple.sys_user.entity.SysUserGenDoubleSimple) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				c.x.platform.admin.gen.gen_double_simple.sys_user.vo.SysUserGenDoubleSimpleVo.class, c.x.platform.admin.gen.gen_double_simple.sys_user.entity.SysUserGenDoubleSimple.class, request);

		c.x.platform.admin.gen.gen_double_simple.sys_user.service.SysUserGenDoubleSimpleService service = new c.x.platform.admin.gen.gen_double_simple.sys_user.service.SysUserGenDoubleSimpleService();
	
		// 第3表
		//{
		c.x.platform.admin.gen.gen_double_simple.sys_org_user.service.SysOrgUserGenDoubleSimpleService tSysOrgUserGenDoubleSimpleService = new c.x.platform.admin.gen.gen_double_simple.sys_org_user.service.SysOrgUserGenDoubleSimpleService();
		c.x.platform.admin.gen.gen_double_simple.sys_org_user.entity.SysOrgUserGenDoubleSimple tSysOrgUserGenDoubleSimple = new c.x.platform.admin.gen.gen_double_simple.sys_org_user.entity.SysOrgUserGenDoubleSimple();
		//树的老的ID
		String name_first$tree$id = request
				.getParameter("name_first$tree$id");
		//树的新的ID
		String sys_org$parent = request
				.getParameter("sys_org.parent");
		
		request.setAttribute("first$tree$id", sys_org$parent  );
		//}
		// 第3表
		
		if (StringUtil.isBlank(id)) {
			id = service.save(entity);

			// 第3表保存

			tSysOrgUserGenDoubleSimple.setSysOrgId(sys_org$parent);
			tSysOrgUserGenDoubleSimple.setSysUserId(id);

			tSysOrgUserGenDoubleSimpleService.save(tSysOrgUserGenDoubleSimple);

		} else {
			service.update(entity);

			// 第3表删除
			tSysOrgUserGenDoubleSimpleService.del(name_first$tree$id, id);

			// 第3表保存

			tSysOrgUserGenDoubleSimple.setSysOrgId( sys_org$parent);
			tSysOrgUserGenDoubleSimple.setSysUserId(id);

			tSysOrgUserGenDoubleSimpleService.save(tSysOrgUserGenDoubleSimple);

		}

		return CommViewEnum.Default.toString();
	}
}
