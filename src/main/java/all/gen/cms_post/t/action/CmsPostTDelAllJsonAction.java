package all.gen.cms_post.t.action;
import all.gen.cms_post.t.service.CmsPostTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsPostTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsPostTService service = new CmsPostTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
