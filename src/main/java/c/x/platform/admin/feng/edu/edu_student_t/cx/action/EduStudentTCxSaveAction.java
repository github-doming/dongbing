package c.x.platform.admin.feng.edu.edu_student_t.cx.action;

import c.x.platform.admin.feng.edu.edu_student_t.cx.entity.EduStudentTCx;
import c.x.platform.admin.feng.edu.edu_student_t.cx.service.EduStudentTCxService;
import c.x.platform.admin.feng.edu.edu_student_t.cx.vo.EduStudentTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduStudentTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_student_t.id");

		EduStudentTCx entity = (EduStudentTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				EduStudentTCxVo.class, EduStudentTCx.class, request);

		EduStudentTCxService service = new EduStudentTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
