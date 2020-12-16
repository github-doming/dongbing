package all.gen.cms_board.t.action;

import all.gen.cms_board.t.entity.CmsBoardT;
import all.gen.cms_board.t.service.CmsBoardTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoardTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsBoardTService service = new CmsBoardTService();
			CmsBoardT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
