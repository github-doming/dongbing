package all.gen.cms_board.t.action;
import all.gen.cms_board.t.service.CmsBoardTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoardTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		CmsBoardTService service = new CmsBoardTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
