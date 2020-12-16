package all.gen.alipay_log_notify.t.action;
import all.gen.alipay_log_notify.t.service.AlipayLogNotifyTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogNotifyTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AlipayLogNotifyTService service = new AlipayLogNotifyTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
