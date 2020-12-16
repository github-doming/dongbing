package all.gen.sys_group.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_group.t.entity.SysGroupT;
import all.gen.sys_group.t.service.SysGroupTService;
import all.gen.sys_group.t.vo.SysGroupTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysGroupTService service = new SysGroupTService();
		SysGroupT entity = null;
		String id = request.getParameter("sys_group.sysGroupId");
		entity = (SysGroupT) RequestThreadLocal.doRequest2Entity(SysGroupTVo.class,SysGroupT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
