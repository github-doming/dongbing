package all.gen.sys_group_menu.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_group_menu.t.entity.SysGroupMenuT;
import all.gen.sys_group_menu.t.service.SysGroupMenuTService;
import all.gen.sys_group_menu.t.vo.SysGroupMenuTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupMenuTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysGroupMenuTService service = new SysGroupMenuTService();
		SysGroupMenuT entity = null;
		String id = request.getParameter("sys_group_menu.sysGroupMenuId");
		entity = (SysGroupMenuT) RequestThreadLocal.doRequest2Entity(SysGroupMenuTVo.class,SysGroupMenuT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
