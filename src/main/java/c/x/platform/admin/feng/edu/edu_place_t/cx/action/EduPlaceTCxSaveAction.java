package c.x.platform.admin.feng.edu.edu_place_t.cx.action;

import c.x.platform.admin.feng.edu.edu_place_t.cx.entity.EduPlaceTCx;
import c.x.platform.admin.feng.edu.edu_place_t.cx.service.EduPlaceTCxService;
import c.x.platform.admin.feng.edu.edu_place_t.cx.vo.EduPlaceTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduPlaceTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_place_t.id");

		EduPlaceTCx entity = (EduPlaceTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				EduPlaceTCxVo.class, EduPlaceTCx.class, request);

		EduPlaceTCxService service = new EduPlaceTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
