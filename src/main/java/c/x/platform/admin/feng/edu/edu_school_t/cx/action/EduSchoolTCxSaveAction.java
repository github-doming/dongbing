package c.x.platform.admin.feng.edu.edu_school_t.cx.action;

import c.x.platform.admin.feng.edu.edu_school_t.cx.entity.EduSchoolTCx;
import c.x.platform.admin.feng.edu.edu_school_t.cx.service.EduSchoolTCxService;
import c.x.platform.admin.feng.edu.edu_school_t.cx.vo.EduSchoolTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduSchoolTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_school_t.id");

		EduSchoolTCx entity = (EduSchoolTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				EduSchoolTCxVo.class, EduSchoolTCx.class, request);

		EduSchoolTCxService service = new EduSchoolTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
