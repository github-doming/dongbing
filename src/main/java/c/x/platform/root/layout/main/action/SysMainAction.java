package c.x.platform.root.layout.main.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.util.StringUtil;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.x.platform.root.common.action.BaseAction;
import c.x.platform.root.compo.jdbc_dao.common.AyDao;
public class SysMainAction extends BaseAction {
	public SysMainAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		return this.executeSys();
	}
	public String executeSys() throws Exception {
		if (false) {
			log.trace("经过 MainAction");
		}
		// 请求日志
		this.saveSysLogRequest();
		// 1级菜单
		List<Map<String, Object>> menuFirstMapList = this.findSysMenuFirst_RoleShowList();
		this.request.setAttribute("menuFirstMapList", menuFirstMapList);
		// 1级菜单的第1个节点的id
		String menu_id_00010001 = this.findSysMenuChildId(menuFirstMapList);
		this.request.setAttribute("menu_id_00010001", menu_id_00010001);
		// 当前用户名称
		String current_user_name = this.findCurrentSysUser().getSysUserName();
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
	public List<Map<String, Object>> findSysMenuFirstList() throws Exception {
		List<Map<String, Object>> mapList = null;
		AyDao dao = new AyDao();
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append("select  t1.SYS_MENU_ID_ as id ,SYS_MENU_NAME_  FROM SYS_MENU  t1,");
		stringBuffer.append("(");
		stringBuffer.append("select SYS_MENU_ID_ FROM SYS_MENU  where tree_code_=0001");
		stringBuffer.append(") as t2 ");
		stringBuffer.append("where  t1.PARENT_=t2.SYS_MENU_ID_ order by t1.SN_ asc,t1.ID_ asc");
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
	public List<Map<String, Object>> findSysMenuFirst_RoleList() throws Exception {
		String userId = this.findCurrentUserId();
		AyDao dao = new AyDao();
		List<Map<String, Object>> mapList = null;
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append(" select DISTINCT m.SYS_MENU_ID_ ,m.SYS_MENU_NAME_,SN_ from  ");
		stringBuffer.append(" (");
		stringBuffer.append(" select  t1.SYS_MENU_ID_ as id ,name,SN_ FROM SYS_MENU  t1,");
		stringBuffer.append(" (select SYS_MENU_ID_ FROM SYS_MENU  where TREE_CODE_=0001) as t2  ");
		stringBuffer.append(" where  t1.PARENT_=t2.SYS_MENU_ID_  ");
		stringBuffer.append(" ) as  m ");
		stringBuffer.append(" left join  SYS_GROUP_MENU rm ");
		stringBuffer.append(" on m.SYS_MENU_ID_=rm.SYS_MENU_ID_ ");
		stringBuffer.append(" left join  SYS_GROUP_USER ru  ");
		stringBuffer.append(" on ru.SYS_GROUP_ID_=rm.SYS_GROUP_ID_ ");
		stringBuffer.append(" where ru.SYS_USER_ID_=" + userId);
		stringBuffer.append(" order by SN_ asc,SYS_MENU_ID_ asc ");
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
	public List<Map<String, Object>> findSysMenuFirst_RoleShowList() throws Exception {
		List<Map<String, Object>> mapList = null;
		AyDao dao = new AyDao();
		String userId = this.findCurrentUserId();
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append(" select DISTINCT m.SYS_MENU_ID_  ,m.SYS_MENU_NAME_,m.SN_ from   ");
		stringBuffer.append(" (   ");
		stringBuffer.append(" select  t1.SYS_MENU_ID_  as SYS_MENU_ID_  ,SYS_MENU_NAME_,SN_ FROM SYS_MENU  t1   ");
		stringBuffer.append(" where t1.STATE_!=? and t1.PARENT_= ");
		stringBuffer.append(" (select SYS_MENU_ID_ FROM SYS_MENU  where TREE_CODE_=0001 )    ");
		stringBuffer.append(" )  m ");
		stringBuffer.append(" left join  SYS_GROUP_MENU rm ");
		stringBuffer.append(" on m.SYS_MENU_ID_ =rm.SYS_MENU_ID_  ");
		stringBuffer.append(" left join  SYS_GROUP_USER ru  ");
		stringBuffer.append(" on ru.SYS_GROUP_ID_=rm.SYS_GROUP_ID_ ");
		stringBuffer.append(" where   ru.SYS_USER_ID_=?");
		stringBuffer.append(" order by SN_ asc,SYS_MENU_ID_ asc ");
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
	public List<Map<String, Object>> findSysMenuChildList(String parent_id) throws Exception {
		AyDao dao = new AyDao();
		List<Map<String, Object>> mapList = null;
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append("select * FROM SYS_MENU  t1  ");
		stringBuffer.append(" where  t1.PARENT_=");
		stringBuffer.append(parent_id);
		stringBuffer.append(" order by t1.SN_ asc,t1.SYS_MENU_ID_  asc");
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
	public List<Map<String, Object>> findSysMenuChild_RoleList(String parentId) throws Exception {
		AyDao dao = new AyDao();
		String userId = this.findCurrentUserId();
		List<Map<String, Object>> mapList = null;
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append("select * from SYS_MENU m  ");
		stringBuffer.append(" left join  SYS_GROUP_MENU rm    on m.SYS_MENU_ID_=rm.SYS_MENU_ID_    ");
		stringBuffer.append(" left join  SYS_GROUP_USER ru    on ru.SYS_GROUP_ID_=rm.SYS_GROUP_ID_   ");
		stringBuffer.append("where ru.SYS_USER_ID_=" + userId);
		stringBuffer.append(" and m.PARENT_=" + parentId);
		stringBuffer.append("order by m.SN_ asc,m.SYS_MENU_ID_ asc    ");
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
	public List<Map<String, Object>> findSysMenuChild_RoleShowList(String parentId) throws Exception {
		String userId = this.findCurrentUserId();
		AyDao dao = new AyDao();
		List<Map<String, Object>> listMap = null;
		StringBuilder stringBuffer = new StringBuilder();
		stringBuffer.append("select * from SYS_MENU m  ");
		stringBuffer.append(" left join  SYS_GROUP_MENU rm    on m.SYS_MENU_ID_=rm.SYS_MENU_ID_    ");
		stringBuffer.append(" left join  SYS_GROUP_USER ru    on ru.SYS_GROUP_ID_=rm.SYS_GROUP_ID_   ");
		stringBuffer.append("where ru.SYS_USER_ID_='" + userId + "'");
		stringBuffer.append(" and m.STATE_!='" + TaskStateEnum.DEL.toString() + "' and m.PARENT_='" + parentId + "'");
		stringBuffer.append(" order by m.SN_ asc,m.SYS_MENU_ID_ asc    ");
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
	public String findSysMenuChildId(List<Map<String, Object>> mapList) throws Exception {
		for (Map<String, Object> map : mapList) {
			String parentIdString = (String) map.get("SYS_MENU_ID_");
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
	public Map<String, Object> findNodeNotChilds(String parentId) throws Exception {
		Map<String, Object> returnMap = null;
		// 2级菜单
		List<Map<String, Object>> childMapList = this.findSysMenuChild_RoleShowList(parentId);
		for (Map<String, Object> childMap : childMapList) {
			// 2级菜单id
			String childIdStr = (String) childMap.get("SYS_MENU_ID_");
			// 2级菜单id
			String childId = childIdStr.toString();
			// 3级菜单
			List<Map<String, Object>> menuThirdList = this.findSysMenuChild_RoleShowList(childId);
			if (menuThirdList.size() == 0) {
				return childMap;
			}
			returnMap = this.findNodeNotChilds(childId);
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
	public Map<String, Object> findNodeNotChilds_v3(String parentId) throws Exception {
		List<Map<String, Object>> mapList = this.findSysMenuChildList(parentId);
		return this.findNodeNotChilds_v2(mapList);
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
	public Map<String, Object> findNodeNotChilds_v2(List<Map<String, Object>> mapList) throws Exception {
		/**
		 * 找出下一级菜单中没有孩子的节点;
		 * 
		 * 找出 2级菜单中（没有孩子的节点）;
		 */
		for (Map<String, Object> map : mapList) {
			// 2级菜单id
			Integer childIdInt = (Integer) map.get("SYS_MENU_ID_");
			// 2级菜单id
			String childIdStr = childIdInt.toString();
			// 3级菜单
			List<Map<String, Object>> menuThirdMapList = this.findSysMenuChildList(childIdStr);
			if (menuThirdMapList.size() == 0) {
				return map;
			} else {
				// 3级菜单id
				Map<String, Object> menuThirdId = this.findNodeNotChilds_v2(menuThirdMapList);
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
	public String findNodeNotChilds_v1(List<Map<String, Object>> mapList) throws Exception {
		/**
		 * 找出 2级菜单中（没有孩子的节点）
		 */
		for (Map<String, Object> map : mapList) {
			// 2级菜单id
			Integer menuChildIdInt = (Integer) map.get("SYS_MENU_ID_");
			// 2级菜单id
			String menuChildIdStr = menuChildIdInt.toString();
			// 3级菜单
			List<Map<String, Object>> menuThirdMapList = this.findSysMenuChildList(menuChildIdStr);
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
			Integer menuChildIdInt = (Integer) map.get("SYS_MENU_ID_");
			// 2级菜单id
			String menuChildIdStr = menuChildIdInt.toString();
			// 3级菜单
			List<Map<String, Object>> menuThirdMapList = this.findSysMenuChildList(menuChildIdStr);
			for (Map<String, Object> menuThirdMap : menuThirdMapList) {
				// 3级菜单id
				Integer menuThirdIdInt = (Integer) menuThirdMap.get("SYS_MENU_ID_");
				String menuThirdIdStr = menuThirdIdInt.toString();
				// 4级菜单
				List<Map<String, Object>> menuFourthMapList = this.findSysMenuChildList(menuThirdIdStr);
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
