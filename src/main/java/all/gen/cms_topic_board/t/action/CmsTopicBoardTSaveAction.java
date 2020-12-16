package all.gen.cms_topic_board.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_topic_board.t.entity.CmsTopicBoardT;
import all.gen.cms_topic_board.t.service.CmsTopicBoardTService;
import all.gen.cms_topic_board.t.vo.CmsTopicBoardTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsTopicBoardTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsTopicBoardTService service = new CmsTopicBoardTService();
		CmsTopicBoardT entity = null;
		String id = request.getParameter("cms_topic_board.cmsTopicBoardId");
		entity = (CmsTopicBoardT) RequestThreadLocal.doRequest2Entity(CmsTopicBoardTVo.class,CmsTopicBoardT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
