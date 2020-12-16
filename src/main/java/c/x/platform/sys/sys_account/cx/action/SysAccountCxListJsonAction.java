package c.x.platform.sys.sys_account.cx.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.sys.sys_account.cx.service.SysAccountCxService;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountCxListJsonAction extends BaseAction {
	public SysAccountCxListJsonAction() {
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 分页
	
		String sortField=BeanThreadLocal.findThreadLocal().get().find(request,"sortField", "");
		String sortOrder=BeanThreadLocal.findThreadLocal().get().find(request,"sortOrder", "");
		request.setAttribute("sortField", sortField);
		request.setAttribute("sortOrder",sortOrder);
		
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(request,"pageIndex", 1)+1;
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request,"pageSize", 10);

		List<Object> parameterList = new ArrayList<Object>();
		SysAccountCxService service = new SysAccountCxService();
		PageCoreBean<Map<String, Object>> basePage = service.find(sortField,sortOrder,
				pageIndex, pageSize);

		List<Map<String, Object>> listMap = basePage.getList();
		map.put("data", listMap);
		map.put("total", basePage.getTotalCount());
		return this.returnJson(map);
	}
	/**
	 * 构造sql
	 * 
	 * @Description
	 * @Title toSql
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String toSql() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT * from    ");
		sb.append("  sys_account ");
		sb.append(" where path_ like '1.%' and ");
		sb.append("  (TREE_CODE_ is null or TREE_CODE_!='0001')  ");
		sb.append(" 	order by SN_ asc,SYS_ACCOUNT_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
}
