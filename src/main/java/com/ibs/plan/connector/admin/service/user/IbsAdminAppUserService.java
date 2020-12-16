package com.ibs.plan.connector.admin.service.user;

import all.app.common.service.AppUserService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-08 17:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbsAdminAppUserService extends AppUserService {

	/**
	 * 根据管理者token 和 所属类型 获取管理用户
	 *
	 * @param token  通讯token
	 * @param type 用户所属类型
	 * @return 管理用户
	 */
	public AdminUser find(String token, String tenant, String type) throws Exception {
		String sql;
		List<Object> parameterList = new ArrayList<>(4);
		if (IbsTypeEnum.ADMIN.equal(type)) {
			sql = "SELECT au.APP_USER_ID_ as USER_ID_,au.APP_USER_NAME_ as USER_NAME_,au.APP_USER_TYPE_ as USER_TYPE_  "
					+ " FROM `app_user` au  LEFT JOIN app_token at on `at`.APP_USER_ID_ = au.APP_USER_ID_ where "
					+ " au.TENANT_CODE_ = ? and au.APP_USER_TYPE_ = ? and `at`.VALUE_ = ? and `at`.STATE_ = ? and au.STATE_ = ?";
			parameterList.add(tenant);
			parameterList.add(type);
		} else if (IbsTypeEnum.SYS.equal(type)) {
			sql = "SELECT su.SYS_USER_ID_ as USER_ID_,su.SYS_USER_NAME_ as USER_NAME_,'SYS' as USER_TYPE_ FROM `sys_user` su "
					+ " LEFT JOIN sys_sso ss on `ss`.SYS_USER_ID_ = su.SYS_USER_ID_ where "
					+ " `ss`.VALUE_ = ? and `ss`.STATE_ = ? and  su.STATE_ = ?";
		} else {
			log.trace("不存在的请求平台类型".concat(type));
			return null;
		}
		parameterList.add(token);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(AdminUser.class, sql, parameterList);
	}

	/**
	 * 根据账户名称获取 用户实体类
	 *
	 * @param accountName 账户名称
	 * @return 用户实体类
	 */
	public AppUserT findByAccountName(String accountName) throws Exception {
		String sql = "SELECT * FROM app_user u  LEFT JOIN app_account a ON u.APP_USER_ID_ = a.APP_USER_ID_ "
				+ "WHERE a.ACCOUNT_NAME_ = ? AND a.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(accountName);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findObject(AppUserT.class, sql, parameterList);
	}

	/**
	 * 获取分页信息
	 *
	 * @param pageIndex 页面索引
	 * @param pageSize  页面大小
	 * @return 分页信息
	 */
	public PageCoreBean<Map<String, Object>> listShow(String userName,  long startTime, long endTime, Integer pageIndex, Integer pageSize)
			throws SQLException {
		String sqlCount = "SELECT COUNT(*) FROM ibsp_user iu ";
		String sql = "SELECT NICKNAME_,APP_USER_ID_,CREATE_TIME_LONG_  FROM ibsp_user  WHERE USER_TYPE_ = ? AND state_ != ? ";
		ArrayList<Object> parameterList = new ArrayList<>();
		String sqlPlus = "";
		parameterList.add(IbsTypeEnum.USER.name());
		parameterList.add(IbsStateEnum.DEL.name());

		if (startTime > 0) {
			sqlPlus +=" and LOGIN_TIME_LONG_ > ?";
			parameterList.add(startTime);
		}
		if (endTime > 0) {
			sqlPlus +=" and LOGIN_TIME_LONG_ < ?";
			parameterList.add(endTime);
		}
		if (StringTool.notEmpty(userName)) {
			sqlPlus +=" AND NICKNAME_ LIKE ? ";
			userName = "%" + userName + "%";
			parameterList.add(userName);
		}
		sqlPlus +=" ORDER BY CREATE_TIME_LONG_ DESC";
		sqlCount += sqlPlus;
		sql += sqlPlus;
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}


	/**
	 * 获取用户最新一条记录
	 *
	 * @param userId 用户Id
	 */
	public Map<String, Object> findUserLastInfo(String userId) throws SQLException {
		String sql = "select IP_,STATE_ from app_token where APP_USER_ID_ = ? AND STATE_!=? " +
				"order by UPDATE_TIME_LONG_ DESC LIMIT 1";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}



	/**
	 * 根据用户名查找用户Id
	 *
	 * @param userName 用户名
	 */
	public String findUserIdByUserName(String userName) throws SQLException {
		String sql = "SELECT APP_USER_ID_ FROM `app_user` WHERE APP_USER_NAME_ = ? AND APP_USER_TYPE_= ? AND STATE_ !=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userName);
		parameterList.add(IbsTypeEnum.USER.name());
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findString("APP_USER_ID_", sql, parameterList);
	}

	/**
	 * 删除某个用户的所有盘口信息
	 *
	 * @param appUserId 用户主键
	 * @param nowTime   删除时间
	 * @param desc      描述
	 */
	public void delByUserId(String appUserId, Date nowTime, String desc) throws SQLException {

		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(IbsStateEnum.DEL.name());


		String sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
				+ " HANDICAP_MEMBER_ID_ IN (SELECT IBSP_HANDICAP_MEMBER_ID_ FROM ibsp_handicap_member WHERE "
				+ " APP_USER_ID_ = ? AND STATE_ != ?) and STATE_ != ?";
		//会员
		super.dao.execute(String.format(sqlFormat, "ibsp_hm_bet_item"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_hm_set"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_hm_info"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_hm_game_set"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_hm_mode_cutover"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_period"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_period_vr"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_period_vr"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_vr"), parameterList);


		sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where %s = ? and STATE_ != ?";
		parameterList.remove(parameterList.size() - 1);
		//会员
		super.dao.execute(String.format(sqlFormat, "ibsp_hm_user", "APP_USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_plan_user", "APP_USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_plan", "APP_USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibsp_profit_plan_vr", "APP_USER_ID_"), parameterList);

	}

	/**
	 * 根据用户id逻辑删除
	 *
	 * @param appUserId 用户id
	 */
	public void delUserInfo(String appUserId, Date nowTime,String desc) throws SQLException {
		String sqlFormat = "update %s set state_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,DESC_=? WHERE APP_USER_ID_=? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		parameters.add(desc);
		parameters.add(appUserId);
		dao.execute(String.format(sqlFormat, "ibsp_user_token"), parameters);
		dao.execute(String.format(sqlFormat, "ibsp_user"), parameters);
		dao.execute(String.format(sqlFormat, "ibsp_sys_manage_point"), parameters);
		dao.execute(String.format(sqlFormat, "ibsp_sys_manage_time"), parameters);
		dao.execute(String.format(sqlFormat, "ibsp_exp_user"), parameters);
	}


}
