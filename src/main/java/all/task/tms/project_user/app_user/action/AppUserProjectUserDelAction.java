package all.task.tms.project_user.app_user.action;
import java.util.ArrayList;
import java.util.List;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class AppUserProjectUserDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		all.task.tms.project_user.app_user.service.AppUserProjectUserService service = new all.task.tms.project_user.app_user.service.AppUserProjectUserService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		//删除第3表
		// 第3表
		all.task.tms.project_user.tms_project_user.service.TmsProjectUserProjectUserService tTmsProjectUserProjectUserService = new all.task.tms.project_user.tms_project_user.service.TmsProjectUserProjectUserService();
		// 第3表删除
		tTmsProjectUserProjectUserService .del_by_userId(id);
			return CommViewEnum.Default.toString();
	}
}
