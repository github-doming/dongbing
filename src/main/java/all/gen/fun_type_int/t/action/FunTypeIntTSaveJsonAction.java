package all.gen.fun_type_int.t.action;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.fun_type_int.t.entity.FunTypeIntT;
import all.gen.fun_type_int.t.service.FunTypeIntTService;
import all.gen.fun_type_int.t.vo.FunTypeIntTVo;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeIntTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunTypeIntTService service = new FunTypeIntTService();
		FunTypeIntT entity = null;
		String id = request.getParameter("id");
		entity = (FunTypeIntT) RequestThreadLocal.doRequest2EntityByJson(FunTypeIntTVo.class, FunTypeIntT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
