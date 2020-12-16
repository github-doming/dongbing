package all.cms.msg.admin.cms_msg_topic.t.action;

import all.cms.msg.admin.cms_msg_topic.t.service.CmsMsgTopicTService;
import all.gen.cms_msg_topic.t.entity.CmsMsgTopicT;
import all.gen.cms_msg_topic.t.vo.CmsMsgTopicTVo;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class CmsMsgTopicTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsMsgTopicTService service = new CmsMsgTopicTService();
		CmsMsgTopicT entity = null;
		String id = request.getParameter("id");
		entity = (CmsMsgTopicT) RequestThreadLocal.doRequest2EntityByJson(CmsMsgTopicTVo.class, CmsMsgTopicT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
