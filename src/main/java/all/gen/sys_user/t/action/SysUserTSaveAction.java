package all.gen.sys_user.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_user.t.entity.SysUserT;
import all.gen.sys_user.t.service.SysUserTService;
import all.gen.sys_user.t.vo.SysUserTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysUserTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysUserTService service = new SysUserTService();
		SysUserT entity = null;
		String id = request.getParameter("sys_user.sysUserId");
		entity = (SysUserT) RequestThreadLocal.doRequest2Entity(SysUserTVo.class,SysUserT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
