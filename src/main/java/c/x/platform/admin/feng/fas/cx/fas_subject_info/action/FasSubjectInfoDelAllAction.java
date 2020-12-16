package c.x.platform.admin.feng.fas.cx.fas_subject_info.action;
import c.x.platform.admin.feng.fas.cx.fas_subject_info.service.FasSubjectInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FasSubjectInfoDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		FasSubjectInfoService service = new FasSubjectInfoService();
		service.delAll(ids);
		return "index";
	}
}
