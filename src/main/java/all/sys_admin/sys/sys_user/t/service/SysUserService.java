package all.sys_admin.sys.sys_user.t.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import all.gen.sys_user.t.entity.SysUserT;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import c.x.platform.root.login_not.current.CurrentSysUser;
public class SysUserService extends BaseService {
	
	/**
	 * 
	 * 查询SysUser(通过sso)
	 * 
	 * @return
	 * @throws Exception
	 */
	public  SysUserT findSysUserBySSO(String sso) throws Exception {
		SysUserT entity =null;
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(sso.trim());
		String sql = "select SYS_USER_ID_ from sys_sso where value_=?";
		String sysUserId = this.dao.findString("SYS_USER_ID_", sql, parameterList);
		if(StringUtil.isNotBlank(sysUserId)){
			parameterList = new ArrayList<Object>();
			parameterList.add(sysUserId.trim());
			sql = "SELECT * FROM sys_user  WHERE SYS_USER_ID_ = ?";
			entity = (SysUserT) this.dao.findObject(SysUserT.class, sql, parameterList);
		}
		return entity;
	}
	
	/**
	 * 通过用户名，找出用户是否存在
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public boolean isUserByName(String name) throws Exception {
		String sql = "select SYS_USER_NAME_ FROM SYS_USER where SYS_USER_NAME_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(name);
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, parameterList);
		if (listMap.size() == 0) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SysUserT find(String id) throws Exception {
		return (SysUserT) this.dao.find(SysUserT.class, id);
	}
	/**
	 * 
	 * 更新
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(SysUserT entity) throws Exception {
		entity.setState(UserStateEnum.OPEN.getCode());
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
			String sql = "delete FROM SYS_USER where SYS_USER_ID_ in(" + stringBuffer.toString() + ")";
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
		String sql = "delete FROM SYS_USER where SYS_USER_ID_=?";
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
	public String save(SysUserT entity) throws Exception {
		entity.setState(UserStateEnum.OPEN.getCode());
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
			sql_count = "SELECT count(*) FROM SYS_USER ";
			sql = "SELECT * FROM SYS_USER where PERMISSION_GRADE_>=? order by SYS_USER_ID_ desc";
		} else {
			sql_count = "SELECT count(*) FROM SYS_USER ";
			sql = "SELECT * FROM SYS_USER where PERMISSION_GRADE_>=?  order by " + sortFieldName + " " + sortOrderName;
		}
		parameters.add(permissionGrade);
		PageCoreBean<Map<String, Object>> page = dao.page(sql, parameters, pageIndex.intValue(), pageSize.intValue(),
				sql_count);
		List<Map<String, Object>> listMap = page.getList();
		for (Map<String, Object> map : listMap) {
			Object desc = map.get("DESC_");
			if (desc == null) {
				map.put("DESC_", "");
			}
		}
		return page;
	}
	/**
	 * 
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM SYS_USER order by UPDATE_TIME_ desc";
		List<Map<String, Object>> listMap = this.dao.findMapList(sql, null);
		return listMap;
	}
}
