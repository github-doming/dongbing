package all.workflow.actwf.actwf_flow_def.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.workflow.actwf.actwf_flow_def.t.entity.ActwfFlowDefT;
import all.workflow.actwf.actwf_flow_def.t.service.ActwfFlowDefTService;
import all.workflow.actwf.actwf_flow_def.t.vo.ActwfFlowDefTVo;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFlowDefTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		ActwfFlowDefTService service = new ActwfFlowDefTService();
		ActwfFlowDefT entity = null;
		String id = request.getParameter("id");
		entity = (ActwfFlowDefT) RequestThreadLocal.doRequest2EntityByJson(ActwfFlowDefTVo.class, ActwfFlowDefT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
