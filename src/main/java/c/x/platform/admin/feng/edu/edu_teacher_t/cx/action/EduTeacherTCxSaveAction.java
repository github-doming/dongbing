package c.x.platform.admin.feng.edu.edu_teacher_t.cx.action;

import c.x.platform.admin.feng.edu.edu_teacher_t.cx.entity.EduTeacherTCx;
import c.x.platform.admin.feng.edu.edu_teacher_t.cx.service.EduTeacherTCxService;
import c.x.platform.admin.feng.edu.edu_teacher_t.cx.vo.EduTeacherTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduTeacherTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_teacher_t.id");

		EduTeacherTCx entity = (EduTeacherTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				EduTeacherTCxVo.class, EduTeacherTCx.class, request);

		EduTeacherTCxService service = new EduTeacherTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
