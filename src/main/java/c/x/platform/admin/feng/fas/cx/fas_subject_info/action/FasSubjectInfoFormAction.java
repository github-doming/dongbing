package c.x.platform.admin.feng.fas.cx.fas_subject_info.action;
import c.x.platform.admin.feng.fas.cx.fas_subject_info.entity.FasSubjectInfo;
import c.x.platform.admin.feng.fas.cx.fas_subject_info.service.FasSubjectInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FasSubjectInfoFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			FasSubjectInfoService service = new FasSubjectInfoService();
			FasSubjectInfo s = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", s);
		}
		return "index";
	}
}
