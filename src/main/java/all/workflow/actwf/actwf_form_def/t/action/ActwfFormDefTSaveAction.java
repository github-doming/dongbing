package all.workflow.actwf.actwf_form_def.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.workflow.actwf.actwf_form_def.t.entity.ActwfFormDefT;
import all.workflow.actwf.actwf_form_def.t.service.ActwfFormDefTService;
import all.workflow.actwf.actwf_form_def.t.vo.ActwfFormDefTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFormDefTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		ActwfFormDefTService service = new ActwfFormDefTService();
		ActwfFormDefT entity = null;
		String id = request.getParameter("actwf_form_def.actwfFormDefId");
		entity = (ActwfFormDefT) RequestThreadLocal.doRequest2Entity(ActwfFormDefTVo.class,ActwfFormDefT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
