package all.gen.sys_file_ref.t.action;

import all.gen.sys_file_ref.t.entity.SysFileRefT;
import all.gen.sys_file_ref.t.service.SysFileRefTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysFileRefTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysFileRefTService service = new SysFileRefTService();
			SysFileRefT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
