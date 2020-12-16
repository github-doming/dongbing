package all.sys_admin.sys.sys_dict_tree.t.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import c.x.platform.root.common.action.BaseAction;
import java.sql.Connection;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonThreadLocal;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import all.sys_admin.sys.sys_dict_tree.t.entity.SysDictTreeT;
public class SysDictTreeTListJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String sql = "SELECT * FROM sys_dict_tree where state_!='DEL' order by SYS_DICT_TREE_ID_ desc";
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		List<SysDictTreeT> menuList = jdbcTool.findObjectList(SysDictTreeT.class, conn, sql, null);
		List<SysDictTreeT> treeEasyuiMenuBeanList = tree(menuList, "0");
		String jsonStr = JsonThreadLocal.findThreadLocal().get().list2json(treeEasyuiMenuBeanList);
		response.getWriter().print(jsonStr);
		return null;
	}
	public List<SysDictTreeT> tree(List<SysDictTreeT> easyuiMenuBeanList, String id) {
		// 递归转化为树形
		List<SysDictTreeT> treeEasyuiMenuBeanList = new ArrayList<SysDictTreeT>();
		for (SysDictTreeT easyuiMenuBean : easyuiMenuBeanList) {
			SysDictTreeT treeEasyuiMenuBean = new SysDictTreeT();
			treeEasyuiMenuBean.setSysDictTreeId(easyuiMenuBean.getSysDictTreeId());
			treeEasyuiMenuBean.setParent(easyuiMenuBean.getParent());
			treeEasyuiMenuBean.setSysDictTreeName(easyuiMenuBean.getSysDictTreeName());
			//treeEasyuiMenuBean.setUrl(easyuiMenuBean.getUrl());
			treeEasyuiMenuBean.setState(easyuiMenuBean.getState());
			if (id.equals(easyuiMenuBean.getParent())) {
				treeEasyuiMenuBean.setChildren(tree(easyuiMenuBeanList, treeEasyuiMenuBean.getSysDictTreeId()));
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
			map.put("sysId", map.get("SYS_DICT_TREE_ID_"));
			map.put("name", map.get("SYS_DICT_TREE_NAME_"));
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
		sb.append(" sys_dict_tree ");
		sb.append(" where path_ like '1.%' and ");
		sb.append("  (TREE_CODE_ is null or TREE_CODE_!='0001')  ");
		sb.append(" 	order by SN_ asc,SYS_DICT_TREE_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
}
