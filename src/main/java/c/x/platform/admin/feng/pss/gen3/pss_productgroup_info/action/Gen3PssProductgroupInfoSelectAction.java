package c.x.platform.admin.feng.pss.gen3.pss_productgroup_info.action;

import c.x.platform.admin.feng.pss.gen3.pss_productgroup_info.entity.Gen3PssProductgroupInfo;
import c.x.platform.admin.feng.pss.gen3.pss_productgroup_info.service.Gen3PssProductgroupInfoService;
import c.x.platform.root.common.action.BaseAction;

public class Gen3PssProductgroupInfoSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		Gen3PssProductgroupInfoService service = new Gen3PssProductgroupInfoService();
		String parent_id = request.getParameter("parent_id");
		Gen3PssProductgroupInfo s = service.find(parent_id);
		request.setAttribute("s", s);
		return "index";
	}
}
