package com.ibm.old.v1.pc.app_user.t.service;
import all.app_admin.app.app_user.t.service.AppUserTService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-05-05 11:11
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppUserService extends AppUserTService {
	/**
	 * 根据用户code获取用户信息
	 *
	 * @param code 用户code
	 * @return 用户信息
	 */
	public AppUserT findByCode(String code) throws Exception {
		String sql = "SELECT * FROM APP_USER  where APP_USER_CODE_ = ? and state_ !='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(code);
		Object obj = super.dao.findObject(AppUserT.class, sql, parameterList);
		return obj == null ? null : (AppUserT) obj;
	}

	/**
	 * 获取客户端用户
	 *
	 * @param accountName 账号名称
	 * @param accountPass 账号密码
	 * @return 客户端用户
	 */
	public AppUserT findClientUser(String accountName, String accountPass) throws Exception {
		String sql =
				"SELECT au.* FROM `app_user` au LEFT JOIN app_account aa ON au.APP_USER_ID_ = aa.APP_USER_ID_ WHERE "
						+ " aa.ACCOUNT_NAME_ = ? AND aa.PASSWORD_ = ? AND au.APP_USER_TYPE_ = ? AND au.STATE_ = ?'";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(accountName);
		parameterList.add(accountPass);
		parameterList.add(IbmTypeEnum.CLIENT.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return (AppUserT) super.dao.findObject(AppUserT.class, sql, parameterList);
	}

	/**
	 * 根据用户id获取用户类型
	 *
	 * @param userId 用户id
	 * @return 用户类型
	 */
	public String findType(String userId) throws SQLException {
		String sql = "SELECT APP_USER_TYPE_ FROM APP_USER  where APP_USER_ID_ = ? and state_ !='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		return super.dao.findString("APP_USER_TYPE_", sql, parameterList);
	}

	/**
	 * 获取用户主页信息
	 *
	 * @param userId 用户id
	 * @return 用户主页信息
	 */
	public Map<String, Object> findHomeInfo(String userId) throws SQLException {
		String sql = "SELECT au.NICKNAME_, ip.USEABLE_POINT_T_ as POINT_T_, iat.END_TIME_LONG_ FROM `app_user` au "
				+ " LEFT JOIN ibm_point ip ON au.APP_USER_ID_ = ip.APP_USER_ID_ AND ip.state_ != 'DEL' "
				+ " LEFT JOIN ibm_available_time iat ON au.APP_USER_ID_ = iat.APP_USER_ID_ AND iat.state_ != 'DEL' "
				+ " WHERE au.APP_USER_ID_ = ? AND au.state_ != 'DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		Map<String, Object> map = super.dao.findMap(sql, parameterList);
		if (ContainerTool.notEmpty(map)) {
			map.put("POINT_", map.get("POINT_T_") != null ? NumberTool.doubleT(map.get("POINT_T_")) : 0);
			map.remove("POINT_T_");
		}
		return map;

	}

	/**
	 * @param userType 用户类型
	 * @return 用户信息集合
	 * @Description: 通过用户类型获取用户信息
	 */
	public List<Map<String, Object>> listByAppUserType(String userType) throws SQLException {
		List<Object> parameterList = new ArrayList<>(1);
		String sql = "select APP_USER_ID_,APP_USER_NAME_ from app_user where APP_USER_TYPE_ = ? ";
		parameterList.add(userType);
		return dao.findMapList(sql, parameterList);
	}

	/**
	 * @param userType 用户类型
	 * @return 用户数
	 * @Description: 通过用户类型获取用户数
	 */
	public String findByType(String userType) throws SQLException {
		List<Object> parameterList = new ArrayList<>(1);
		String sql = "select count(APP_USER_TYPE_) from app_user where APP_USER_TYPE_ = ? ";
		parameterList.add(userType);
		return dao.findString("count(APP_USER_TYPE_)", sql, parameterList);
	}

	/**
	 * @param userId 用户ID
	 * @return 该用户可开启的盘口ID
	 * @Description: 通过用户ID获取可开启的盘口ID
	 */
	public String findHandicapById(String userId) throws SQLException {
		String sql = "SELECT r.HANDICAP_ID_ from app_user u LEFT JOIN ibm_role r ON u.APP_USER_TYPE_ = r.ROLE_CODE_ where u.APP_USER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		return dao.findString("HANDICAP_ID_", sql, parameterList);
	}

	/**
	 * @param userId 用户ID
	 * @return 该用户可开启的盘口ID
	 * @Description: 通过用户ID获取可开启的方案ID
	 */
	public String findPlanById(String userId) throws SQLException {
		String sql = "SELECT r.PLAN_ID_ from app_user u LEFT JOIN ibm_role r ON u.APP_USER_TYPE_ = r.ROLE_CODE_ where u.APP_USER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		return dao.findString("PLAN_ID_", sql, parameterList);
	}

	/**
	 * @param userId 用户ID
	 * @return 最大在线会员数
	 * @Description: 通过用户ID获取最大在线会员数
	 * <p>
	 * 参数说明
	 */
	public String findOnlineNum(String userId) throws SQLException {
		String sql = "SELECT r.ONLINE_NUMBER_MAX_ from app_user u LEFT JOIN ibm_role r ON u.APP_USER_TYPE_ = r.ROLE_CODE_ where u.APP_USER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(userId);
		return dao.findString("ONLINE_NUMBER_MAX_", sql, parameterList);
	}

	/**
	 * @param roleCode 角色CODE
	 * @param userId   用户ID
	 * @return 修改的行数
	 * @Description: 修改用户组
	 * <p>
	 * 参数说明
	 */
	public int updateUserType(String roleCode, String userId) throws SQLException {
		String sql = "update app_user set APP_USER_TYPE_ = ? where APP_USER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(roleCode);
		parameterList.add(userId);
		return super.dao.execute(sql, parameterList);
	}

	/**
	 * @return 用户类型
	 * 返回类型 List<String>
	 * @Description: 查询用户类型
	 * <p>
	 * 参数说明
	 */
	public List<String> findType() throws SQLException {
		String sql = "select APP_USER_TYPE_ from app_user GROUP BY APP_USER_TYPE_ ";
		return super.dao.findStringList("APP_USER_TYPE_", sql, null);
	}

	/**
	 * 分页
	 *
	 * @param userName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public PageCoreBean find(String userName, Integer pageIndex, Integer pageSize) throws SQLException {
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append("SELECT count(*) FROM app_user where state_!='DEL' ");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM app_user where state_!='DEL' ");
		ArrayList<Object> parameters = null;
		ArrayList<Object> parametersCount = null;
		if (StringTool.notEmpty(userName)) {
			parameters = new ArrayList<>();
			parametersCount = new ArrayList<>();
			parameters.add("%" + userName + "%");
			parametersCount.add("%" + userName + "%");
			sql.append("and APP_USER_NAME_ like ? ");
			sqlCount.append("and APP_USER_NAME_ like ? ");
		}
		sql.append("order by UPDATE_TIME_ desc");
		return dao.page(sql.toString(), parameters, pageIndex, pageSize, sqlCount.toString(), parametersCount);
	}

	/**
	 * 逻辑删除用户
	 *
	 * @param id 用户ID
	 * @throws Exception
	 */
	public void delUser(String id) throws Exception {
		this.del(id);
	}

	/**
	 * 逻辑删除选中用户
	 *
	 * @param ids 用户ID集合
	 * @throws Exception
	 */
	public void delAllUser(String[] ids) throws Exception {
		this.delAll(ids);
		AppAccountService accountService = new AppAccountService();
		accountService.delAllByUserId(ids);
		AppTokenService tokenService = new AppTokenService();
		tokenService.delAllByUserId(ids);
	}

	/**
	 * @return 用户ID
	 * @Description: 查询所有用户ID
	 * <p>
	 * 参数说明
	 */
	public List<String> findAllId() throws SQLException {
		String sql = "SELECT APP_USER_ID_ FROM app_user  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findStringList("APP_USER_ID_", sql, null);
	}

	/**
	 * @return 用户ID
	 * @Description: 查询免费用户ID
	 * <p>
	 * 参数说明
	 */
	public List<String> findFreeId() throws SQLException {
		String sql = "SELECT APP_USER_ID_ FROM app_user  where state_!= ? AND APP_USER_TYPE_ = ? order by UPDATE_TIME_ desc";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmTypeEnum.FREE.name());
		return super.dao.findStringList("APP_USER_ID_", sql, parameterList);
	}

	/**
	 * 查询AppUser(通过账户名称)
	 *
	 * @return
	 * @throws Exception
	 */
	public AppUserT findAppUserByAccountName(String accountName) throws Exception {
		List<Object> parameterList = new ArrayList<>();
		String sql = "SELECT * FROM app_user u " + "LEFT JOIN app_account a ON u.APP_USER_ID_ = a.APP_USER_ID_ "
				+ "WHERE a.ACCOUNT_NAME_ = ? AND a.STATE_ != ? ";
		parameterList.add(accountName);
		parameterList.add(IbmStateEnum.DEL.name());
		return (AppUserT) this.dao.findObject(AppUserT.class, sql, parameterList);
	}
	/**
	 * 添加
	 *
	 * @param userT
	 * @return
	 * @throws Exception
	 */
	public String save(AppUserT userT) throws Exception {
		return dao.save(userT);
	}

	/**
	 * 根据类型查找用户id
	 *
	 * @param type 类型
	 * @return 用户id
	 */
	public List<String> listIdByType(String type) throws SQLException {
		List<Object> parameterList = new ArrayList<>(1);
		String sql = "select APP_USER_ID_ from app_user where APP_USER_TYPE_ = ? ";
		parameterList.add(type);
		return dao.findStringList("APP_USER_ID_", sql, parameterList);
	}
}
