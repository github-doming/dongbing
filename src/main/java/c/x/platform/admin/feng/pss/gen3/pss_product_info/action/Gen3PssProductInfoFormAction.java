package c.x.platform.admin.feng.pss.gen3.pss_product_info.action;

import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;

public class Gen3PssProductInfoFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {

		c.x.platform.admin.feng.pss.gen3.pss_product_info.service.Gen3PssProductInfoService sGen3PssProductInfoService = new c.x.platform.admin.feng.pss.gen3.pss_product_info.service.Gen3PssProductInfoService();
		c.x.platform.admin.feng.pss.gen3.pss_productgroup_info.service.Gen3PssProductgroupInfoService fGen3PssProductgroupInfoService = new c.x.platform.admin.feng.pss.gen3.pss_productgroup_info.service.Gen3PssProductgroupInfoService();

		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		// BaseLog.trace("id=" + id);

		if (id == null) {

		}

		if (id != null) {
			// 本身
			c.x.platform.admin.feng.pss.gen3.pss_product_info.entity.Gen3PssProductInfo s = sGen3PssProductInfoService
					.find(id);

			request.setAttribute("s", s);

		}

		// 树节点id
		// {
		// first$tree$id
		String first$tree$id = (String) request.getParameter("first$tree$id");

		request.setAttribute("value_first$tree$id", first$tree$id);

		// 选择上一级菜单
		if (StringUtil.isBlank(first$tree$id)) {

		} else {

			first$tree$id = first$tree$id.trim();

			c.x.platform.admin.feng.pss.gen3.pss_productgroup_info.entity.Gen3PssProductgroupInfo p = fGen3PssProductgroupInfoService
					.find(first$tree$id);

			request.setAttribute("p", p);

			// 树名称

			request.setAttribute("value_first$tree$name", p.getName());

		}

		// }
		// 树节点id

		return "index";
	}
}
