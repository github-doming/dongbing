package all.sys_admin.sys.account_user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import all.gen.sys_account.t.entity.SysAccountT;
import all.gen.sys_user.t.entity.SysUserT;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;

public class SysAccountUserService extends BaseService {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 查找SysUser
	 * 
	 * @Title: findSysUserByAccountId
	 * @Description:
	 *
	 * 				参数说明
	 * @param id
	 * @return
	 * @throws Exception
	 *             返回类型 SysUserT
	 */
	public SysUserT findSysUserByAccountId(String id) throws Exception {
		ArrayList<Object> parameters = new ArrayList<Object>();
		String sql = "SELECT 	u.* FROM sys_account a LEFT JOIN sys_user u ON a.SYS_USER_ID_ = u.SYS_USER_ID_ WHERE a.SYS_ACCOUNT_ID_ = ?";
		parameters.add(id);
		return (SysUserT) this.dao.findObjectUnique(SysUserT.class, sql, parameters);
	}
	/**
	 * 通过用户名，找出用户是否存在
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public boolean isUserByName(String name) throws Exception {
		String sql = "select SYS_ACCOUNT_NAME_ FROM SYS_ACCOUNT where SYS_ACCOUNT_NAME_=?";
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
	 * 保存
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public String save(SysAccountT entity) throws Exception {
		entity.setState(UserStateEnum.OPEN.getCode());
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
		String sql = "update sys_account set state_='DEL' where SYS_ACCOUNT_ID_=?";
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
			String sql = "update sys_account set state_='DEL' where SYS_ACCOUNT_ID_ in(" + stringBuffer.toString()
					+ ")";
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
		String sql = "delete from sys_account where SYS_ACCOUNT_ID_=?";
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
			String sql = "delete from sys_account where SYS_ACCOUNT_ID_ in(" + stringBuffer.toString() + ")";
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
	public void update(SysAccountT entity) throws Exception {
		entity.setState(UserStateEnum.OPEN.getCode());
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
	public SysAccountT find(String id) throws Exception {
		return (SysAccountT) this.dao.find(SysAccountT.class, id);
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
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		ArrayList<Object> parameters = new ArrayList<Object>();
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM sys_account a LEFT JOIN sys_user u ON a.SYS_USER_ID_ = u.SYS_USER_ID_ WHERE a.state_ != 'DEL' ";
			sql = "SELECT a.SYS_ACCOUNT_ID_,a.SYS_USER_ID_,a.SYS_ACCOUNT_NAME_,a.DESC_,u.SYS_USER_NAME_ FROM sys_account a LEFT JOIN sys_user u ON a.SYS_USER_ID_ = u.SYS_USER_ID_ WHERE a.state_ != 'DEL' ORDER BY a.UPDATE_TIME_ DESC";
		} else {
			sql_count = "SELECT count(*) FROM sys_account a LEFT JOIN sys_user u ON a.SYS_USER_ID_ = u.SYS_USER_ID_ WHERE a.state_ != 'DEL' ";
			sql = "SELECT a.SYS_ACCOUNT_ID_,a.SYS_USER_ID_,a.SYS_ACCOUNT_NAME_,a.DESC_,u.SYS_USER_NAME_ FROM sys_account a LEFT JOIN sys_user u ON a.SYS_USER_ID_ = u.SYS_USER_ID_ WHERE a.state_ != 'DEL'   order by "
					+ sortFieldName + " " + sortOrderName;
		}
		PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex.intValue(),
				pageSize.intValue(), sql_count, parameters);
		List<Map<String, Object>> listMap = basePage.getList();
		for (Map<String, Object> map : listMap) {
			Object nickNameObject = map.get("SYS_USER_NAME_");
			if (nickNameObject == null) {
				map.put("SYS_USER_NAME_", "");
			}
			Object descObject = map.get("DESC_");
			if (descObject == null) {
				map.put("DESC_", "");
			}
		}
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
	public PageCoreBean<SysAccountT> findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sql_count = null;
		String sql = null;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql_count = "SELECT count(*) FROM sys_account where state_!='DEL'";
			sql = "SELECT * FROM sys_account  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql_count = "SELECT count(*) FROM sys_account where state_!='DEL'";
			sql = "SELECT * FROM sys_account  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		PageCoreBean<SysAccountT> basePage = dao.page(SysAccountT.class, sql, null, pageIndex.intValue(),
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
		// String sql = "SELECT * FROM sys_account where state_!='DEL' order by
		// SYS_ACCOUNT_ID_ desc";
		String sql = "SELECT * FROM sys_account  where state_!='DEL' order by UPDATE_TIME_ desc";
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
	public List<SysAccountT> findObjectAll() throws Exception {
		// String sql = "SELECT * FROM sys_account where state_!='DEL' order by
		// SYS_ACCOUNT_ID_ desc";
		String sql = "SELECT * FROM sys_account  where state_!='DEL' order by UPDATE_TIME_ desc";
		List<SysAccountT> list = this.dao.findObjectList(SysAccountT.class, sql);
		return list;
	}
}
