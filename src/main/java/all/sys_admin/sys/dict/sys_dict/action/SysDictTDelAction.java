package all.sys_admin.sys.dict.sys_dict.action;
import java.util.ArrayList;
import java.util.List;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class SysDictTDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		all.sys_admin.sys.dict.sys_dict.service.SysDictTService service = new all.sys_admin.sys.dict.sys_dict.service.SysDictTService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		//删除第3表
		// 第3表
		all.sys_admin.sys.dict.sys_dict_ref.service.SysDictRefTService tSysDictRefTService = new all.sys_admin.sys.dict.sys_dict_ref.service.SysDictRefTService();
		// 第3表删除
		tSysDictRefTService.del_by_sysDictId(id);
			return CommViewEnum.Default.toString();
	}
}
