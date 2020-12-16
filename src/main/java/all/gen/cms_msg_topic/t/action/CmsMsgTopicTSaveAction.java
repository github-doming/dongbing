package all.gen.cms_msg_topic.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_msg_topic.t.entity.CmsMsgTopicT;
import all.gen.cms_msg_topic.t.service.CmsMsgTopicTService;
import all.gen.cms_msg_topic.t.vo.CmsMsgTopicTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsMsgTopicTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsMsgTopicTService service = new CmsMsgTopicTService();
		CmsMsgTopicT entity = null;
		String id = request.getParameter("cms_msg_topic.cmsMsgTopicId");
		entity = (CmsMsgTopicT) RequestThreadLocal.doRequest2Entity(CmsMsgTopicTVo.class,CmsMsgTopicT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
