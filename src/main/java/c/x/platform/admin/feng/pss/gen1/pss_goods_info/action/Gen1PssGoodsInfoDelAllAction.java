package c.x.platform.admin.feng.pss.gen1.pss_goods_info.action;

import c.x.platform.admin.feng.pss.gen1.pss_goods_info.service.Gen1PssGoodsInfoService;
import c.x.platform.root.common.action.BaseAction;

public class Gen1PssGoodsInfoDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		Gen1PssGoodsInfoService service = new Gen1PssGoodsInfoService();
		service.delAll(ids);
		return "index";
	}
}
