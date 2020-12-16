package c.x.platform.admin.feng.edu.edu_class_student.cx.action;

import c.x.platform.admin.feng.edu.edu_class_student.cx.entity.EduClassStudentCx;
import c.x.platform.admin.feng.edu.edu_class_student.cx.service.EduClassStudentCxService;
import c.x.platform.admin.feng.edu.edu_class_student.cx.vo.EduClassStudentCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduClassStudentCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_class_student.id");

		EduClassStudentCx entity = (EduClassStudentCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(EduClassStudentCxVo.class,
						EduClassStudentCx.class, request);

		EduClassStudentCxService service = new EduClassStudentCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
