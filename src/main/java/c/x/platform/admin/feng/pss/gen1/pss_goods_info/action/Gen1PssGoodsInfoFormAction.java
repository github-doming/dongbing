package c.x.platform.admin.feng.pss.gen1.pss_goods_info.action;

import c.x.platform.admin.feng.pss.gen1.pss_goods_info.entity.Gen1PssGoodsInfo;
import c.x.platform.admin.feng.pss.gen1.pss_goods_info.service.Gen1PssGoodsInfoService;
import c.x.platform.root.common.action.BaseAction;

public class Gen1PssGoodsInfoFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			Gen1PssGoodsInfoService service = new Gen1PssGoodsInfoService();
			Gen1PssGoodsInfo entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
