package all.gen.fun_type_long.t.action;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.fun_type_long.t.entity.FunTypeLongT;
import all.gen.fun_type_long.t.service.FunTypeLongTService;
import all.gen.fun_type_long.t.vo.FunTypeLongTVo;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeLongTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunTypeLongTService service = new FunTypeLongTService();
		FunTypeLongT entity = null;
		String id = request.getParameter("id");
		entity = (FunTypeLongT) RequestThreadLocal.doRequest2EntityByJson(FunTypeLongTVo.class, FunTypeLongT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
