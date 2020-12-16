package all.gen.cms_topic.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_topic.t.entity.CmsTopicT;
import all.gen.cms_topic.t.service.CmsTopicTService;
import all.gen.cms_topic.t.vo.CmsTopicTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsTopicTService service = new CmsTopicTService();
		CmsTopicT entity = null;
		String id = request.getParameter("cms_topic.cmsTopicId");
		entity = (CmsTopicT) RequestThreadLocal.doRequest2Entity(CmsTopicTVo.class,CmsTopicT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
