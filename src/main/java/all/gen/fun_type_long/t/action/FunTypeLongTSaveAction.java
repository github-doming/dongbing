package all.gen.fun_type_long.t.action;

import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.fun_type_long.t.entity.FunTypeLongT;
import all.gen.fun_type_long.t.service.FunTypeLongTService;
import all.gen.fun_type_long.t.vo.FunTypeLongTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeLongTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunTypeLongTService service = new FunTypeLongTService();
		FunTypeLongT entity = null;
	
		
			String id = request.getParameter("fun_type_long.id");
			entity = (FunTypeLongT) RequestThreadLocal.findThreadLocal().get()
					.doRequest2Entity(FunTypeLongTVo.class,
							FunTypeLongT.class, request);
			if (StringUtil.isBlank(id)) {
				service.save(entity);
			} else {
				service.update(entity);
			}
		return CommViewEnum.Default.toString();
		
	}
}
