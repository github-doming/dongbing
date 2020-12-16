package all.gen.sys_bytes.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_bytes.t.entity.SysBytesT;
import all.gen.sys_bytes.t.service.SysBytesTService;
import all.gen.sys_bytes.t.vo.SysBytesTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysBytesTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysBytesTService service = new SysBytesTService();
		SysBytesT entity = null;
		String id = request.getParameter("sys_bytes.sysBytesId");
		entity = (SysBytesT) RequestThreadLocal.doRequest2Entity(SysBytesTVo.class,SysBytesT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
