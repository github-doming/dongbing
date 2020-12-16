package all.archit.complex.spring_kaida.admin.fun.user_info.action;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.BaseRoleAction;
import all.archit.complex.spring_kaida.admin.fun.user_info.service.FunUserInfoService;
public class FunUserInfoActionList extends BaseRoleAction {
	public FunUserInfoActionList() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
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
		FunUserInfoService service = new FunUserInfoService();
		PageCoreBean<Map<String, Object>> pageBean = service.find(sortOrderName,
				sortFieldName, pageNo, pageSize);
		List<Map<String, Object>> listMap = pageBean.getList();
		request.setAttribute("cPage", pageBean);
		request.setAttribute("list", listMap);
		return "index";
	}
}
