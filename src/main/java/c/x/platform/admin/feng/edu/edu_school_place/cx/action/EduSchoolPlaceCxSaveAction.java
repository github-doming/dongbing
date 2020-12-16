package c.x.platform.admin.feng.edu.edu_school_place.cx.action;

import c.x.platform.admin.feng.edu.edu_school_place.cx.entity.EduSchoolPlaceCx;
import c.x.platform.admin.feng.edu.edu_school_place.cx.service.EduSchoolPlaceCxService;
import c.x.platform.admin.feng.edu.edu_school_place.cx.vo.EduSchoolPlaceCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduSchoolPlaceCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_school_place.id");

		EduSchoolPlaceCx entity = (EduSchoolPlaceCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(EduSchoolPlaceCxVo.class,
						EduSchoolPlaceCx.class, request);

		EduSchoolPlaceCxService service = new EduSchoolPlaceCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
