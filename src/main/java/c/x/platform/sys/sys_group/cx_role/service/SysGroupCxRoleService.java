package c.x.platform.sys.sys_group.cx_role.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.x.platform.root.common.service.BaseService;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import all.gen.sys_group.t.entity.SysGroupT;
import all.gen.sys_group_user.t.entity.SysGroupUserT;
import all.gen.sys_menu.t.entity.SysMenuT;
public class SysGroupCxRoleService extends BaseService {
	protected Logger log = LogManager.getLogger(SysGroupCxRoleService.class);
	/**
	 * 
	 * 6角色与菜单;
	 * 
	 * 删除角色与菜单列表
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public void delMenuGroupByRoleId(String groupId) throws Exception {
		String sql = "delete from SYS_GROUP_MENU where SYS_GROUP_ID_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(groupId);
		dao.execute(sql, parameterList);
	}
	/**
	 * 5角色与菜单;
	 * 
	 * 保存角色与菜单列表
	 * 
	 * @param groupId
	 * @param menuIdArray
	 * @throws Exception
	 */
	public void save_groupId_menuIdList(String groupId, String[] menuIdArray) throws Exception {
		if (menuIdArray != null) {
			for (String menuId : menuIdArray) {
				this.save_role_menu(groupId, menuId);
			}
		}
	}
	/**
	 * 
	 * 3角色与菜单;
	 * 
	 * 保存角色与菜单
	 * 
	 * @param groupId
	 * @param menuId
	 * @throws Exception
	 */
	public void save_role_menu(String groupId, String menuId) throws Exception {
		String sql = "INSERT INTO SYS_GROUP_MENU(SYS_GROUP_MENU_ID_,SYS_GROUP_ID_,SYS_MENU_ID_) values(?,?,?)";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(this.findPKSimple("SYS_GROUP_MENU"));
		parameterList.add(groupId);
		parameterList.add(menuId);
		dao.execute(sql, parameterList);
	}
	/**
	 * 2角色与菜单;
	 * 
	 * 通过角色id查找所有的菜单(path)(toString)
	 * 
	 * @param SYS_GROUP_ID_
	 * @return
	 * @throws Exception
	 */
	public String listMenuPath_by_roleId_toString(String SYS_GROUP_ID_) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<SysMenuT> list = this.listMenu_by_roleId(SYS_GROUP_ID_);
		for (SysMenuT menu : list) {
			sb.append(menu.getPath());
			sb.append(",");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		// log.trace("通过角色id查找所有的菜单=" + sb.toString());
		return sb.toString();
	}
	/**
	 * 1角色与菜单;
	 * 
	 * 通过角色id查找所有的菜单(ids,path)
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List<SysMenuT> listMenu_by_roleId(String groupId) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("	select m.SYS_MENU_ID_ as SYS_MENU_ID_,m.PATH_ as PATH_");
		sb.append("	FROM SYS_MENU  m");
		sb.append("		left join ");
		sb.append("	 SYS_GROUP_MENU  rm");
		sb.append("	on m.SYS_MENU_ID_=rm.SYS_MENU_ID_");
		sb.append("	where rm.SYS_GROUP_ID_=" + groupId);
		String sql = sb.toString();
		log.trace("sql=" + sql);
		List<SysMenuT> list = this.dao.findObjectList(SysMenuT.class, sql);
		return list;
	}
	/**
	 * 
	 * 6角色与用户;
	 * 
	 * 删除用户与角色列表
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public void delGroupUserByUserId(String userId) throws Exception {
		String sql = "delete from SYS_GROUP_USER  where SYS_USER_ID_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(userId);
		dao.execute(sql, parameterList);
	}
	/**
	 * 
	 * 5角色与用户;
	 * 
	 * 保存用户与角色列表
	 * 
	 * @param userId
	 * @param groupIdArray
	 * @throws Exception
	 */
	public void save_userId_groupRoleIdList(String userId, String[] groupIdArray) throws Exception {
		if (groupIdArray != null) {
			for (String groupId : groupIdArray) {
				this.save_user_groupRole(userId, groupId);
			}
		}
	}
	/**
	 * 
	 * 3角色与用户;
	 * 
	 * 保存用户与角色
	 * 
	 * @param userId
	 * @param groupId
	 * @throws Exception
	 */
	public void save_user_groupRole(String userId, String groupId) throws Exception {
		SysGroupUserT entity = new SysGroupUserT();
		entity.setSysGroupId(groupId);
		entity.setSysUserId(userId);
		dao.save(entity);
	}
	/**
	 * 2角色与用户;
	 * 
	 * 通过用户id查找所有的角色(ids)(toString)
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String findGroupRoleListByUserId2String(String userId) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<SysGroupT> list = this.findGroupRoleListByUserId(userId);
		for (SysGroupT group : list) {
			sb.append(group.getSysGroupId());
			sb.append(",");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		// log.trace("通过用户id查找所有的角色=" + sb.toString());
		return sb.toString();
	}
	/**
	 * 1角色与用户;
	 * 
	 * 通过用户id查找所有的角色(ids)
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<SysGroupT> findGroupRoleListByUserId(String userId) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("	select r.SYS_GROUP_ID_ as SYS_GROUP_ID_ FROM SYS_GROUP r");
		sb.append("		left join ");
		sb.append("	SYS_GROUP_USER ru ");
		sb.append("	on r.SYS_GROUP_ID_=ru.SYS_GROUP_ID_");
		sb.append("	where ru.SYS_USER_ID_='" + userId + "'");
		String sql = sb.toString();
		log.trace("sql=" + sql);
		List<SysGroupT> list = this.dao.findObjectList(SysGroupT.class, sql);
		return list;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @param permission_grade
	 *            权限等级
	 * @return
	 * @throws Exception
	 */
	public List<SysGroupT> findListByPermissionGrade(Integer permission_grade) throws Exception {
		String sql = "SELECT * FROM SYS_GROUP where PERMISSION_GRADE_>=" + permission_grade
				+ "  order by UPDATE_TIME_ desc";
		List<SysGroupT> list = this.dao.findObjectList(SysGroupT.class, sql);
		return list;
	}
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysGroupT find(String id) throws Exception {
		return (SysGroupT ) this.dao.find(SysGroupT.class, id);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysGroupT entity) throws Exception {
		dao.update(entity);
	}
	/**
	 * 
	 * 删除所有
	 * 
	 * @param idArray
	 * @throws Exception
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : idArray) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete FROM SYS_GROUP where SYS_GROUP_ID_ in(" + stringBuffer.toString() + ")";
			dao.execute(sql, null);
		}
	}
	/**
	 * 
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {
		String sql = "delete FROM SYS_GROUP where SYS_GROUP_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(SysGroupT entity) throws Exception {
		return dao.save(entity);
	}
	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param order_property
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortOrderName, String sortFieldName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM SYS_GROUP ";
			sql = "SELECT * FROM SYS_GROUP order by SYS_GROUP_ID_ desc";
		} else {
			sql_count = "SELECT count(*) FROM SYS_GROUP ";
			sql = "SELECT * FROM SYS_GROUP order by " + sortFieldName + " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> pageBean = dao.page(sql, parameters, pageIndex.intValue(),
				pageSize.intValue(), sql_count);
		return pageBean;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM SYS_GROUP order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
