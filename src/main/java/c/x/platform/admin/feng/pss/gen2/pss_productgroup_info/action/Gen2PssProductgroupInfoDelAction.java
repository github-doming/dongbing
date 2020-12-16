package c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.action;

import c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.service.Gen2PssProductgroupInfoService;
import c.x.platform.root.common.action.BaseAction;

public class Gen2PssProductgroupInfoDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		Gen2PssProductgroupInfoService service = new Gen2PssProductgroupInfoService();
		service.del(id);
		return "index";
	}
}
