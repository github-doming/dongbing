package all.gen.sys_menu.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_menu.t.entity.SysMenuT;
import all.gen.sys_menu.t.service.SysMenuTService;
import all.gen.sys_menu.t.vo.SysMenuTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysMenuTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysMenuTService service = new SysMenuTService();
		SysMenuT entity = null;
		String id = request.getParameter("id");
		entity = (SysMenuT) RequestThreadLocal.doRequest2EntityByJson(SysMenuTVo.class, SysMenuT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
