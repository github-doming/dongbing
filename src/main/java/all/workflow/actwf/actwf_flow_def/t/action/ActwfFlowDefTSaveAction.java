package all.workflow.actwf.actwf_flow_def.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.workflow.actwf.actwf_flow_def.t.entity.ActwfFlowDefT;
import all.workflow.actwf.actwf_flow_def.t.service.ActwfFlowDefTService;
import all.workflow.actwf.actwf_flow_def.t.vo.ActwfFlowDefTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFlowDefTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		ActwfFlowDefTService service = new ActwfFlowDefTService();
		ActwfFlowDefT entity = null;
		String id = request.getParameter("actwf_flow_def.actwfFlowDefId");
		entity = (ActwfFlowDefT) RequestThreadLocal.doRequest2Entity(ActwfFlowDefTVo.class,ActwfFlowDefT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
