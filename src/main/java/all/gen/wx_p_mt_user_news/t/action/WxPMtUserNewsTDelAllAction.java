package all.gen.wx_p_mt_user_news.t.action;
import all.gen.wx_p_mt_user_news.t.service.WxPMtUserNewsTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtUserNewsTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPMtUserNewsTService service = new WxPMtUserNewsTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
