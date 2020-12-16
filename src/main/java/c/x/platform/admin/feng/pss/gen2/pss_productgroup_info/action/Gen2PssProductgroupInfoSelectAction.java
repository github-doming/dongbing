package c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.action;

import c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.entity.Gen2PssProductgroupInfo;
import c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.service.Gen2PssProductgroupInfoService;
import c.x.platform.root.common.action.BaseAction;

public class Gen2PssProductgroupInfoSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		Gen2PssProductgroupInfoService service = new Gen2PssProductgroupInfoService();
		String parent_id = request.getParameter("parent_id");
		Gen2PssProductgroupInfo s = service.find(parent_id);
		request.setAttribute("s", s);
		return "index";
	}
}
