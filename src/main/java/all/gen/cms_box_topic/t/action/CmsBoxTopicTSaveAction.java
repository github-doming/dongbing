package all.gen.cms_box_topic.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_box_topic.t.entity.CmsBoxTopicT;
import all.gen.cms_box_topic.t.service.CmsBoxTopicTService;
import all.gen.cms_box_topic.t.vo.CmsBoxTopicTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxTopicTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsBoxTopicTService service = new CmsBoxTopicTService();
		CmsBoxTopicT entity = null;
		String id = request.getParameter("cms_box_topic.cmsBoxTopicId");
		entity = (CmsBoxTopicT) RequestThreadLocal.doRequest2Entity(CmsBoxTopicTVo.class,CmsBoxTopicT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
