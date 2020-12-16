package c.x.platform.admin.feng.bbs.buy.bbs_post_info.action;

import c.x.platform.admin.feng.bbs.buy.bbs_post_info.service.BbsPostInfoService;
import c.x.platform.root.common.action.BaseAction;

public class BbsPostInfoDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		BbsPostInfoService service = new BbsPostInfoService();
		service.delAll(ids);
		return "index";
	}
}
