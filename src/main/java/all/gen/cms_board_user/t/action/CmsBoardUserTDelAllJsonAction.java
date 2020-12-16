package all.gen.cms_board_user.t.action;
import all.gen.cms_board_user.t.service.CmsBoardUserTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoardUserTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsBoardUserTService service = new CmsBoardUserTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
