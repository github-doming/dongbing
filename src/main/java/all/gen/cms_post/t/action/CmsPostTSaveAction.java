package all.gen.cms_post.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_post.t.entity.CmsPostT;
import all.gen.cms_post.t.service.CmsPostTService;
import all.gen.cms_post.t.vo.CmsPostTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsPostTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsPostTService service = new CmsPostTService();
		CmsPostT entity = null;
		String id = request.getParameter("cms_post.cmsPostId");
		entity = (CmsPostT) RequestThreadLocal.doRequest2Entity(CmsPostTVo.class,CmsPostT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
