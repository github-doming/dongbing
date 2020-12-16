package c.x.platform.admin.feng.edu.edu_class_teacher.cx.action;

import c.x.platform.admin.feng.edu.edu_class_teacher.cx.entity.EduClassTeacherCx;
import c.x.platform.admin.feng.edu.edu_class_teacher.cx.service.EduClassTeacherCxService;
import c.x.platform.admin.feng.edu.edu_class_teacher.cx.vo.EduClassTeacherCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduClassTeacherCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_class_teacher.id");

		EduClassTeacherCx entity = (EduClassTeacherCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(EduClassTeacherCxVo.class,
						EduClassTeacherCx.class, request);

		EduClassTeacherCxService service = new EduClassTeacherCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
