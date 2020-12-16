package all.task.tms.tms_function.t.action;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.task.tms.tms_function.t.entity.TmsFunctionT;
import all.task.tms.tms_function.t.service.TmsFunctionTService;
import all.task.tms.tms_function.t.vo.TmsFunctionTVo;
public class TmsFunctionTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		TmsFunctionTService service = new TmsFunctionTService();
		String parent_id = request.getParameter("tms_function.parent");
		String id = request.getParameter("tms_function.tmsFunctionId");
		TmsFunctionT entity = (TmsFunctionT) RequestThreadLocal.doRequest2Entity(
		TmsFunctionTVo.class, TmsFunctionT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
