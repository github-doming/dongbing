package c.x.platform.root.layout.menu.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
public class SysMenuListJsonAction extends BaseAction {
	public SysMenuListJsonAction() {
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
			map.put("id", map.get("SYS_MENU_ID_"));
			map.put("text", map.get("SYS_MENU_NAME_"));
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
		sb.append(" SYS_MENU m");
		sb.append(" left join  SYS_GROUP_MENU rm   ");
		sb.append(" on m. SYS_MENU_ID_=rm.SYS_MENU_ID_    ");
		sb.append(" left join  SYS_GROUP_USER ru   ");
		sb.append("  on ru.SYS_GROUP_ID_=rm.SYS_GROUP_ID_ ");
		sb.append("  where path_ like ? and m.STATE_='OPEN' and ru.SYS_USER_ID_="
				+ userId);
		sb.append("  and  m.SYS_MENU_ID_!=? ");
		sb.append(" 	order by m.SN_ asc,m.SYS_MENU_ID_ asc  ");
		String sql = sb.toString().toUpperCase();
		return sql;
	}
	
}
