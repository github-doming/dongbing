package all.gen.cms_box_msg.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_box_msg.t.entity.CmsBoxMsgT;
import all.gen.cms_box_msg.t.service.CmsBoxMsgTService;
import all.gen.cms_box_msg.t.vo.CmsBoxMsgTVo;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoxMsgTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsBoxMsgTService service = new CmsBoxMsgTService();
		CmsBoxMsgT entity = null;
		String id = request.getParameter("id");
		entity = (CmsBoxMsgT) RequestThreadLocal.doRequest2EntityByJson(CmsBoxMsgTVo.class, CmsBoxMsgT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
