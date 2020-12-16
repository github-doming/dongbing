package all.sys.ay.fun_type_str.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import all.gen.fun_type_str.t.service.FunTypeStrTService;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.CommAction;
public class FunTypeStrSysListJsonAction extends CommAction {
	public FunTypeStrSysListJsonAction() {
	}
	@Override
	public String execute() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 分页
		String sortField = BeanThreadLocal.findThreadLocal().get().find(request, "sortField", "");
		String sortOrder = BeanThreadLocal.findThreadLocal().get().find(request, "sortOrder", "");
		request.setAttribute("sortField", sortField);
		request.setAttribute("sortOrder", sortOrder);
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(request, "pageIndex", 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request, "pageSize", 10);
		List<Object> parameterList = new ArrayList<Object>();
		FunTypeStrTService service = new FunTypeStrTService();
		PageCoreBean<Map<String, Object>> basePage = service.find(sortField, sortOrder, pageIndex, pageSize);
		List<Map<String, Object>> listMap = basePage.getList();
		map.put("data", listMap);
		map.put("total", basePage.getTotalCount());
		return this.returnJson(map);
	}
	/**
	 * 构造sql
	 * 
	 */
	public String toSql() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT * from    ");
		sb.append("  fun_type_str ");
		sb.append(" where path_ like '1.%' and ");
		sb.append("  (TREE_CODE_ is null or TREE_CODE_!='0001')  ");
		sb.append(" 	order by SN_ asc,ID asc  ");
		String sql = sb.toString();
		return sql;
	}
}
