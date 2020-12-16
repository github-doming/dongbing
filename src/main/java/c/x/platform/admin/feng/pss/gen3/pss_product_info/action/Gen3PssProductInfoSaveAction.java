package c.x.platform.admin.feng.pss.gen3.pss_product_info.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;

public class Gen3PssProductInfoSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("pss_product_info.id");

		c.x.platform.admin.feng.pss.gen3.pss_product_info.entity.Gen3PssProductInfo entity = (c.x.platform.admin.feng.pss.gen3.pss_product_info.entity.Gen3PssProductInfo) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(
						c.x.platform.admin.feng.pss.gen3.pss_product_info.vo.Gen3PssProductInfoVo.class,
						c.x.platform.admin.feng.pss.gen3.pss_product_info.entity.Gen3PssProductInfo.class,
						request);

		c.x.platform.admin.feng.pss.gen3.pss_product_info.service.Gen3PssProductInfoService service = new c.x.platform.admin.feng.pss.gen3.pss_product_info.service.Gen3PssProductInfoService();

		// 第3表
		// {
		c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.service.Gen3PssProductgroupProductService tGen3PssProductgroupProductService = new c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.service.Gen3PssProductgroupProductService();
		c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.entity.Gen3PssProductgroupProduct tGen3PssProductgroupProduct = new c.x.platform.admin.feng.pss.gen3.pss_productgroup_product.entity.Gen3PssProductgroupProduct();
		// 树的老的ID
		String name_first$tree$id = request.getParameter("name_first$tree$id");
		// 树的新的ID
		String pss_productgroup_info$parent = request
				.getParameter("pss_productgroup_info.parent");

		request.setAttribute("first$tree$id", pss_productgroup_info$parent);
		// }
		// 第3表

		if (StringUtil.isBlank(id)) {
			id = service.save(entity);

			// 第3表保存

			tGen3PssProductgroupProduct
					.setProductgroup_id(pss_productgroup_info$parent);
			tGen3PssProductgroupProduct.setProduct_id(id);

			tGen3PssProductgroupProductService
					.save(tGen3PssProductgroupProduct);

		} else {
			service.update(entity);

			// 第3表删除
			tGen3PssProductgroupProductService.del(name_first$tree$id, id);

			// 第3表保存

			tGen3PssProductgroupProduct
					.setProductgroup_id(pss_productgroup_info$parent);
			tGen3PssProductgroupProduct.setProduct_id(id);

			tGen3PssProductgroupProductService
					.save(tGen3PssProductgroupProduct);

		}

		return "index";
	}
}
