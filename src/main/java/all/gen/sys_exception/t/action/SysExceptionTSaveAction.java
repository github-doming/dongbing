package all.gen.sys_exception.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_exception.t.entity.SysExceptionT;
import all.gen.sys_exception.t.service.SysExceptionTService;
import all.gen.sys_exception.t.vo.SysExceptionTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysExceptionTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysExceptionTService service = new SysExceptionTService();
		SysExceptionT entity = null;
		String id = request.getParameter("sys_exception.sysExceptionId");
		entity = (SysExceptionT) RequestThreadLocal.doRequest2Entity(SysExceptionTVo.class,SysExceptionT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
