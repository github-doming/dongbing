package c.x.platform.admin.feng.edu.edu_school_class.cx.action;

import c.x.platform.admin.feng.edu.edu_school_class.cx.entity.EduSchoolClassCx;
import c.x.platform.admin.feng.edu.edu_school_class.cx.service.EduSchoolClassCxService;
import c.x.platform.admin.feng.edu.edu_school_class.cx.vo.EduSchoolClassCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class EduSchoolClassCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("edu_school_class.id");

		EduSchoolClassCx entity = (EduSchoolClassCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(EduSchoolClassCxVo.class,
						EduSchoolClassCx.class, request);

		EduSchoolClassCxService service = new EduSchoolClassCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
