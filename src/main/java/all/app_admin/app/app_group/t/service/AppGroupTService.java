package all.app_admin.app.app_group.t.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.gen.app_group.t.entity.AppGroupT;
import all.gen.app_menu.t.entity.AppMenuT;
import all.gen.app_user_group.t.entity.AppUserGroupT;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;

public class AppGroupTService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
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
		String sql = "delete from APP_MENU_GROUP where APP_GROUP_ID_=?";
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
		String sql = "INSERT INTO APP_MENU_GROUP(APP_MENU_GROUP_ID_,APP_GROUP_ID_,APP_MENU_ID_) values(?,?,?)";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(this.findPKSimple("APP_GROUP_MENU"));
		parameterList.add(groupId);
		parameterList.add(menuId);
		dao.execute(sql, parameterList);
	}
	/**
	 * 2角色与菜单;
	 * 
	 * 通过角色id查找所有的菜单(path)(toString)
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public String findMenuPathListByRoleId2String(String groupId) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<AppMenuT> list = this.findMenuListByRoleId(groupId);
		for (AppMenuT info : list) {
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
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List<AppMenuT> findMenuListByRoleId(String groupId) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> parameterList = new ArrayList<Object>();
		sb.append("	select m.APP_MENU_ID_ as APP_MENU_ID_,m.PATH_ as PATH_");
		sb.append("	FROM APP_MENU  m");
		sb.append("		left join ");
		sb.append("	 APP_MENU_GROUP  rm");
		sb.append("	on m.APP_MENU_ID_=rm.APP_MENU_ID_");
		sb.append("	where rm.APP_GROUP_ID_=? ");
		String sql = sb.toString();
		log.trace("sql=" + sql);
		parameterList.add(groupId);
		List<AppMenuT> list = this.dao.findObjectList(AppMenuT.class, sql,parameterList);
		return list;
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
	public String findRoleListByUserId2String(String userId) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<AppGroupT> list = this.findRoleListByUserId(userId);
		for (AppGroupT info : list) {
			sb.append(info.getAppGroupId());
			sb.append(",");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		// log.trace("通过用户id查找所有的角色=" + sb.toString());
		return sb.toString();
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
		String sql = "delete from APP_USER_GROUP  where APP_USER_ID_=?";
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
		AppUserGroupT entity = new AppUserGroupT();
		entity.setAppGroupId(groupId);
		entity.setAppUserId(userId);
		dao.save(entity);
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
	public List<AppGroupT> findRoleListByUserId(String userId) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("	select r.APP_GROUP_ID_ as APP_GROUP_ID_ FROM APP_GROUP r");
		sb.append("		left join ");
		sb.append("	APP_USER_GROUP ru ");
		sb.append("	on r.APP_GROUP_ID_=ru.APP_GROUP_ID_");
		sb.append("	where ru.APP_USER_ID_='" + userId + "'");
		String sql = sb.toString();
		log.trace("sql=" + sql);
		List<AppGroupT> list = this.dao.findObjectList(AppGroupT.class, sql);
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
	public List<AppGroupT> findAllPermissionGrade(Integer permission_grade) throws Exception {
		String sql = "SELECT * FROM APP_GROUP where PERMISSION_GRADE_>=" + permission_grade
				+ "  order by APP_GROUP_ID_ desc";
		List<AppGroupT> list = this.dao.findObjectList(AppGroupT.class, sql);
		return list;
	}

	
	/**
	 * 
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(AppGroupT entity) throws Exception {

		return dao.save(entity);
	}
	/**
	 * 
	 * 逻辑删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void del(String id) throws Exception {

		String sql = "update app_group set state_='DEL' where APP_GROUP_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 
	 * 逻辑删除所有
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
			String sql = "update app_group set state_='DEL' where APP_GROUP_ID_ in(" + stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}

	}

	/**
	 * 
	 * 物理删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delPhysical(String id) throws Exception {

		String sql = "delete from app_group where APP_GROUP_ID_=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 
	 * 物理删除所有
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delAllPhysical(String[] ids) throws Exception {
		if (ids != null) {
			StringBuilder stringBuffer = new StringBuilder();
			for (String id : ids) {
				stringBuffer.append("'").append(id).append("'").append(",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			String sql = "delete from app_group where APP_GROUP_ID_ in(" + stringBuffer.toString() + ")";

			dao.execute(sql, null);
		}

	}

	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(AppGroupT entity) throws Exception {
		dao.update(entity);
	}
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AppGroupT find(String id) throws Exception {
		return (AppGroupT) this.dao.find(AppGroupT.class, id);

	}

	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param sortFieldName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<Map<String, Object>> find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize,String tenantCode) throws Exception {

		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM app_group where state_!='DEL' and TENANT_CODE_=?  ";
			sql = "SELECT * FROM app_group  where state_!='DEL' and TENANT_CODE_=?  order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM app_group where state_!='DEL' and TENANT_CODE_=?   ";
			sql = "SELECT * FROM app_group  where state_!='DEL' and TENANT_CODE_=?  order by " + sortFieldName + " " + sortOrderName;
		}
		parameters .add(tenantCode);
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(),
				pageSize.intValue(), sql_count,parameters);

		return basePage;
	}

	/**
	 * 
	 * 分页
	 * 
	 * @param order
	 * @param sortFieldName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageCoreBean<AppGroupT> findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;

		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM app_group where state_!='DEL'";
			sql = "SELECT * FROM app_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {

			sql_count = "SELECT count(*) FROM app_group where state_!='DEL'";
			sql = "SELECT * FROM app_group  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}

		PageCoreBean<AppGroupT> basePage = dao.page(AppGroupT.class, sql, null, pageIndex.intValue(),
				pageSize.intValue(), sql_count);
		return basePage;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		// String sql = "SELECT * FROM app_group where state_!='DEL' order by
		// APP_GROUP_ID_ desc";

		String sql = "SELECT * FROM app_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}

	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<AppGroupT> findObjectAll() throws Exception {
		// String sql = "SELECT * FROM app_group where state_!='DEL' order by
		// APP_GROUP_ID_ desc";
		String sql = "SELECT * FROM app_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<AppGroupT> list = this.dao.findObjectList(AppGroupT.class, sql);

		return list;
	}
	/**
	 * 
	 * @Title: findIdByGroupCode 
	 * @Description: 通过用户类型获取用户组ID
	 *
	 * 参数说明 
	 * @param type 
	 * 返回类型 void
	 * @return 
	 * @throws SQLException 
	 */
	public String findIdByGroupCode(String type) throws SQLException {
		String sql="select APP_GROUP_ID_ from app_group where APP_GROUP_CODE_=?";
		List<Object> parameterList=new ArrayList<>();
		parameterList.add(type);
		return dao.findString("APP_GROUP_ID_", sql, parameterList);
		
	}
}
