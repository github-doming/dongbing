package c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.action;

import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.entity.FunSingleSimpleGenTreeSimple;
import c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.service.FunSingleSimpleGenTreeSimpleService;
import c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.vo.FunSingleSimpleGenTreeSimpleVo;
import c.x.platform.root.common.action.BaseAction;

public class FunSingleSimpleGenTreeSimpleSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunSingleSimpleGenTreeSimpleService service = new FunSingleSimpleGenTreeSimpleService();

		String parent_id = request.getParameter("fun_single_simple.parent");
		String id = request.getParameter("fun_single_simple.funSingleSimpleId");
		FunSingleSimpleGenTreeSimple entity = (FunSingleSimpleGenTreeSimple) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(FunSingleSimpleGenTreeSimpleVo.class,
						FunSingleSimpleGenTreeSimple.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();

	}
}
