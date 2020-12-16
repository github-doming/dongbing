package all.sys_admin.sys.sys_dict_tree.t.action;
import java.util.ArrayList;
import java.util.List;
import all.sys_admin.sys.sys_dict_tree.t.service.SysDictTreeTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysDictTreeTDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysDictTreeTService service = new SysDictTreeTService();
		service.del(id);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
