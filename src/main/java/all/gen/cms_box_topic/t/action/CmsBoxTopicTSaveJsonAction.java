package all.gen.cms_box_topic.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_box_topic.t.entity.CmsBoxTopicT;
import all.gen.cms_box_topic.t.service.CmsBoxTopicTService;
import all.gen.cms_box_topic.t.vo.CmsBoxTopicTVo;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxTopicTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsBoxTopicTService service = new CmsBoxTopicTService();
		CmsBoxTopicT entity = null;
		String id = request.getParameter("id");
		entity = (CmsBoxTopicT) RequestThreadLocal.doRequest2EntityByJson(CmsBoxTopicTVo.class, CmsBoxTopicT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
