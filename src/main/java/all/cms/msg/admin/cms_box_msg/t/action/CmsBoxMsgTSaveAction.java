package all.cms.msg.admin.cms_box_msg.t.action;
import all.cms.msg.admin.cms_box_msg.t.service.CmsBoxMsgTService;
import all.gen.cms_box_msg.t.entity.CmsBoxMsgT;
import all.gen.cms_box_msg.t.vo.CmsBoxMsgTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxMsgTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsBoxMsgTService service = new CmsBoxMsgTService();
		CmsBoxMsgT entity = null;
		String id = request.getParameter("cms_box_msg.cmsBoxMsgId");
		entity = (CmsBoxMsgT) RequestThreadLocal.doRequest2Entity(CmsBoxMsgTVo.class,CmsBoxMsgT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
