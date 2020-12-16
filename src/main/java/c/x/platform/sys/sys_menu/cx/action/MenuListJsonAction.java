package c.x.platform.sys.sys_menu.cx.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import c.x.platform.root.common.action.BaseAction;
public class MenuListJsonAction extends BaseAction {
//public class MenuListJsonAction extends CommAction {
	public MenuListJsonAction() {
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		List<Map<String, Object>> mapList = this.findDao().findMapList(this.toSql(), parameterList);
		// 重新构造key
		for (Map<String, Object> map : mapList) {
			map.put("sysMenuId", map.get("SYS_MENU_ID_"));
			map.put("name", map.get("NAME_"));
			map.put("parent", map.get("PARENT_"));
			map.put("url", map.get("URL_"));
		}
		return this.returnJson(mapList);
	}
	/**
	 * 构造sql
	 * 
	 * @Description @Title toSql @return 参数说明 @return String 返回类型 @throws
	 */
	public String toSql() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" sys_menu m");
		sb.append(" where m.path_ like '1.%' and ");
		sb.append("  (m.TREE_CODE_ is null or m.TREE_CODE_!='0001')  ");
		sb.append(" 	order by m.SN_ asc,m.SYS_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
}
