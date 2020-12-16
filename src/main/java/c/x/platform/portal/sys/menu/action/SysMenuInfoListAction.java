package c.x.platform.portal.sys.menu.action;
import java.util.List;

import c.a.config.SysConfig;
import c.a.tools.crud.action.TransactionAction;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonTcpBean;
import all.gen.sys_menu.t.entity.SysMenuT;
import c.x.platform.portal.sys.menu.service.SysMenuInfoService;
public class SysMenuInfoListAction extends TransactionAction {
	public SysMenuInfoListAction() {
		this.database = true;
		transaction = true;
	}
	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		String returnStr = SysConfig.configValueTrue;
		return this.returnJsonTcpBean(returnStr);
	}
	@Override
	public String execute() throws Exception {
		// 排序
		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);
		// 搜索
		String menu_name = request.getParameter("menu_name");
		request.setAttribute("value_menu_name", menu_name);
		// 分页
		Integer pageNo = null;
		Integer pageSize = null;
		String show_all = (String) request.getParameter(SysConfig.name_show_all);
		if ("show_all_true".equals(show_all)) {
			// 显示全部
			pageNo = new Integer("1");
			pageSize = new Integer("-1");
			// js函数名(显示全部)
			request.setAttribute(SysConfig.value_js_cmd_show_all, SysConfig.ay_page_query_all_not);
			request.setAttribute(SysConfig.value_text_span_show_all, SysConfig.text_span_show_all_not);
		} else {
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
			// js函数名(不显示全部)
			request.setAttribute(SysConfig.value_js_cmd_show_all, SysConfig.ay_page_query_all);
			request.setAttribute(SysConfig.value_text_span_show_all, SysConfig.text_span_show_all);
		}
		SysMenuInfoService service = new SysMenuInfoService();
		// List<HashMap<String, Object>> listMap =service.query();
		// PageCoreBean pageBean = service.page(pageNo, pageSize);
		PageCoreBean<SysMenuT> pageCoreBean = service.find(sortOrderName, sortFieldName, menu_name, pageNo,
				pageSize);
		List<SysMenuT> listMap = pageCoreBean.getList();
		request.setAttribute("cPage", pageCoreBean);
		request.setAttribute("list", listMap);
		request.setAttribute(SysConfig.value_show_all, show_all);
		return "index";
	}
}
