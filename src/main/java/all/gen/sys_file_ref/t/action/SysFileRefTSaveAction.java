package all.gen.sys_file_ref.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_file_ref.t.entity.SysFileRefT;
import all.gen.sys_file_ref.t.service.SysFileRefTService;
import all.gen.sys_file_ref.t.vo.SysFileRefTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysFileRefTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysFileRefTService service = new SysFileRefTService();
		SysFileRefT entity = null;
		String id = request.getParameter("sys_file_ref.sysFileRefId");
		entity = (SysFileRefT) RequestThreadLocal.doRequest2Entity(SysFileRefTVo.class,SysFileRefT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
