package all.gen.sys_group_menu.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_group_menu.t.entity.SysGroupMenuT;
import all.gen.sys_group_menu.t.service.SysGroupMenuTService;
import all.gen.sys_group_menu.t.vo.SysGroupMenuTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupMenuTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysGroupMenuTService service = new SysGroupMenuTService();
		SysGroupMenuT entity = null;
		String id = request.getParameter("id");
		entity = (SysGroupMenuT) RequestThreadLocal.doRequest2EntityByJson(SysGroupMenuTVo.class, SysGroupMenuT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
