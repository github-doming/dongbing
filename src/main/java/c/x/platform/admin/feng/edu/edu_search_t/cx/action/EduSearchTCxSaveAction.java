package c.x.platform.admin.feng.edu.edu_search_t.cx.action;

import c.x.platform.admin.feng.edu.edu_search_t.cx.entity.EduSearchTCx;
import c.x.platform.admin.feng.edu.edu_search_t.cx.service.EduSearchTCxService;
import c.x.platform.admin.feng.edu.edu_search_t.cx.vo.EduSearchTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduSearchTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_search_t.id");

		EduSearchTCx entity = (EduSearchTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				EduSearchTCxVo.class, EduSearchTCx.class, request);

		EduSearchTCxService service = new EduSearchTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
