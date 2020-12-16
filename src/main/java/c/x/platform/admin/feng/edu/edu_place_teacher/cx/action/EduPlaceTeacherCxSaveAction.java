package c.x.platform.admin.feng.edu.edu_place_teacher.cx.action;

import c.x.platform.admin.feng.edu.edu_place_teacher.cx.entity.EduPlaceTeacherCx;
import c.x.platform.admin.feng.edu.edu_place_teacher.cx.service.EduPlaceTeacherCxService;
import c.x.platform.admin.feng.edu.edu_place_teacher.cx.vo.EduPlaceTeacherCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduPlaceTeacherCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_place_teacher.id");

		EduPlaceTeacherCx entity = (EduPlaceTeacherCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(EduPlaceTeacherCxVo.class,
						EduPlaceTeacherCx.class, request);

		EduPlaceTeacherCxService service = new EduPlaceTeacherCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
