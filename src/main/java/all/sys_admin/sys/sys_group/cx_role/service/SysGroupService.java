package all.sys_admin.sys.sys_group.cx_role.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import all.gen.sys_group.t.entity.SysGroupT;
import all.gen.sys_group_user.t.entity.SysGroupUserT;
import all.gen.sys_menu.t.entity.SysMenuT;
import c.a.util.core.enums.bean.PermissionGradeEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import c.x.platform.root.login_not.current.CurrentSysUser;

public class SysGroupService extends BaseService {
	protected Logger log = LogManager.getLogger(SysGroupService.class);
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
	public void save_roleId_menuIds(String groupId, String[] menuIdArray) throws Exception {
		if (menuIdArray != null) {
			for (String menuId : menuIdArray) {
				this.save_group_menu(groupId, menuId);
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
	public void save_group_menu(String groupId, String menuId) throws Exception {
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
	 * @param sysGroupId
	 * @return
	 * @throws Exception
	 */
	public String findMenuPathListByRoleId2String(String sysGroupId) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<SysMenuT> list = this.findMenuListByRoleId(sysGroupId);
		for (SysMenuT info : list) {
			sb.append(info.getPath());
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
	 * @param SYS_GROUP_ID_
	 * @return
	 * @throws Exception
	 */
	public List<SysMenuT> findMenuListByRoleId(String SYS_GROUP_ID_) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("	select m.SYS_MENU_ID_ as SYS_MENU_ID_,m.PATH_ as PATH_");
		sb.append("	FROM SYS_MENU  m");
		sb.append("		left join ");
		sb.append("	 SYS_GROUP_MENU  rm");
		sb.append("	on m.SYS_MENU_ID_=rm.SYS_MENU_ID_");
		sb.append("	where rm.SYS_GROUP_ID_=" + SYS_GROUP_ID_);
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
	public void save_userId_roleIdList(String userId, String[] groupIdArray) throws Exception {
		if (groupIdArray != null) {
			for (String groupId : groupIdArray) {
				this.save_user_role(userId, groupId);
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
	public void save_user_role(String userId, String groupId) throws Exception {
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
	 * @param sysUserId
	 * @return
	 * @throws Exception
	 */
	public String findRoleListByUserId2String(String sysUserId) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<SysGroupT> list = this.findRoleListByUserId(sysUserId);
		for (SysGroupT info : list) {
			sb.append(info.getSysGroupId());
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
	 * @param sysUserId
	 * @return
	 * @throws Exception
	 */
	public List<SysGroupT> findRoleListByUserId(String sysUserId) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("	select r.SYS_GROUP_ID_ as SYS_GROUP_ID_ FROM SYS_GROUP r");
		sb.append("		left join ");
		sb.append("	SYS_GROUP_USER ru ");
		sb.append("	on r.SYS_GROUP_ID_=ru.SYS_GROUP_ID_");
		sb.append("	where ru.SYS_USER_ID_='" + sysUserId + "'");
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
	public List<SysGroupT> findAllPermissionGrade(Integer permission_grade) throws Exception {
		String sql = "SELECT * FROM SYS_GROUP where PERMISSION_GRADE_>=" + permission_grade
				+ "  order by SYS_GROUP_ID_ desc";
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
		return (SysGroupT) this.dao.find(SysGroupT.class, id);

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
	 * @param ids
	 * @throws Exception
	 */
	public void delAll(String[] ids) throws Exception {
		if (ids != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
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
		entity.setPermissionGrade(Integer.valueOf(PermissionGradeEnum.PERMISSION_GRADE_USER.getCode()));
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
	public PageCoreBean<Map<String, Object>> find(CurrentSysUser currentUser, String sortOrderName, String sortFieldName,
			Integer pageIndex, Integer pageSize) throws Exception {
		Integer permissionGrade = currentUser.getPermissionGrade();
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortOrderName) || StringUtil.isBlank(sortFieldName)) {
			sql_count = "SELECT count(*) FROM SYS_GROUP ";
			sql = "SELECT * FROM SYS_GROUP where PERMISSION_GRADE_>=? order by SYS_GROUP_ID_ desc";
		
		} else {

			sql_count = "SELECT count(*) FROM SYS_GROUP ";
			sql = "SELECT * FROM SYS_GROUP where PERMISSION_GRADE_>=?  order by " + sortFieldName + " " + sortOrderName;
		
		}
		parameters.add(permissionGrade);
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
