package com.ibm.connector.service.authority;

import all.app.common.service.AppAccountService;
import all.app.common.service.AppUserRedisService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_user.t.entity.AppUserT;
import all.gen.app_user_group.t.entity.AppUserGroupT;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.connector.service.user.AppUserService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.entity.IbmManageTime;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import org.doming.core.tools.*;

import java.sql.SQLException;
import java.util.*;

/**
 * 权限服务类
 *
 * @Author: Dongming
 * @Date: 2020-03-27 17:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AuthorityService extends BaseServicePlus<Object> {

	//region 菜单

	/**
	 * 根据请求地址，获取菜单权限
	 *
	 * @param userId 请求用户ID
	 * @param url    请求地址
	 * @return 菜单权限
	 */
	public Map<String, Object> findUserMenu(String userId, String url) throws SQLException {
		String sql = "SELECT DISTINCT am.* FROM app_user_group aug "
				+ " LEFT JOIN app_menu_group amg ON aug.APP_GROUP_ID_ = amg.APP_GROUP_ID_ "
				+ " LEFT JOIN app_menu am ON amg.APP_MENU_ID_ = am.APP_MENU_ID_ "
				+ " WHERE aug.APP_USER_ID_ = ? AND am.ADMIN_URL_ = ? AND aug.STATE_ != ? AND amg.STATE_ != ? AND am.STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(url);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 校验权限
	 *
	 * @param menuIds 用户的菜单主键
	 * @param url     校验地址
	 * @return 菜单权限
	 */
	public Map<String, Object> checkAuthority(String menuIds, String url) throws SQLException {
		String sql =
				"SELECT * FROM app_menu WHERE APP_MENU_ID_ IN ('" + menuIds + "') AND ADMIN_URL_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(url);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 查询管理者的 数据权限等级
	 *
	 * @return 数据权限等级
	 */
	public Integer findPermissionGrade4Admin() throws SQLException {
		String sql = "SELECT PERMISSION_GRADE_ FROM `app_group` where APP_GROUP_CODE_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(ChannelTypeEnum.ADMIN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return NumberTool.getInteger(super.findMap(sql, parameterList).getOrDefault("PERMISSION_GRADE_", 0));
	}

	/**
	 * 查询给定用户ID的 数据权限等级
	 *
	 * @param userId 用户ID
	 * @return 数据权限等级
	 */
	public Integer findPermissionGrade(String userId) throws SQLException {
		String sql = "SELECT ag.PERMISSION_GRADE_ FROM app_user_group aug LEFT JOIN app_group ag ON "
				+ " aug.APP_GROUP_ID_ = ag.APP_GROUP_ID_ WHERE aug.APP_USER_ID_ = ? AND ag.STATE_ != ? AND aug.STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return NumberTool
				.getInteger(super.findMap(sql, parameterList).getOrDefault("PERMISSION_GRADE_", Integer.MAX_VALUE));

	}

	/**
	 * 获取用户 所拥有的菜单列表
	 *
	 * @param userId 用户id
	 * @return 菜单列表ID
	 */
	public List<String> listMenuIds4User(String userId) throws SQLException {
		String sql = "SELECT amg.APP_MENU_ID_ as key_ FROM app_menu_group amg "
				+ " LEFT JOIN app_user_group aug ON amg.APP_GROUP_ID_ = aug.APP_GROUP_ID_ "
				+ " WHERE aug.APP_USER_ID_ = ? AND amg.STATE_ != ? AND aug.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findStringList(sql, parameterList);

	}

	/**
	 * 查询所有的菜单ID
	 *
	 * @return 菜单列表ID
	 */
	public List<String> listAllMenuIds() throws SQLException {
		String sql = "SELECT APP_MENU_ID_ as key_ FROM app_menu WHERE STATE_ != ? ORDER BY PATH_";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findStringList(sql, parameterList);
	}


	/**
	 * 根据缓存ID 获取用户的菜单
	 *
	 * @param menuIds 缓存菜单ID
	 * @return 用户的菜单
	 */
	public List<Map<String, Object>> listUserMenusByCacheIds(String menuIds) throws SQLException {
		String sql = "SELECT * FROM app_menu WHERE APP_MENU_ID_ IN ('" + menuIds + "') AND STATE_ != ? ORDER BY PATH_";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 获取用户的菜单列表
	 *
	 * @param userId 用户主键
	 * @return 菜单列表
	 */
	public List<Map<String, Object>> listUserMenus(String userId) throws SQLException {
		String sql = "SELECT am.APP_MENU_ID_ as id,am.APP_MENU_NAME_,am.APP_MENU_CODE_,am.ADMIN_URL_,am.ADMIN_PIC_, "
				+ " am.SN_,am.PERMISSION_CODE_,am.PARENT_ as pid,am.STATE_,am.DESC_ as MENU_TYPE_ FROM app_menu am "
				+ " LEFT JOIN app_menu_group amg ON am.APP_MENU_ID_ = amg.APP_MENU_ID_ "
				+ " LEFT JOIN app_user_group aug ON amg.APP_GROUP_ID_ = aug.APP_GROUP_ID_ "
				+ " WHERE aug.APP_USER_ID_ = ? AND am.STATE_ != ? AND amg.STATE_ != ? AND aug.STATE_ != ? ORDER BY SN_";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 获取用户的主菜单列表
	 *
	 * @param userId 用户主键
	 * @param pid    父级菜单ID
	 * @return 菜单列表
	 */
	public List<Map<String, Object>> listManageUserMenus(String userId, String pid) throws SQLException {
		String sql = "SELECT am.APP_MENU_ID_ as id,am.APP_MENU_NAME_,am.APP_MENU_CODE_,am.ADMIN_URL_ htmlUrl,am.ADMIN_PIC_, "
				+ " am.SN_,am.PERMISSION_CODE_ jsUrl,am.PARENT_ as pid,am.STATE_ FROM app_menu am "
				+ " LEFT JOIN app_menu_group amg ON am.APP_MENU_ID_ = amg.APP_MENU_ID_ "
				+ " LEFT JOIN app_user_group aug ON amg.APP_GROUP_ID_ = aug.APP_GROUP_ID_ "
				+ " WHERE aug.APP_USER_ID_ = ? AND am.STATE_ != ? AND amg.STATE_ != ? AND aug.STATE_ != ? AND am.PARENT_ =? AND am.DESC_ =? ORDER BY SN_";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(pid);
		parameterList.add("MENU");
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 根据菜单主键查找菜单
	 *
	 * @param menuId 菜单主键
	 * @return 菜单
	 */
	public Map<String, Object> findMenu(String menuId) throws SQLException {
		String sql = "SELECT APP_MENU_ID_,APP_MENU_NAME_,APP_MENU_CODE_,ADMIN_URL_,ADMIN_PIC_,PATH_,PERMISSION_GRADE_, "
				+ " SN_,PERMISSION_CODE_,PARENT_,STATE_,DESC_ as MENU_TYPE_ FROM app_menu WHERE APP_MENU_ID_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(menuId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 查询菜单是否以存在
	 *
	 * @param menu 菜单键，可能为 url，也可为 permissionCode
	 * @return 存在 true
	 */
	public boolean checkMenu(String menu) throws SQLException {
		String sql = "SELECT APP_MENU_ID_ FROM app_menu WHERE (ADMIN_URL_ = ? or PERMISSION_CODE_ = ?) AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(menu);
		parameterList.add(menu);
		parameterList.add(IbmStateEnum.DEL.name());
		return ContainerTool.notEmpty(super.findMapList(sql, parameterList));
	}

	/**
	 * 存储菜单
	 *
	 * @param menu            修改的菜单
	 * @param parentId        菜单的父类
	 * @param path            菜单路径
	 * @param permissionGrade 权限等级
	 * @param menuType        菜单类型
	 * @param tenant          租户编码
	 * @param userId          更新用户主键
	 */
	public void saveMenu(Menu menu, String parentId, String path, Integer permissionGrade, String menuType, String tenant, String userId)
			throws SQLException {
		String sql = "INSERT INTO app_menu (APP_MENU_ID_,APP_MENU_NAME_,APP_MENU_CODE_,CHANNEL_TYPE_, "
				+ " PERMISSION_CODE_,SN_,PARENT_,PATH_,ADMIN_URL_,ADMIN_PIC_,PERMISSION_GRADE_,CREATE_USER_,"
				+ " CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,TENANT_CODE_,STATE_,DESC_)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List<Object> parameterList = new ArrayList<>(17);
		parameterList.add(menu.getMenuId());
		parameterList.add(menu.getMenuName());
		parameterList.add(menu.getMenuCode());
		parameterList.add(ChannelTypeEnum.ADMIN.name());
		parameterList.add(menu.getPermissionCode());
		parameterList.add(menu.getSn());
		parameterList.add(parentId);
		parameterList.add(path);
		parameterList.add(menu.getMenuUrl());
		parameterList.add(menu.getMenuPic());
		parameterList.add(permissionGrade);
		parameterList.add(userId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(new Date());
		parameterList.add(tenant);
		parameterList.add(menu.getState());
		parameterList.add(menuType);
		super.execute(sql, parameterList);
	}

	/**
	 * 将目录分配至用户所在角色
	 *
	 * @param menuId 目录主键
	 * @param userId 用户主键
	 */
	public void saveMenu2Role(String menuId, String userId) throws SQLException {
		String roleId = findUserRole(userId).get("APP_GROUP_ID_").toString();
		String sql =
				"INSERT into app_menu_group (APP_GROUP_ID_,CREATE_USER_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_, "
						+ " STATE_,APP_MENU_GROUP_ID_,APP_MENU_ID_) VALUES (?,?,?,?,?,?,?,?)";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(roleId);
		parameterList.add(userId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(RandomTool.getNumLetter32());
		parameterList.add(menuId);
		super.execute(sql, parameterList);
	}

	/**
	 * 更新菜单 - 有修改才会进行修改存储
	 *
	 * @param menu            修改的菜单
	 * @param parent          菜单的父类
	 * @param path            菜单路径
	 * @param permissionGrade 权限等级
	 * @param menuType        目录类型
	 * @param userId          更新用户主键
	 */
	public void updateMenu(Menu menu, String parent, String path, Integer permissionGrade, String menuType, String userId)
			throws SQLException {
		StringBuilder sql = new StringBuilder("UPDATE app_menu set ");
		List<Object> parameterList = new ArrayList<>(10);
		if (StringTool.notEmpty(parent)) {
			sql.append("PARENT_ = ?,");
			parameterList.add(menu.getMenuId());
		}
		if (StringTool.notEmpty(path)) {
			sql.append("PATH_ = ?,");
			parameterList.add(path);
		}
		if (StringTool.notEmpty(permissionGrade)) {
			sql.append("PERMISSION_GRADE_ = ?,");
			parameterList.add(permissionGrade);
		}
		if (StringTool.notEmpty(menu.getMenuName())) {
			sql.append("APP_MENU_NAME_ = ?,");
			parameterList.add(menu.getMenuName());
		}
		if (StringTool.notEmpty(menu.getMenuCode())) {
			sql.append("APP_MENU_CODE_ = ?,");
			parameterList.add(menu.getMenuCode());
		}
		if (StringTool.notEmpty(menu.getMenuPic())) {
			sql.append("ADMIN_PIC_ = ?,");
			parameterList.add(menu.getMenuPic());
		}
		if (StringTool.notEmpty(menu.getMenuUrl())) {
			sql.append("ADMIN_URL_ = ?,");
			parameterList.add(menu.getMenuUrl());
		}
		if (StringTool.notEmpty(menu.getPermissionCode())) {
			sql.append("PERMISSION_CODE_ = ?,");
			parameterList.add(menu.getPermissionCode());
		}
		if (Objects.nonNull(menu.getSn())) {
			sql.append("SN_ = ?,");
			parameterList.add(menu.getSn());
		}
		if (StringTool.notEmpty(menu.getState())) {
			sql.append("STATE_ = ?,");
			parameterList.add(menu.getState());
		}
		if (StringTool.notEmpty(menuType)) {
			sql.append("DESC_ = ?,");
			parameterList.add(menuType);
		}
		sql.append("UPDATE_USER_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE APP_MENU_ID_ = ? and STATE_ != ?");
		parameterList.add(userId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(menu.getMenuId());
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(sql.toString(), parameterList);
	}

	/**
	 * 获取用户的所有菜单
	 *
	 * @param userId 用户主键
	 * @return 用户菜单
	 */
	public Menu getUserAllMenu(String userId) throws SQLException {
		List<Map<String, Object>> menuInfos = listUserMenus(userId);

		//级联出菜单项
		Map<String, Menu> menuMap = new HashMap<>(menuInfos.size());
		Menu root = new Menu("1");
		menuMap.put("1", root);
		for (Map<String, Object> menuInfo : menuInfos) {
			String menuId = menuInfo.get("APP_MENU_ID_").toString();
			Menu menu = menuMap.getOrDefault(menuId, new Menu(menuId));
			menu.attr(StringTool.getString(menuInfo, "APP_MENU_NAME_", null),
					StringTool.getString(menuInfo, "APP_MENU_CODE_", null),
					StringTool.getString(menuInfo, "ADMIN_URL_", null),
					StringTool.getString(menuInfo, "PERMISSION_CODE_", null),
					StringTool.getString(menuInfo, "ADMIN_PIC_", null), NumberTool.getInteger(menuInfo.get("SN_"), 0),
					StringTool.getString(menuInfo, "STATE_", null));
			menuMap.put(menuId, menu);

			String parentId = menuInfo.get("PARENT_").toString();
			Menu parentMenu = menuMap.getOrDefault(parentId, new Menu(parentId));
			parentMenu.addChild(menu);
			menuMap.put(parentId, parentMenu);
		}
		return root;
	}

	/**
	 * 更新子菜单路径
	 *
	 * @param menuId 菜单主键
	 * @param path   路径
	 */
	public void updateMenuChild(String menuId, String path) throws SQLException {
		List<String> childMenuIds = listChildMenuId(menuId);
		if (ContainerTool.isEmpty(childMenuIds)) {
			return;
		}
		for (String childMenuId : childMenuIds) {
			String childPath = path.concat(childMenuId).concat(".");
			updateMenuPath(childMenuId, path);
			updateMenuChild(childMenuId, childPath);
		}
	}

	/**
	 * 获取子类菜单
	 *
	 * @param menuId 菜单主键
	 * @return 子类菜单
	 */
	public List<String> listChildMenuId(String menuId) throws SQLException {
		String sql = "SELECT APP_MENU_ID_ as key_ from app_menu where PARENT_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(menuId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findStringList(sql, parameterList);
	}

	/**
	 * 更新路径
	 *
	 * @param menuId 更新菜单主键
	 * @param path   路径
	 */
	public void updateMenuPath(String menuId, String path) throws SQLException {
		String sql = "UPDATE app_menu set PATH_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE APP_MENU_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(path);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(menuId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(sql, parameterList);
	}

	/**
	 * 修改上级菜单
	 *
	 * @param menuId 更新菜单主键
	 * @param parent 上级菜单主键
	 * @param path   上级菜单路径
	 */
	public void updateMenuParent(String menuId, String parent, String path) throws SQLException {
		String sql = "UPDATE app_menu set PARENT_ = ?,PATH_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " WHERE APP_MENU_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(parent);
		parameterList.add(path);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(menuId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(sql, parameterList);
	}

	/**
	 * 删除目录
	 *
	 * @param menuId 菜单主键
	 * @param userId 操作者主键
	 */
	public void deleteMenu(String menuId, String userId) throws SQLException {
		String sql = "UPDATE %s set STATE_ = ?,UPDATE_USER_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " WHERE APP_MENU_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(userId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(menuId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(String.format(sql, "app_menu"), parameterList);
		super.execute(String.format(sql, "app_menu_group"), parameterList);
	}

	//endregion

	//region 角色

	/**
	 * 获取用户的角色列表
	 *
	 * @param userId   用户主键
	 * @param roleName 查询角色建
	 * @param roleCode 查询角色编码键
	 * @return 角色列表
	 */
	public List<Map<String, Object>> listUserAllRole(String userId, String roleName, String roleCode)
			throws SQLException {

		int perGrade = findPermissionGrade(userId);
		String sql = "SELECT APP_GROUP_ID_,APP_GROUP_NAME_,APP_GROUP_CODE_,PERMISSION_GRADE_,STATE_ FROM app_group WHERE STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbmStateEnum.DEL.name());
		if (perGrade > 0) {
			sql += " AND PERMISSION_GRADE_ > ? ";
			parameterList.add(perGrade);
		}
		if (StringTool.notEmpty(roleName)) {
			sql += " and APP_GROUP_NAME_ LIKE ? ";
			parameterList.add("%" + roleName + "%");
		}
		if (StringTool.notEmpty(roleCode)) {
			sql += " and APP_GROUP_CODE_ like ? ";
			parameterList.add("%" + roleCode + "%");
		}
		sql += " ORDER BY PERMISSION_GRADE_";
		return super.findMapList(sql, parameterList);

	}

	/**
	 * 获取角色
	 *
	 * @param roleId 角色主键
	 * @return 角色信息
	 */
	public Map<String, Object> findRole(String roleId) throws SQLException {
		String sql = "SELECT APP_GROUP_NAME_,APP_GROUP_CODE_,PERMISSION_GRADE_,STATE_ "
				+ " FROM `app_group` where APP_GROUP_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(roleId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 获取角色
	 *
	 * @param roleId 角色主键
	 * @param userId 用户主键
	 * @return 角色信息
	 */
	public Map<String, Object> findRole(String roleId, String userId) throws SQLException {
		String sql = "SELECT APP_GROUP_NAME_,APP_GROUP_CODE_,PERMISSION_GRADE_,STATE_ "
				+ " FROM `app_group` where APP_GROUP_ID_ = ? AND PERMISSION_GRADE_ > ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(roleId);
		parameterList.add(findPermissionGrade(userId));
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);

	}

	/**
	 * 获取用户所处的角色信息
	 *
	 * @param userId 角色主键
	 * @return 角色信息
	 */
	public Map<String, Object> findUserRole(String userId) throws SQLException {
		String sql = "SELECT ag.APP_GROUP_ID_,ag.PERMISSION_GRADE_,ag.APP_GROUP_CODE_ FROM app_group ag "
				+ " LEFT JOIN app_user_group aug ON ag.APP_GROUP_ID_ = aug.APP_GROUP_ID_ "
				+ " WHERE aug.APP_USER_ID_ = ? AND aug.STATE_ != ? AND ag.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);

	}

	/**
	 * 查询角色是否以存在
	 *
	 * @param roleCode 角色编码
	 * @return 存在 - true
	 */
	public boolean checkRole(String roleCode) throws SQLException {
		String sql = "SELECT APP_GROUP_ID_ FROM `app_group` where APP_GROUP_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(roleCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return ContainerTool.notEmpty(super.findMapList(sql, parameterList));
	}

	/**
	 * 保存角色信息
	 *
	 * @param roleName  名称
	 * @param roleCode  编码
	 * @param roleGrade 等级
	 * @param state     状态
	 * @param userId    创建者
	 * @return 角色主键
	 */
	public String saveRole(String roleName, String roleCode, Integer roleGrade, String state, String userId)
			throws SQLException {
		String sql =
				"INSERT into app_group (APP_GROUP_ID_,APP_GROUP_NAME_,APP_GROUP_CODE_,PERMISSION_GRADE_,CREATE_USER_,"
						+ " CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) VALUES (?,?,?,?,?,?,?,?,?)";
		List<Object> parameterList = new ArrayList<>(9);
		String roleId = RandomTool.getNumLetter32();
		parameterList.add(roleId);
		parameterList.add(roleName);
		parameterList.add(roleCode);
		parameterList.add(roleGrade);
		parameterList.add(userId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(state);
		super.execute(sql, parameterList);
		return roleId;
	}

	/**
	 * 修改角色
	 *
	 * @param roleId    角色主键
	 * @param roleName  角色名称
	 * @param roleCode  角色编码
	 * @param roleGrade 角色等级
	 * @param state     角色状态
	 * @param userId    修改者主键
	 */
	public void updateRole(String roleId, String roleName, String roleCode, Integer roleGrade, String state,
						   String userId) throws SQLException {
		String sql = "UPDATE app_group set APP_GROUP_NAME_ = ?,APP_GROUP_CODE_ = ?,PERMISSION_GRADE_ = ?, "
				+ " STATE_ = ? ,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,UPDATE_USER_ = ? WHERE APP_GROUP_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(roleName);
		parameterList.add(roleCode);
		parameterList.add(roleGrade);
		parameterList.add(state);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(userId);
		parameterList.add(roleId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(sql, parameterList);
	}

	/**
	 * 获取角色的菜单主键数组
	 *
	 * @param roleId 角色主键
	 * @return 菜单主键数组
	 */
	public List<String> listMenuIds4Role(String roleId) throws SQLException {
		String sql = "SELECT APP_MENU_ID_ as key_ FROM `app_menu_group` where APP_GROUP_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(roleId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findStringList(sql, parameterList);
	}

	/**
	 * 更新角色资源
	 *
	 * @param roleId  角色主键
	 * @param menuIds 资源列表
	 * @param userId  更新者主键
	 */
	public void updateRoleResources(String roleId, List<String> menuIds, String userId) throws SQLException {
		String sql = "UPDATE app_menu_group set STATE_ = ? ,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,UPDATE_USER_ = ?"
				+ " where APP_GROUP_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(10);

		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(userId);
		parameterList.add(roleId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(sql, parameterList);
		sql = "INSERT into app_menu_group (APP_GROUP_ID_,CREATE_USER_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_, "
				+ " STATE_,APP_MENU_GROUP_ID_,APP_MENU_ID_) VALUES (?,?,?,?,?,?,?,?)";
		parameterList.clear();
		parameterList.add(roleId);
		parameterList.add(userId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(new Object());
		parameterList.add(new Object());
		for (String menuId : menuIds) {
			parameterList.remove(6);
			parameterList.remove(6);
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(menuId);
			super.execute(sql, parameterList);
		}

	}

	/**
	 * 删除角色
	 *
	 * @param roleId 角色主键
	 * @param userId 操作者主键
	 */
	public void deleteRole(String roleId, String userId) throws SQLException {
		String sql = "UPDATE %s set STATE_ = ?,UPDATE_USER_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " WHERE APP_GROUP_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(userId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(roleId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(String.format(sql, "app_group"), parameterList);
		super.execute(String.format(sql, "app_menu_group"), parameterList);
	}

	/**
	 * 根据角色编码获取 角色ID
	 *
	 * @return 角色ID
	 */
	public String findRoleId(String roleCode) throws SQLException {
		String sql = "SELECT APP_GROUP_ID_ as key_ "
				+ " FROM `app_group` where APP_GROUP_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(roleCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findString(sql, parameterList);


	}

	//endregion

	//region 用戶

	/**
	 * @param userId    用户主键
	 * @param userName  查询条件名称
	 * @param pageIndex 页面索引
	 * @param pageSize  页面大小
	 * @return 用户可管理的用户列表
	 */
	public PageCoreBean<Map<String, Object>> listAllUser(String userId, String userName, Integer pageIndex,
														 Integer pageSize) throws SQLException {

		int grade = findPermissionGrade(userId);
		grade = grade == 0 ? -1 : grade;

		String sqlCount =
				"SELECT COUNT(*) FROM ibm_user iu LEFT JOIN app_user_group aug ON iu.APP_USER_ID_ = aug.APP_USER_ID_ "
						+ " WHERE aug.APP_GROUP_ID_ IN (SELECT APP_GROUP_ID_ FROM app_group WHERE PERMISSION_GRADE_ > ?) "
						+ " AND iu.STATE_ != ? AND aug.STATE_ != ? ";
		List<Object> parameterListCount = new ArrayList<>(3);
		parameterListCount.add(grade);
		parameterListCount.add(IbmStateEnum.DEL.name());
		parameterListCount.add(IbmStateEnum.DEL.name());

		String sql =
				"SELECT iu.NICKNAME_ APP_USER_NAME_,iu.NICKNAME_,ag.APP_GROUP_NAME_,iu.STATE_, iu.APP_USER_ID_ FROM ibm_user iu "
						+ " LEFT JOIN app_user_group aug ON iu.APP_USER_ID_ = aug.APP_USER_ID_ "
						+ " LEFT JOIN app_group ag ON aug.APP_GROUP_ID_ = ag.APP_GROUP_ID_ "
						+ " WHERE aug.APP_GROUP_ID_ IN (SELECT APP_GROUP_ID_ FROM app_group WHERE PERMISSION_GRADE_ > ?)"
						+ " AND iu.STATE_ != ? AND aug.STATE_ != ? AND iu.STATE_ != ? ";

		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(grade);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		if (StringTool.notEmpty(userName)) {
			sql += " AND iu.NICKNAME_ like ? ";
			sqlCount += " AND iu.NICKNAME_ like ? ";
			parameterList.add("%" + userName + "%");
			parameterListCount.add("%" + userName + "%");
		}
		sql += " ORDER BY iu.LOGIN_TIME_LONG_ DESC ";
		return super.dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterListCount);
	}

	/**
	 * 查找登录信息
	 *
	 * @param userId 用户主键
	 * @return 登录信息
	 */
	public Map<String, Object> findLoginInfo(String userId) throws SQLException {
		String sql = "SELECT distinct APP_USER_ID_,IP_,UPDATE_TIME_ FROM app_token WHERE APP_USER_ID_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户主键
	 * @return 用户信息
	 */
	public Map<String, Object> findUser(String userId) throws SQLException {
		String sql = "SELECT au.APP_USER_NAME_,au.NICKNAME_,ag.APP_GROUP_NAME_,ag.APP_GROUP_CODE_,au.STATE_ FROM app_user au "
				+ " LEFT JOIN app_user_group aug ON au.APP_USER_ID_ = aug.APP_USER_ID_ "
				+ " LEFT JOIN app_group ag ON aug.APP_GROUP_ID_ = AG.APP_GROUP_ID_ "
				+ " WHERE au.APP_USER_ID_ = ? AND au.STATE_ != ? AND aug.STATE_ != ? AND ag.STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 保存用户角色表
	 *
	 * @param appUserId 用户id
	 * @param roleId    角色id
	 * @param userId    创建者
	 * @param nowTime   创建时间
	 */
	public void saveUserRole(String appUserId, String roleId, String userId, Date nowTime) throws Exception {
		AppUserGroupT userGroupT = new AppUserGroupT();
		userGroupT.setAppUserId(appUserId);
		userGroupT.setAppGroupId(roleId);
		userGroupT.setCreateUser(userId);
		userGroupT.setCreateTime(nowTime);
		userGroupT.setCreateTimeLong(System.currentTimeMillis());
		userGroupT.setUpdateTimeLong(System.currentTimeMillis());
		userGroupT.setState(IbmStateEnum.OPEN.name());
		super.dao.save(userGroupT);
	}

	/**
	 * 更新用户
	 *
	 * @param userName 用户名称
	 * @param state    用户状态
	 * @param userId   用户主键
	 * @param updateId 修改用户主键
	 */
	public void updateUser(String userName, String state, String userId, String updateId) throws SQLException {
		String sql = "UPDATE app_user set UPDATE_USER_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(updateId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(state);
		if (StringTool.notEmpty(userName)) {
			sql += ",NICKNAME_ = ?";
			parameterList.add(userName);
		}
		sql += "WHERE APP_USER_ID_ = ? and STATE_ != ?";
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(sql, parameterList);
	}

	/**
	 * 更新用户角色
	 *
	 * @param userId   用户主键
	 * @param roleId   角色主键
	 * @param updateId 更新者主键
	 */
	public void updateUserRole(String userId, String roleId, String updateId) throws SQLException {
		String sql = "SELECT APP_GROUP_ID_,APP_USER_GROUP_ID_ FROM app_user_group where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, Object> roleInfo = super.findMap(sql, parameterList);
		String oldRoleId = roleInfo.get("APP_GROUP_ID_").toString();
		if (oldRoleId.equals(roleId)) {
			return;
		}
		sql = "UPDATE app_user_group set APP_GROUP_ID_ = ?,UPDATE_USER_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " WHERE APP_USER_GROUP_ID_ = ?";
		parameterList.clear();
		parameterList.add(roleId);
		parameterList.add(updateId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(roleInfo.get("APP_USER_GROUP_ID_"));
		super.execute(sql, parameterList);

	}

	/**
	 * 删除用户
	 *
	 * @param userId   用户主键
	 * @param updateId 更新者主键
	 */
	public void deleteUser(String userId, String updateId) throws SQLException {
		String sql = "UPDATE %s set STATE_ = ?,UPDATE_USER_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " WHERE APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(updateId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.execute(String.format(sql, "app_user"), parameterList);
		super.execute(String.format(sql, "app_account"), parameterList);
		super.execute(String.format(sql, "app_token"), parameterList);
		super.execute(String.format(sql, "app_user_group"), parameterList);
	}

	/**
	 * 保存用户
	 *
	 * @param bean         回传实体类
	 * @param userAccount  用户账户
	 * @param nickName     备注名
	 * @param userPassWord 用户密码
	 * @param tenant       机构名称
	 * @param state        用户状态
	 */
	public void saveUser(JsonResultBeanPlus bean, String userAccount, String nickName, String userPassWord, String tenant, String state) throws Exception {
		AppUserT appUser = new AppUserService().findByAccountName(userAccount);
		if (appUser != null) {
			bean.putEnum(ReturnCodeEnum.app409Register);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return;
		}
		Date nowTime = new Date();

		nickName = StringTool.isEmpty(nickName) ? userAccount : nickName;
		appUser = new AppUserT();
		appUser.setAppUserName(userAccount);
		appUser.setNickname(nickName);
		appUser.setAppUserType(ChannelTypeEnum.ADMIN.name());
		appUser.setCreateTime(nowTime);
		appUser.setCreateTimeLong(System.currentTimeMillis());
		appUser.setUpdateTime(nowTime);
		appUser.setUpdateTimeLong(System.currentTimeMillis());
		appUser.setState(state);
		appUser.setTenantCode(tenant);

		String appUserId = new AppUserRedisService().save(appUser);

		//保存会员账户
		AppAccountT appAccount = new AppAccountT();
		userPassWord = EncryptTool.encode(EncryptTool.Type.ASE, userPassWord);
		appAccount.setAccountName(userAccount);
		appAccount.setPassword(userPassWord);
		appAccount.setAppUserId(appUserId);
		appAccount.setCreateTime(nowTime);
		appAccount.setCreateTimeLong(System.currentTimeMillis());
		appAccount.setUpdateTime(nowTime);
		appAccount.setUpdateTimeLong(System.currentTimeMillis());
		appAccount.setState(IbmStateEnum.OPEN.name());
		appAccount.setTenantCode(tenant);
		appAccount.setChannelType(ChannelTypeEnum.ADMIN.name());
		new AppAccountService().save(appAccount);

		IbmManageTime time = new IbmManageTime();
		time.setAppUserId(appUserId);
		time.setRepTimeId(IbmTypeEnum.CARD_ADMIN.name());
		time.setStartTime(nowTime);
		time.setStartTimeLong(System.currentTimeMillis());
		time.setEndTime(DateTool.getDate("2200-12-31"));
		time.setEndTimeLong(System.currentTimeMillis());
		time.setCreateTime(nowTime);
		time.setCreateTimeLong(System.currentTimeMillis());
		time.setUpdateTimeLong(System.currentTimeMillis());
		time.setState(IbmStateEnum.OPEN.name());
		new IbmManageTimeService().save(time);

		bean.success(appUserId);
	}

	/**
	 * 查询用户类型
	 * @param appUserId
	 */
	public String findUserType(String appUserId) throws SQLException {
		String sql = "SELECT ag.APP_GROUP_CODE_ key_ FROM `app_user_group` aug LEFT JOIN app_group ag on aug.APP_GROUP_ID_=ag.APP_GROUP_ID_ " +
				"WHERE aug.APP_USER_ID_ = ? AND aug.STATE_!=? AND ag.STATE_!=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findString(sql, parameterList);

	}
	//endregion

}
