package all.gen.fun_type_str_queue.t.action;
import all.gen.fun_type_str_queue.t.service.FunTypeStrQueueTService;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeStrQueueTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		FunTypeStrQueueTService service = new FunTypeStrQueueTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
