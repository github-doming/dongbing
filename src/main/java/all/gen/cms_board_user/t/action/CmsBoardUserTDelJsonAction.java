package all.gen.cms_board_user.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_board_user.t.service.CmsBoardUserTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoardUserTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsBoardUserTService service = new CmsBoardUserTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
