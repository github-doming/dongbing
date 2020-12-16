package all.gen.cms_send_box_draft.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_send_box_draft.t.entity.CmsSendBoxDraftT;
import all.gen.cms_send_box_draft.t.service.CmsSendBoxDraftTService;
import all.gen.cms_send_box_draft.t.vo.CmsSendBoxDraftTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsSendBoxDraftTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsSendBoxDraftTService service = new CmsSendBoxDraftTService();
		CmsSendBoxDraftT entity = null;
		String id = request.getParameter("cms_send_box_draft.cmsSendBoxDraftId");
		entity = (CmsSendBoxDraftT) RequestThreadLocal.doRequest2Entity(CmsSendBoxDraftTVo.class,CmsSendBoxDraftT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
