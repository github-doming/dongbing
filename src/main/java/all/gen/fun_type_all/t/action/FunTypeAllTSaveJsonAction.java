package all.gen.fun_type_all.t.action;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.fun_type_all.t.entity.FunTypeAllT;
import all.gen.fun_type_all.t.service.FunTypeAllTService;
import all.gen.fun_type_all.t.vo.FunTypeAllTVo;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeAllTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunTypeAllTService service = new FunTypeAllTService();
		FunTypeAllT entity = null;
		String id = request.getParameter("id");
		entity = (FunTypeAllT) RequestThreadLocal.doRequest2EntityByJson(FunTypeAllTVo.class, FunTypeAllT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
