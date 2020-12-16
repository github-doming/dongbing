package all.workflow.actwf.actwf_business_def.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.workflow.actwf.actwf_business_def.t.entity.ActwfBusinessDefT;
import all.workflow.actwf.actwf_business_def.t.service.ActwfBusinessDefTService;
import all.workflow.actwf.actwf_business_def.t.vo.ActwfBusinessDefTVo;
import c.x.platform.root.common.action.BaseAction;
public class ActwfBusinessDefTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		ActwfBusinessDefTService service = new ActwfBusinessDefTService();
		ActwfBusinessDefT entity = null;
		String id = request.getParameter("id");
		entity = (ActwfBusinessDefT) RequestThreadLocal.doRequest2EntityByJson(ActwfBusinessDefTVo.class, ActwfBusinessDefT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
