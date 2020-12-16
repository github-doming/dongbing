package c.x.platform.admin.feng.pss.gen1.pss_goods_info.action;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import c.x.platform.admin.feng.pss.gen1.pss_goods_info.entity.Gen1PssGoodsInfo;
import c.x.platform.admin.feng.pss.gen1.pss_goods_info.service.Gen1PssGoodsInfoService;
import c.x.platform.root.common.action.BaseRoleAction;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.bean.BeanThreadLocal;
public class Gen1PssGoodsInfoListAction extends BaseRoleAction {

	@Override
	public String execute() throws Exception {

		// 排序

		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request
				.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);

		// 分页
		Integer pageNo = BeanThreadLocal.findThreadLocal().get().find(
				request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(
				request.getParameter(SysConfig.pageSizeName), 10);

		Gen1PssGoodsInfoService service = new Gen1PssGoodsInfoService();
		PageCoreBean<Map<String, Object>> pageBean = service.find(sortOrderName,
				sortFieldName, pageNo, pageSize);

		List<Map<String, Object>> listMap = pageBean.getList();

		request.setAttribute("cPage", pageBean);
		request.setAttribute("list", listMap);

		return "index";
	}
}
