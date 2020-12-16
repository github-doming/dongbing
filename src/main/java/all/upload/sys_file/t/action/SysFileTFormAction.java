package all.upload.sys_file.t.action;
import all.gen.sys_file.t.entity.SysFileT;
import all.upload.sys_file.t.service.SysFileTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysFileTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysFileTService service = new SysFileTService();
			SysFileT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
