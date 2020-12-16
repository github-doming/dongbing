package all.cms.msg.admin.cms_topic_user.t.action;

import all.cms.msg.admin.cms_topic_user.t.service.CmsTopicUserTService;
import all.gen.cms_topic_user.t.entity.CmsTopicUserT;
import all.gen.cms_topic_user.t.vo.CmsTopicUserTVo;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicUserTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsTopicUserTService service = new CmsTopicUserTService();
		CmsTopicUserT entity = null;
		String id = request.getParameter("id");
		entity = (CmsTopicUserT) RequestThreadLocal.doRequest2EntityByJson(CmsTopicUserTVo.class, CmsTopicUserT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
