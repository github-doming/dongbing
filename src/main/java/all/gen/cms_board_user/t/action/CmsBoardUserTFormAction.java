package all.gen.cms_board_user.t.action;

import all.gen.cms_board_user.t.entity.CmsBoardUserT;
import all.gen.cms_board_user.t.service.CmsBoardUserTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoardUserTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			CmsBoardUserTService service = new CmsBoardUserTService();
			CmsBoardUserT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
