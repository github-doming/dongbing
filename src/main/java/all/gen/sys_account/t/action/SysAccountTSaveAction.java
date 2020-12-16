package all.gen.sys_account.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_account.t.entity.SysAccountT;
import all.gen.sys_account.t.service.SysAccountTService;
import all.gen.sys_account.t.vo.SysAccountTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysAccountTService service = new SysAccountTService();
		SysAccountT entity = null;
		String id = request.getParameter("sys_account.sysAccountId");
		entity = (SysAccountT) RequestThreadLocal.doRequest2Entity(SysAccountTVo.class,SysAccountT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
