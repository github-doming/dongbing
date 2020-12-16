package all.cms.msg.admin.cms_msg.t.action;
import all.cms.msg.admin.cms_msg.t.service.CmsMsgTService;
import all.gen.cms_msg.t.entity.CmsMsgT;
import all.gen.cms_msg.t.vo.CmsMsgTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class CmsMsgTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsMsgTService service = new CmsMsgTService();
		CmsMsgT entity = null;
		String id = request.getParameter("cms_msg.cmsMsgId");
		entity = (CmsMsgT) RequestThreadLocal.doRequest2Entity(CmsMsgTVo.class,CmsMsgT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
