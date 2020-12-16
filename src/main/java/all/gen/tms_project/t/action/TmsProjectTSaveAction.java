package all.gen.tms_project.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.tms_project.t.entity.TmsProjectT;
import all.gen.tms_project.t.service.TmsProjectTService;
import all.gen.tms_project.t.vo.TmsProjectTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TmsProjectTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsProjectTService service = new TmsProjectTService();
		TmsProjectT entity = null;
		String id = request.getParameter("tms_project.tmsProjectIdId");
		entity = (TmsProjectT) RequestThreadLocal.doRequest2Entity(TmsProjectTVo.class,TmsProjectT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
