package c.x.platform.admin.feng.upload.sys_lob_info.action;
import java.util.List;

import c.x.platform.admin.feng.upload.sys_lob_info.entity.SysLobInfo;
import c.x.platform.admin.feng.upload.sys_lob_info.service.SysLobInfoService_listObject;
import c.x.platform.root.common.action.BaseAction;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
public class SysLobInfoListObjectAction extends BaseAction {
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
		SysLobInfoService_listObject service = new SysLobInfoService_listObject();
		PageCoreBean<SysLobInfo> pageBean = service.find(sortOrderName, sortFieldName, pageNo, pageSize);
		List<SysLobInfo> listMap = pageBean.getList();
		request.setAttribute("cPage", pageBean);
		request.setAttribute("list", listMap);
		return "index";
	}
}
