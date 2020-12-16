package all.cms.msg.admin.sys_user.t.action;

import all.cms.msg.admin.sys_user.t.service.SysUserTService;
import all.gen.sys_user.t.entity.SysUserT;
import all.gen.sys_user.t.vo.SysUserTVo;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysUserTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysUserTService service = new SysUserTService();
		SysUserT entity = null;
		String id = request.getParameter("id");
		entity = (SysUserT) RequestThreadLocal.doRequest2EntityByJson(SysUserTVo.class, SysUserT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
