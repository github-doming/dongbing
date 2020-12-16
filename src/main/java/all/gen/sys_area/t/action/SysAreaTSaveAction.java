package all.gen.sys_area.t.action;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.gen.sys_area.t.entity.SysAreaT;
import all.gen.sys_area.t.service.SysAreaTService;
import all.gen.sys_area.t.vo.SysAreaTVo;
public class SysAreaTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysAreaTService service = new SysAreaTService();
		String parent_id = request.getParameter("sys_area.parent");
		String id = request.getParameter("sys_area.sysAreaId");
		SysAreaT entity = (SysAreaT) RequestThreadLocal.doRequest2Entity(
		SysAreaTVo.class, SysAreaT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
