package all.gen.fun_type_str_queue.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.fun_type_str_queue.t.entity.FunTypeStrQueueT;
import all.gen.fun_type_str_queue.t.service.FunTypeStrQueueTService;
import all.gen.fun_type_str_queue.t.vo.FunTypeStrQueueTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class FunTypeStrQueueTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		FunTypeStrQueueTService service = new FunTypeStrQueueTService();
		FunTypeStrQueueT entity = null;
		String id = request.getParameter("fun_type_str_queue.funTypeStrQueueId");
		entity = (FunTypeStrQueueT) RequestThreadLocal.doRequest2Entity(FunTypeStrQueueTVo.class,FunTypeStrQueueT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
