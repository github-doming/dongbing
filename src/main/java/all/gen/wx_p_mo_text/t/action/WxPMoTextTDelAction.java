package all.gen.wx_p_mo_text.t.action;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.wx_p_mo_text.t.service.WxPMoTextTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMoTextTDelAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		WxPMoTextTService service = new WxPMoTextTService();
		service.del(id);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
