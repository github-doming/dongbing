package all.workflow.actwf.actwf_form_def.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.workflow.actwf.actwf_form_def.t.entity.ActwfFormDefT;
import all.workflow.actwf.actwf_form_def.t.service.ActwfFormDefTService;
import all.workflow.actwf.actwf_form_def.t.vo.ActwfFormDefTVo;
import c.x.platform.root.common.action.BaseAction;
public class ActwfFormDefTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		ActwfFormDefTService service = new ActwfFormDefTService();
		ActwfFormDefT entity = null;
		String id = request.getParameter("id");
		entity = (ActwfFormDefT) RequestThreadLocal.doRequest2EntityByJson(ActwfFormDefTVo.class, ActwfFormDefT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
