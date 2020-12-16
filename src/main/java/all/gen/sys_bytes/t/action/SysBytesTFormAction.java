package all.gen.sys_bytes.t.action;

import all.gen.sys_bytes.t.entity.SysBytesT;
import all.gen.sys_bytes.t.service.SysBytesTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysBytesTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysBytesTService service = new SysBytesTService();
			SysBytesT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
