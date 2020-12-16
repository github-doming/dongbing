package c.x.platform.admin.feng.edu.edu_class_place.cx.action;

import c.x.platform.admin.feng.edu.edu_class_place.cx.entity.EduClassPlaceCx;
import c.x.platform.admin.feng.edu.edu_class_place.cx.service.EduClassPlaceCxService;
import c.x.platform.admin.feng.edu.edu_class_place.cx.vo.EduClassPlaceCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduClassPlaceCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_class_place.id");

		EduClassPlaceCx entity = (EduClassPlaceCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(EduClassPlaceCxVo.class,
						EduClassPlaceCx.class, request);

		EduClassPlaceCxService service = new EduClassPlaceCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
