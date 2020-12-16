package all.gen.fun_type_int.t.action;

import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.fun_type_int.t.entity.FunTypeIntT;
import all.gen.fun_type_int.t.service.FunTypeIntTService;
import all.gen.fun_type_int.t.vo.FunTypeIntTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeIntTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunTypeIntTService service = new FunTypeIntTService();
		FunTypeIntT entity = null;
	
		
			String id = request.getParameter("fun_type_int.id");
			entity = (FunTypeIntT) RequestThreadLocal.findThreadLocal().get()
					.doRequest2Entity(FunTypeIntTVo.class,
							FunTypeIntT.class, request);
			if (StringUtil.isBlank(id)) {
				service.save(entity);
			} else {
				service.update(entity);
			}
		return CommViewEnum.Default.toString();
		
	}
}
