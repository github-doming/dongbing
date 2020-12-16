package all.cms.msg.admin.cms_box_topic.t.action;
import all.cms.msg.admin.cms_box_topic.t.service.CmsBoxTopicTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxTopicTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsBoxTopicTService service = new CmsBoxTopicTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
