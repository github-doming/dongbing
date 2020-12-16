package all.app_admin.root.layout.main.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
import c.x.platform.root.layout.main.action.SysMainAction;
public class AppMainAction extends SysMainAction {
	public AppMainAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		String type = this.findCurrentUserType();
		if ("sys".equals(type)) {
			return this.executeSys();
		}
		if ("app".equals(type)) {
			return this.executeByApp();
		}
		return null;
	}
	public String executeByApp() throws Exception {
		if (false) {
			log.trace("经过 MainAction");
		}
		// 1级菜单
		List<Map<String, Object>> menuFirstMapList = this.findAppMenuFirst_RoleShowList_ByApp();
		this.request.setAttribute("menuFirstMapList", menuFirstMapList);
		// 1级菜单的第1个节点的id
		String menu_id_00010001 = this.findAppMenuChild_ID_ByApp(menuFirstMapList);
		this.request.setAttribute("menu_id_00010001", menu_id_00010001);
		// 当前用户名称
		String current_user_name = this.findCurrentUserName();
		request.setAttribute("current_user_name", current_user_name);
		// 测试单例
		// log.trace(" 测试单例Singleton.findInstance().getId()="
		// + Singleton.findInstance().getId());
		return CommViewEnum.Default.toString();
		// return findViewResult();
	}
	/**
	 * 1级菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAppMenuFirstListByApp() throws Exception {
		List<Map<String, Object>> mapList = null;
		AyDao dao = new AyDao();
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append("select  t1.APP_MENU_ID_ as id ,APP_MENU_NAME_  FROM APP_MENU  t1,");
		stringBuffer.append("(");
		stringBuffer.append("select APP_MENU_ID_ FROM APP_MENU  where tree_code=0001");
		stringBuffer.append(") as t2 ");
		stringBuffer.append("where  t1.PARENT_=t2.APP_MENU_ID_ order by t1.SN_ asc,t1.ID_ asc");
		String sql = stringBuffer.toString();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		mapList = dao.findMapList(sql, parameterList);
		return mapList;
	}
	/**
	 * 1级菜单
	 * 
	 * 带角色;
	 * 
	 * 通过角色过滤菜单;
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findMenuFirst_RoleList_ByApp() throws Exception {
		String userId = this.findCurrentUserId();
		AyDao dao = new AyDao();
		List<Map<String, Object>> mapList = null;
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append(" select DISTINCT m.APP_MENU_ID_ ,m.APP_MENU_NAME_,SN_ from  ");
		stringBuffer.append(" (");
		stringBuffer.append(" select  t1.APP_MENU_ID_ as id ,name,SN_ FROM APP_MENU  t1,");
		stringBuffer.append(" (select APP_MENU_ID_ FROM APP_MENU  where TREE_CODE_=0001) as t2  ");
		stringBuffer.append(" where  t1.PARENT_=t2.APP_MENU_ID_  ");
		stringBuffer.append(" ) as  m ");
		stringBuffer.append(" left join  APP_MENU_GROUP rm ");
		stringBuffer.append(" on m.APP_MENU_ID_=rm.APP_MENU_ID_ ");
		stringBuffer.append(" left join  APP_USER_GROUP ru  ");
		stringBuffer.append(" on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_ ");
		stringBuffer.append(" where ru.APP_USER_ID_=" + userId);
		stringBuffer.append(" order by SN_ asc,APP_MENU_ID_ asc ");
		String sql = stringBuffer.toString();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		mapList = dao.findMapList(sql, parameterList);
		return mapList;
	}
	/**
	 * 1级菜单
	 * 
	 * 带角色;
	 * 
	 * 通过角色过滤菜单;
	 * 
	 * 
	 * 是否显示;
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAppMenuFirst_RoleShowList_ByApp() throws Exception {
		AyDao dao = new AyDao();
		String userId = this.findCurrentUserId();
		List<Map<String, Object>> mapList = null;
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append(" select DISTINCT m.APP_MENU_ID_  ,m.APP_MENU_NAME_,m.SN_ from   ");
		stringBuffer.append(" (   ");
		stringBuffer.append(" select  t1.APP_MENU_ID_  as APP_MENU_ID_  ,APP_MENU_NAME_,SN_ FROM APP_MENU  t1   ");
		stringBuffer.append(" where t1.STATE_!=? and t1.PARENT_= ");
		stringBuffer.append(" (select APP_MENU_ID_ FROM APP_MENU  where TREE_CODE_=0001 )    ");
		stringBuffer.append(" )  m ");
		stringBuffer.append(" left join  APP_MENU_GROUP rm ");
		stringBuffer.append(" on m.APP_MENU_ID_ =rm.APP_MENU_ID_  ");
		stringBuffer.append(" left join  APP_USER_GROUP ru  ");
		stringBuffer.append(" on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_ ");
		stringBuffer.append(" where   ru.APP_USER_ID_=? ");
		stringBuffer.append(" order by SN_ asc,APP_MENU_ID_ asc ");
		String sql = stringBuffer.toString().toUpperCase();
		// log.trace("sql=" + sql);
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(TaskStateEnum.DEL.toString());
		parameterList.add(userId);
		mapList = dao.findMapList(sql, parameterList);
		return mapList;
	}
	/**
	 * 下一级菜单;
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAppMenuChildListByApp(String parent_id) throws Exception {
		List<Map<String, Object>> mapList = null;
		AyDao dao = new AyDao();
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append("select * FROM APP_MENU  t1  ");
		stringBuffer.append(" where  t1.PARENT_=");
		stringBuffer.append(parent_id);
		stringBuffer.append(" order by t1.SN_ asc,t1.APP_MENU_ID_  asc");
		String sql = stringBuffer.toString();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		mapList = dao.findMapList(sql, parameterList);
		return mapList;
	}
	/**
	 * 下一级菜单;
	 * 
	 * 带角色;
	 * 
	 * 通过角色过滤菜单;
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAppMenuChild_RoleList_ByApp(String parentId) throws Exception {
		String userId = this.findCurrentUserId();
		AyDao dao = new AyDao();
		List<Map<String, Object>> mapList = null;
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append("select * from APP_MENU m  ");
		stringBuffer.append(" left join  APP_MENU_GROUP rm    on m.APP_MENU_ID_=rm.APP_MENU_ID_    ");
		stringBuffer.append(" left join  APP_USER_GROUP ru    on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_   ");
		stringBuffer.append("where ru.APP_USER_ID_=" + userId);
		stringBuffer.append(" and m.PARENT_=" + parentId);
		stringBuffer.append("order by m.SN_ asc,m.APP_MENU_ID_ asc    ");
		String sql = stringBuffer.toString();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		mapList = dao.findMapList(sql, parameterList);
		return mapList;
	}
	/**
	 * 下一级菜单;
	 * 
	 * 带角色;
	 * 
	 * 通过角色过滤菜单;
	 * 
	 * 是否显示;
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAppMenuChild_RoleShowList_ByApp(String parentId) throws Exception {
		String userId = this.findCurrentUserId();
		AyDao dao = new AyDao();
		List<Map<String, Object>> listMap = null;
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append("select * from APP_MENU m  ");
		stringBuffer.append(" left join  APP_MENU_GROUP rm    on m.APP_MENU_ID_=rm.APP_MENU_ID_    ");
		stringBuffer.append(" left join  APP_USER_GROUP ru    on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_   ");
		stringBuffer.append("where ru.APP_USER_ID_='" + userId + "'");
		stringBuffer.append(" and m.STATE_!='" + TaskStateEnum.DEL.toString() + "' and m.PARENT_='" + parentId + "'");
		stringBuffer.append(" order by m.SN_ asc,m.APP_MENU_ID_ asc    ");
		String sql = stringBuffer.toString();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		listMap = dao.findMapList(sql, parameterList);
		return listMap;
	}
	/**
	 * 
	 * 找出下一级菜单的第一个节点的id;
	 * 
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findAppMenuChild_ID_ByApp(List<Map<String, Object>> mapList) throws Exception {
		for (Map<String, Object> map : mapList) {
			String parentIdString = (String) map.get("APP_MENU_ID_");
			String parentId = parentIdString.toString();
			return parentId;
		}
		return null;
	}
	/**
	 * 递归;
	 * 
	 * 
	 * 找出下一级菜单中没有孩子的节点;
	 * 
	 * 
	 * @param parentId
	 *            1级菜单id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findNodeNotChildByApp(String parentId) throws Exception {
		Map<String, Object> returnMap = null;
		// 2级菜单
		List<Map<String, Object>> childMapList = this.findAppMenuChild_RoleShowList_ByApp(parentId);
		for (Map<String, Object> childMap : childMapList) {
			// 2级菜单id
			String childIdStr = (String) childMap.get("APP_MENU_ID_");
			// 2级菜单id
			String childId = childIdStr.toString();
			// 3级菜单
			List<Map<String, Object>> menuThirdList = this.findAppMenuChild_RoleShowList_ByApp(childId);
			if (menuThirdList.size() == 0) {
				return childMap;
			}
			returnMap = this.findNodeNotChildByApp(childId);
			if (returnMap != null) {
				return returnMap;
			}
		}
		return null;
	}
	/**
	 * 
	 * 递归;
	 * 
	 * 找出下一级菜单中没有孩子的节点;
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findNodeNotChildByApp_v3(String parentId) throws Exception {
		List<Map<String, Object>> mapList = this.findAppMenuChildListByApp(parentId);
		return this.findNodeNotChildByApp_v2(mapList);
	}
	/**
	 * 递归;
	 * 
	 * 找出下一级菜单中没有孩子的节点;
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param mapList
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findNodeNotChildByApp_v2(List<Map<String, Object>> mapList) throws Exception {
		/**
		 * 找出下一级菜单中没有孩子的节点;
		 * 
		 * 找出 2级菜单中（没有孩子的节点）;
		 */
		for (Map<String, Object> map : mapList) {
			// 2级菜单id
			Integer childIdInt = (Integer) map.get("APP_MENU_ID_");
			// 2级菜单id
			String childIdStr = childIdInt.toString();
			// 3级菜单
			List<Map<String, Object>> menuThirdMapList = this.findAppMenuChildListByApp(childIdStr);
			if (menuThirdMapList.size() == 0) {
				return map;
			} else {
				// 3级菜单id
				Map<String, Object> menuThirdId = this.findNodeNotChildByApp_v2(menuThirdMapList);
				if (menuThirdId != null) {
					return menuThirdId;
				}
			}
		}
		return null;
	}
	/**
	 * 找出 2级菜单中（没有孩子的节点）;
	 * 
	 * 
	 * 找出3级菜单中（没有孩子的节点）;
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param mapList
	 * @return
	 * @throws Exception
	 */
	public String findNodeNotChildByApp_v1(List<Map<String, Object>> mapList) throws Exception {
		/**
		 * 找出 2级菜单中（没有孩子的节点）
		 */
		for (Map<String, Object> map : mapList) {
			// 2级菜单id
			Integer menuChildIdInt = (Integer) map.get("APP_MENU_ID_");
			// 2级菜单id
			String menuChildIdStr = menuChildIdInt.toString();
			// 3级菜单
			List<Map<String, Object>> menuThirdMapList = this.findAppMenuChildListByApp(menuChildIdStr);
			if (menuThirdMapList.size() == 0) {
				// 返回2级菜单id
				return menuChildIdStr;
			} else {
				continue;
			}
		}
		/**
		 * 找出3级菜单中（没有孩子的节点）
		 */
		for (Map<String, Object> map : mapList) {
			// 2级菜单id
			Integer menuChildIdInt = (Integer) map.get("APP_MENU_ID_");
			// 2级菜单id
			String menuChildIdStr = menuChildIdInt.toString();
			// 3级菜单
			List<Map<String, Object>> menuThirdMapList = this.findAppMenuChildListByApp(menuChildIdStr);
			for (Map<String, Object> menuThirdMap : menuThirdMapList) {
				// 3级菜单id
				Integer menuThirdIdInt = (Integer) menuThirdMap.get("APP_MENU_ID_");
				String menuThirdIdStr = menuThirdIdInt.toString();
				// 4级菜单
				List<Map<String, Object>> menuFourthMapList = this.findAppMenuChildListByApp(menuThirdIdStr);
				if (menuFourthMapList.size() == 0) {
					// 返回3级菜单id
					return menuThirdIdStr;
				} else {
					continue;
				}
			}
		}
		return null;
	}
}
