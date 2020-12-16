package all.sys_admin.sys.sys_account.t.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.sys_admin.sys.sys_user.t.service.SysUserService;
import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.CommAction;
public class SysUserJqgridListJsonAction extends CommAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		SysUserService service = new SysUserService();
		int pageIndex = Integer.parseInt(request.getParameter("page")); // 取得当前页数
		int pageSize = Integer.parseInt(request.getParameter("rows")); // 取得每页显示行数
		//System.out.println("1 pageIndex=" + pageIndex);
		//System.out.println("1 pageSize=" + pageSize);
		// 排序
		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);
		PageCoreBean<Map<String, Object>> page = service.find(this.findCurrentSysUser(), sortOrderName, sortFieldName,
				pageIndex, pageSize);
		List<Map<String, Object>> mapList = page.getList();
		// for(Map<String, Object> map:mapList){
		// map.put("id", map.get("SYS_USER_ID_"));
		// }
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("page", page.getPageIndex());
		jsonMap.put("rows", mapList);
		jsonMap.put("records", mapList.size());
		jsonMap.put("total", page.getTotalPages());
		return returnJson(jsonMap);
	}
}
