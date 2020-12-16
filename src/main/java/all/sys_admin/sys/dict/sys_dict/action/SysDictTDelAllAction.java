package all.sys_admin.sys.dict.sys_dict.action;

import all.sys_admin.sys.dict.sys_dict.service.SysDictTService;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class SysDictTDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		SysDictTService service = new SysDictTService();
		service.delAll(ids);
			return CommViewEnum.Default.toString();
	}
}
