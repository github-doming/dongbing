package c.x.platform.admin.fav.sys.sys_area_info.action;

import java.util.List;

import c.x.platform.admin.fav.sys.sys_area_info.entity.FunAreaInfo;
import c.x.platform.admin.fav.sys.sys_area_info.service.FunAreaInfoService;
import c.x.platform.root.common.action.BaseRoleAction;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;

public class FunAreaInfoListAction extends BaseRoleAction {
	@Override
	public String execute() throws Exception {
		// 排序
		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);
		// 分页
		Integer pageNo = null;
		Integer pageSize = null;
		// 不显示全部
		String pageSizeStr = (String) request.getParameter(SysConfig.pageSizeName);
		String pageIndexStr = (String) request.getParameter(SysConfig.pageIndexName);
		if (pageIndexStr != null && pageSizeStr != null) {
			pageNo = new Integer(pageIndexStr);
			pageSize = new Integer(pageSizeStr);
		} else {
			pageNo = new Integer("1");
			pageSize = new Integer("10");
		}
		FunAreaInfoService service = new FunAreaInfoService();
		PageCoreBean<FunAreaInfo> pageCoreBean = service.find(sortOrderName, sortFieldName, pageNo, pageSize);
		List<FunAreaInfo> listMap = pageCoreBean.getList();
		request.setAttribute("cPage", pageCoreBean);
		request.setAttribute("list", listMap);
		return "index";
	}
}
