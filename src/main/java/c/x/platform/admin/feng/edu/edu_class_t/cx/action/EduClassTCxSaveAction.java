package c.x.platform.admin.feng.edu.edu_class_t.cx.action;

import c.x.platform.admin.feng.edu.edu_class_t.cx.entity.EduClassTCx;
import c.x.platform.admin.feng.edu.edu_class_t.cx.service.EduClassTCxService;
import c.x.platform.admin.feng.edu.edu_class_t.cx.vo.EduClassTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduClassTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_class_t.id");

		EduClassTCx entity = (EduClassTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				EduClassTCxVo.class, EduClassTCx.class, request);

		EduClassTCxService service = new EduClassTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
