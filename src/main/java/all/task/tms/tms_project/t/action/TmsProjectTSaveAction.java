package all.task.tms.tms_project.t.action;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.task.tms.tms_project.t.entity.TmsProjectT;
import all.task.tms.tms_project.t.service.TmsProjectTService;
import all.task.tms.tms_project.t.vo.TmsProjectTVo;
public class TmsProjectTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsProjectTService service = new TmsProjectTService();
		String parent_id = request.getParameter("tms_project.parent");
		String id = request.getParameter("tms_project.tmsProjectId");
		TmsProjectT entity = (TmsProjectT) RequestThreadLocal.doRequest2Entity(
		TmsProjectTVo.class, TmsProjectT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
