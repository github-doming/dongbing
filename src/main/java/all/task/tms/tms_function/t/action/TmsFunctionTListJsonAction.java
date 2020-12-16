package all.task.tms.tms_function.t.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import c.x.platform.root.common.action.BaseAction;
import java.sql.Connection;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonThreadLocal;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import all.task.tms.tms_function.t.entity.TmsFunctionT;
public class TmsFunctionTListJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String sql = "SELECT * FROM tms_function where state_!='DEL' order by TMS_FUNCTION_ID_ desc";
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		List<TmsFunctionT> menuList = jdbcTool.findObjectList(TmsFunctionT.class, conn, sql, null);
		List<TmsFunctionT> treeEasyuiMenuBeanList = tree(menuList, "0");
		String jsonStr = JsonThreadLocal.findThreadLocal().get().list2json(treeEasyuiMenuBeanList);
		response.getWriter().print(jsonStr);
		return null;
	}
	public List<TmsFunctionT> tree(List<TmsFunctionT> easyuiMenuBeanList, String id) {
		// 递归转化为树形
		List<TmsFunctionT> treeEasyuiMenuBeanList = new ArrayList<TmsFunctionT>();
		for (TmsFunctionT easyuiMenuBean : easyuiMenuBeanList) {
			TmsFunctionT treeEasyuiMenuBean = new TmsFunctionT();
			treeEasyuiMenuBean.setTmsFunctionId(easyuiMenuBean.getTmsFunctionId());
			treeEasyuiMenuBean.setParent(easyuiMenuBean.getParent());
			//treeEasyuiMenuBean.setName(easyuiMenuBean.getName());
			//treeEasyuiMenuBean.setUrl(easyuiMenuBean.getUrl());
			treeEasyuiMenuBean.setState(easyuiMenuBean.getState());
			if (id.equals(easyuiMenuBean.getParent())) {
				treeEasyuiMenuBean.setChildren(tree(easyuiMenuBeanList, treeEasyuiMenuBean.getTmsFunctionId()));
				treeEasyuiMenuBeanList.add(treeEasyuiMenuBean);
			}
		}
		return treeEasyuiMenuBeanList;
	}
	public String execute2() throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		List<Map<String, Object>> mapList = this.findDao().findMapList(
				this.toSql(), parameterList);
		// 重新构造key
		for (Map<String, Object> map : mapList) {
			map.put("sysId", map.get("TMS_FUNCTION_ID_"));
			map.put("name", map.get("NAME_"));
			map.put("parent", map.get("PARENT_"));
			map.put("url", map.get("URL_"));
		}
		return this.returnJson(mapList);
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
		sb.append(" tms_function ");
		sb.append(" where path_ like '1.%' and ");
		sb.append("  (TREE_CODE_ is null or TREE_CODE_!='0001')  ");
		sb.append(" 	order by SN_ asc,TMS_FUNCTION_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
}
