package c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.action;

import c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.entity.Gen2PssProductgroupInfo;
import c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.service.Gen2PssProductgroupInfoService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;

public class Gen2PssProductgroupInfoFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		Gen2PssProductgroupInfoService service = new Gen2PssProductgroupInfoService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		if (id == null) {
			// 选择上一级菜单
			String parent_id = (String) request.getParameter("parent_id");
			if (StringUtil.isBlank(parent_id)) {
			} else {
				parent_id = parent_id.trim();
				Gen2PssProductgroupInfo p = service.find(parent_id);
				request.setAttribute("p", p);
			}
		}
		if (id != null) {
			id = id.trim();
			// 本身
			Gen2PssProductgroupInfo s = service.find(id);
			request.setAttribute("s", s);
			// 选择上一级菜单
			String long_parent_id = null;
			Gen2PssProductgroupInfo p = null;
			// 上一级
			long_parent_id = s.getParent();
			p = service.find(long_parent_id.toString());
			request.setAttribute("p", p);
		}
		return "index";
	}
}
