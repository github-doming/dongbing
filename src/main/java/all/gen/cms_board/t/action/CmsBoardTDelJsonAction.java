package all.gen.cms_board.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.cms_board.t.service.CmsBoardTService;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoardTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		CmsBoardTService service = new CmsBoardTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
