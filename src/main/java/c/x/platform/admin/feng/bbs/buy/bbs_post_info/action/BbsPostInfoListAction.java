package c.x.platform.admin.feng.bbs.buy.bbs_post_info.action;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import c.x.platform.root.common.action.BaseAction;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.string.StringUtil;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;

public class BbsPostInfoListAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		// 树节点id
		// {
		// first$tree$id
		String first$tree$id = (String) request.getParameter("first$tree$id");

		// BaseLog.trace("first$tree$id="+first$tree$id);
		if (false) {

			String bbs_forum_info$parent = request
					.getParameter("bbs_forum_info.parent");

			// BaseLog.trace("bbs_forum_info$parent="+bbs_forum_info$parent);
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
		String sortOrderName = request
				.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);

		// 分页
		Integer pageNo = null;
		Integer pageSize = null;

		// 不显示全部
		String pageSizeStr = (String) request
				.getParameter(SysConfig.pageSizeName);

		String pageIndexStr = (String) request
				.getParameter(SysConfig.pageIndexName);

		if (pageIndexStr != null && pageSizeStr != null) {

			pageNo = new Integer(pageIndexStr);
			pageSize = new Integer(pageSizeStr);

		} else {
			pageNo = new Integer("1");
			pageSize = new Integer("10");

		}

		c.x.platform.admin.feng.bbs.buy.bbs_post_info.service.BbsPostInfoService service = new c.x.platform.admin.feng.bbs.buy.bbs_post_info.service.BbsPostInfoService();
		PageCoreBean<Map<String, Object>> pageBean = null;
		if (StringUtil.isNotBlank(first$tree$id)) {

			pageBean = service.query(first$tree$id,sortOrderName,sortFieldName,
					pageNo, pageSize);
		} else {

			pageBean = service.find(sortOrderName, sortFieldName, pageNo, pageSize);

		}

		List<Map<String, Object>> listMap = pageBean.getList();

		request.setAttribute("cPage", pageBean);
		request.setAttribute("list", listMap);

		return "index";
	}
}
