package all.workflow.actwf.actwf_business_def.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.workflow.actwf.actwf_business_def.t.entity.ActwfBusinessDefT;
import all.workflow.actwf.actwf_business_def.t.service.ActwfBusinessDefTService;
import all.workflow.actwf.actwf_business_def.t.vo.ActwfBusinessDefTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActwfBusinessDefTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		ActwfBusinessDefTService service = new ActwfBusinessDefTService();
		ActwfBusinessDefT entity = null;
		String id = request.getParameter("actwf_business_def.actwfBusinessDefId");
		entity = (ActwfBusinessDefT) RequestThreadLocal.doRequest2Entity(ActwfBusinessDefTVo.class,ActwfBusinessDefT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
