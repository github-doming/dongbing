package c.x.platform.admin.feng.fas.cx.fas_subject_info.action;
import c.x.platform.admin.feng.fas.cx.fas_subject_info.entity.FasSubjectInfo;
import c.x.platform.admin.feng.fas.cx.fas_subject_info.service.FasSubjectInfoService;
import c.x.platform.admin.feng.fas.cx.fas_subject_info.vo.FasSubjectInfoVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
public class FasSubjectInfoSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("fas_subject_info.id");

		FasSubjectInfo s = (FasSubjectInfo) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				FasSubjectInfoVo.class, FasSubjectInfo.class, request);
		FasSubjectInfoService service = new FasSubjectInfoService();
		if (StringUtil.isBlank(id)) {
			service.insert(s);
		} else {
			service.update(s);
		}
		return "index";
	}
}
