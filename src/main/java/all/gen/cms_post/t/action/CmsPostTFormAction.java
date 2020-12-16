package all.gen.cms_post.t.action;

import all.gen.cms_post.t.entity.CmsPostT;
import all.gen.cms_post.t.service.CmsPostTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsPostTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsPostTService service = new CmsPostTService();
			CmsPostT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
