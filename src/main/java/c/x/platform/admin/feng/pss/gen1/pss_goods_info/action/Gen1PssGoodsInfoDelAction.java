package c.x.platform.admin.feng.pss.gen1.pss_goods_info.action;

import java.util.ArrayList;
import java.util.List;

import c.x.platform.admin.feng.pss.gen1.pss_goods_info.service.Gen1PssGoodsInfoService;
import c.x.platform.root.common.action.BaseAction;

public class Gen1PssGoodsInfoDelAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		Gen1PssGoodsInfoService service = new Gen1PssGoodsInfoService();
		service.del(id);
		List<String> list_msg = new ArrayList<String>();
		list_msg.add("信息");
		list_msg.add("删除成功");
		request.setAttribute("msg", list_msg);
		return "index";
	}
}
