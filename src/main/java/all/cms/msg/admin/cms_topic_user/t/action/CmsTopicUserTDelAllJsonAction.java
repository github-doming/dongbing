package all.cms.msg.admin.cms_topic_user.t.action;
import all.cms.msg.admin.cms_topic_user.t.service.CmsTopicUserTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicUserTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsTopicUserTService service = new CmsTopicUserTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
