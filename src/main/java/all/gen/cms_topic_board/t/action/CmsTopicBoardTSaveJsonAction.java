package all.gen.cms_topic_board.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_topic_board.t.entity.CmsTopicBoardT;
import all.gen.cms_topic_board.t.service.CmsTopicBoardTService;
import all.gen.cms_topic_board.t.vo.CmsTopicBoardTVo;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicBoardTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsTopicBoardTService service = new CmsTopicBoardTService();
		CmsTopicBoardT entity = null;
		String id = request.getParameter("id");
		entity = (CmsTopicBoardT) RequestThreadLocal.doRequest2EntityByJson(CmsTopicBoardTVo.class, CmsTopicBoardT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
