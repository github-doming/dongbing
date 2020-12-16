package all.gen.fun_type_all.t.action;

import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.fun_type_all.t.entity.FunTypeAllT;
import all.gen.fun_type_all.t.service.FunTypeAllTService;
import all.gen.fun_type_all.t.vo.FunTypeAllTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeAllTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunTypeAllTService service = new FunTypeAllTService();
		FunTypeAllT entity = null;
	
		
			String id = request.getParameter("fun_type_all.id");
			entity = (FunTypeAllT) RequestThreadLocal.findThreadLocal().get()
					.doRequest2Entity(FunTypeAllTVo.class,
							FunTypeAllT.class, request);
			if (StringUtil.isBlank(id)) {
				service.save(entity);
			} else {
				service.update(entity);
			}
		return CommViewEnum.Default.toString();
		
	}
}
