package c.x.platform.admin.feng.pss.gen1.pss_goods_info.action;

import c.x.platform.admin.feng.pss.gen1.pss_goods_info.entity.Gen1PssGoodsInfo;
import c.x.platform.admin.feng.pss.gen1.pss_goods_info.service.Gen1PssGoodsInfoService;
import c.x.platform.admin.feng.pss.gen1.pss_goods_info.vo.Gen1PssGoodsInfoVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class Gen1PssGoodsInfoSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("pss_goods_info.id");

		Gen1PssGoodsInfo entity = (Gen1PssGoodsInfo) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(Gen1PssGoodsInfoVo.class,
						Gen1PssGoodsInfo.class, request);

		Gen1PssGoodsInfoService service = new Gen1PssGoodsInfoService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
