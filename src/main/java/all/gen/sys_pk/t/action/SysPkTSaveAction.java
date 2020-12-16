package all.gen.sys_pk.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_pk.t.entity.SysPkT;
import all.gen.sys_pk.t.service.SysPkTService;
import all.gen.sys_pk.t.vo.SysPkTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysPkTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysPkTService service = new SysPkTService();
		SysPkT entity = null;
		String id = request.getParameter("sys_pk.sysPkId");
		entity = (SysPkT) RequestThreadLocal.doRequest2Entity(SysPkTVo.class,SysPkT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
