package all.cms.msg.admin.cms_box_msg.t.action;
import all.cms.msg.admin.cms_box_msg.t.service.CmsBoxMsgTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxMsgTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsBoxMsgTService service = new CmsBoxMsgTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
