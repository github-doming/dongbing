package all.gen.fun_type_str.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.fun_type_str.t.entity.FunTypeStrT;
import all.gen.fun_type_str.t.service.FunTypeStrTService;
import all.gen.fun_type_str.t.vo.FunTypeStrTVo;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeStrTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunTypeStrTService service = new FunTypeStrTService();
		FunTypeStrT entity = null;
		String id = request.getParameter("id");
		entity = (FunTypeStrT) RequestThreadLocal.doRequest2EntityByJson(FunTypeStrTVo.class, FunTypeStrT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
