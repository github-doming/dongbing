package all.gen.tms_task.t.action;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.gen.tms_task.t.entity.TmsTaskT;
import all.gen.tms_task.t.service.TmsTaskTService;
import all.gen.tms_task.t.vo.TmsTaskTVo;
public class TmsTaskTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsTaskTService service = new TmsTaskTService();
		String parent_id = request.getParameter("tms_task.parent");
		String id = request.getParameter("tms_task.tmsTaskId");
		TmsTaskT entity = (TmsTaskT) RequestThreadLocal.doRequest2Entity(
		TmsTaskTVo.class, TmsTaskT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
