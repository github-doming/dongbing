package all.gen.cms_msg.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_msg.t.entity.CmsMsgT;
import all.gen.cms_msg.t.service.CmsMsgTService;
import all.gen.cms_msg.t.vo.CmsMsgTVo;
import c.x.platform.root.common.action.BaseAction;
public class CmsMsgTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsMsgTService service = new CmsMsgTService();
		CmsMsgT entity = null;
		String id = request.getParameter("id");
		entity = (CmsMsgT) RequestThreadLocal.doRequest2EntityByJson(CmsMsgTVo.class, CmsMsgT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
