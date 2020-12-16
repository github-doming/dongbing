package all.gen.cms_topic.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_topic.t.entity.CmsTopicT;
import all.gen.cms_topic.t.service.CmsTopicTService;
import all.gen.cms_topic.t.vo.CmsTopicTVo;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsTopicTService service = new CmsTopicTService();
		CmsTopicT entity = null;
		String id = request.getParameter("id");
		entity = (CmsTopicT) RequestThreadLocal.doRequest2EntityByJson(CmsTopicTVo.class, CmsTopicT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
