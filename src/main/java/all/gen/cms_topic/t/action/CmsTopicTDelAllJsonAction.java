package all.gen.cms_topic.t.action;
import all.gen.cms_topic.t.service.CmsTopicTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsTopicTService service = new CmsTopicTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
