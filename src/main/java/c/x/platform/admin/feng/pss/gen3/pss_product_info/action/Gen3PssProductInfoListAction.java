package c.x.platform.admin.feng.pss.gen3.pss_product_info.action;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import c.x.platform.root.common.action.BaseAction;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.string.StringUtil;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.bean.BeanThreadLocal;
public class Gen3PssProductInfoListAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		// 树节点id
		// {
		// first$tree$id
		String first$tree$id = (String) request.getParameter("first$tree$id");

		// BaseLog.trace("first$tree$id="+first$tree$id);
		if (false) {

			String pss_productgroup_info$parent = request.getParameter("pss_productgroup_info.parent");

			// BaseLog.trace("pss_productgroup_info$parent="+pss_productgroup_info$parent);
		}

		if (StringUtil.isNotBlank(first$tree$id)) {
			String[] array_first$tree$id = first$tree$id.split("_");
			if (array_first$tree$id.length > 1) {
				// 从树节点加载list列表
				first$tree$id = array_first$tree$id[2];
			} else {
				// 从form表单退回list列表

			}

		} else {
			// 从form表单提交或编辑保存到list列表，或删除到list列表
			first$tree$id = (String) request.getAttribute("first$tree$id");

		}

		// BaseLog.trace("first$tree$id="+first$tree$id);

		request.setAttribute("value_first$tree$id", first$tree$id);
		// }
		// 树节点id

		// 排序

		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);

		// 分页
		Integer pageNo = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				10);

		c.x.platform.admin.feng.pss.gen3.pss_product_info.service.Gen3PssProductInfoService service = new c.x.platform.admin.feng.pss.gen3.pss_product_info.service.Gen3PssProductInfoService();
		PageCoreBean<Map<String, Object>> pageBean = null;
		if (StringUtil.isNotBlank(first$tree$id)) {

			pageBean = service.query(first$tree$id, sortOrderName, sortFieldName, pageNo, pageSize);
		} else {

			pageBean = service.find(sortOrderName, sortFieldName, pageNo, pageSize);

		}

		List<Map<String, Object>> listMap = pageBean.getList();

		request.setAttribute("cPage", pageBean);
		request.setAttribute("list", listMap);

		return "index";
	}
}
