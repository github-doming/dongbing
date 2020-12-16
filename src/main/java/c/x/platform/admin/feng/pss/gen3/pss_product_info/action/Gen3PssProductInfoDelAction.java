package c.x.platform.admin.feng.pss.gen3.pss_product_info.action;

import java.util.ArrayList;
import java.util.List;
import c.x.platform.root.common.action.BaseAction;

public class Gen3PssProductInfoDelAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		c.x.platform.admin.feng.pss.gen3.pss_product_info.service.Gen3PssProductInfoService service = new c.x.platform.admin.feng.pss.gen3.pss_product_info.service.Gen3PssProductInfoService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);

		// 删除第3表
		// 第3表
		c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.service.Gen3PssProductgroupProductService tGen3PssProductgroupProductService = new c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.service.Gen3PssProductgroupProductService();
		// 第3表删除
		tGen3PssProductgroupProductService.del_by_userId(id);

		return "index";
	}
}
