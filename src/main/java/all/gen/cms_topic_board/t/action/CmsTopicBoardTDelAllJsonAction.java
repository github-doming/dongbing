package all.gen.cms_topic_board.t.action;
import all.gen.cms_topic_board.t.service.CmsTopicBoardTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicBoardTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsTopicBoardTService service = new CmsTopicBoardTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
