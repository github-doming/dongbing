package all.gen.sys_group_user.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_group_user.t.entity.SysGroupUserT;
import all.gen.sys_group_user.t.service.SysGroupUserTService;
import all.gen.sys_group_user.t.vo.SysGroupUserTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupUserTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysGroupUserTService service = new SysGroupUserTService();
		SysGroupUserT entity = null;
		String id = request.getParameter("sys_group_user.sysGroupUserId");
		entity = (SysGroupUserT) RequestThreadLocal.doRequest2Entity(SysGroupUserTVo.class,SysGroupUserT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
