package all.app_admin.root.layout.menu.action;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
public class AppMenuListJsonAction extends BaseAction {
	public AppMenuListJsonAction() {
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		AyDao dao = new AyDao();
		List<Object> parameterList = new ArrayList<Object>();
		String parentId = BeanThreadLocal.findThreadLocal().get().find(request.getParameter("pid"), "");
		// 查找一级菜单
		if (StringUtil.isNotBlank(parentId)) {
			String path = "1." + parentId + ".%";
			parameterList.add(path);
			parameterList.add(parentId);
			mapList = dao.findMapList(this.toSqlRoleShow(parentId),
					parameterList);

		}
		for (Map<String, Object> map : mapList) {
			map.put("id", map.get("APP_MENU_ID_"));
			map.put("text", map.get("APP_MENU_NAME_"));
			map.put("pid", map.get("PARENT_"));
			map.put("url", map.get("URL_"));
		}
		String jsonStr = JsonThreadLocal.findThreadLocal().get().list2json(mapList);
		response.getWriter().print(jsonStr);
		return null;
	}

	/**
	 * 通过父id查询 菜单sql; 有角色; 通过角色过滤菜单; 是否显示;
	 * 
	 * @Description
	 * @Title toSqlRoleShow
	 * @param parentId
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String toSqlRoleShow(String parentId) throws Exception {
		String userId = this.findCurrentUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" APP_MENU m");
		sb.append(" left join  APP_MENU_GROUP rm   ");
		sb.append(" on m. APP_MENU_ID_=rm.APP_MENU_ID_    ");
		sb.append(" left join  APP_USER_GROUP ru   ");
		sb.append("  on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_ ");
		sb.append("  where path_ like ? and m.STATE_='OPEN' and ru.APP_USER_ID_='"
				+ userId);
		sb.append("'  and  m.APP_MENU_ID_!=? ");
		sb.append(" 	order by m.SN_ asc,m.APP_MENU_ID_ asc  ");
		String sql = sb.toString().toUpperCase();
		return sql;
	}
	public String findRootMenuId() throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		String sql = "select APP_MENU_ID_,PARENT_ FROM APP_MENU  where tree_code_=? ";
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		parameterList.add("0001");
		String menuId = jdbcUtil.findString("APP_MENU_ID_", conn, sql, parameterList);
		return menuId;
	}
}
