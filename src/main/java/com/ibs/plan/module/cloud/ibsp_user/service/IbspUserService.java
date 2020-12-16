package com.ibs.plan.module.cloud.ibsp_user.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 投注用户表 服务类
 *
 * @author Robot
 */
public class IbspUserService extends BaseServiceProxy {

	/**
	 * 保存投注用户表 对象数据
	 *
	 * @param entity IbspUser对象数据
	 */
	public String save(IbspUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_user 的 APP_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_user set state_='DEL' where APP_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_user 的 APP_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_user set state_='DEL' where APP_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_user  的 APP_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_user where APP_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_user 的 APP_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_user where APP_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspUser实体信息
	 *
	 * @param entity 投注用户表 实体
	 */
	public void update(IbspUser entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_user表主键查找 投注用户表 实体
	 *
	 * @param id ibsp_user 主键
	 * @return 投注用户表 实体
	 */
	public IbspUser find(String id) throws Exception {
		return dao.find(IbspUser.class, id);
	}

	/**
	 * 查询是否存在
	 *
	 * @param userId   用户主键
	 * @param userType 用户类型
	 * @return 存在 true
	 */
	public boolean findExist(String userId, IbsTypeEnum userType) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ from  ibsp_user where " +
				" APP_USER_ID_ = ? and USER_TYPE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(userType.name());
		parameterList.add(StateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.findString(sql, parameterList));
	}
	/**
	 * 查询是否存在管理用户，用户类型不等于user
	 *
	 * @param userId   用户主键
	 * @return 存在 true
	 */
	public boolean findExist(String userId) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ from  ibsp_user where " +
				" APP_USER_ID_ = ? and USER_TYPE_ != ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(IbsTypeEnum.USER.name());
		parameterList.add(StateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.findString(sql, parameterList));
	}

	public IbspUser findUserByToken(String token) throws Exception {
		String sql = "SELECT iu.* FROM ibsp_user iu LEFT JOIN ibsp_user_token iut on " +
				" iu.APP_USER_ID_ = iut.APP_USER_ID_ and iut.STATE_ = ? where iut.VALUE_ = ? and iu.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(token);
		parameterList.add(StateEnum.OPEN.name());
		return super.findObject(IbspUser.class, sql, parameterList);
	}

	/**
	 * 获取用户主页信息
	 *
	 * @param appUserId 用户id
	 * @return 用户主页信息
	 */
	public Map<String, Object> findHomeInfo(String appUserId) throws SQLException {
		String sql = "SELECT au.NICKNAME_,  it.END_TIME_LONG_ FROM `ibsp_user` au "
				+ " LEFT JOIN ibsp_sys_manage_time it ON au.APP_USER_ID_ = it.APP_USER_ID_ "
				+ " WHERE au.APP_USER_ID_ = ? AND it.state_ != ? AND au.state_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取分页信息
	 *
	 * @param pageIndex 页面索引
	 * @param pageSize  页面大小
	 * @return 分页信息
	 */
	public PageCoreBean<Map<String, Object>> listShow(String userName, long startTime, long endTime, Integer pageIndex, Integer pageSize)
			throws SQLException {
		String sqlCount = "SELECT COUNT(*) FROM ibsp_user ";
		String sql = "SELECT NICKNAME_,APP_USER_ID_,CREATE_TIME_LONG_  FROM ibsp_user  ";
		ArrayList<Object> parameterList = new ArrayList<>();
		String sqlPlus = " WHERE USER_TYPE_ = ? AND state_ != ? ";
		parameterList.add(IbsTypeEnum.USER.name());
		parameterList.add(IbsStateEnum.DEL.name());

		if (startTime > 0) {
			sqlPlus += " and LOGIN_TIME_LONG_ > ?";
			parameterList.add(startTime);
		}
		if (endTime > 0) {
			sqlPlus += " and LOGIN_TIME_LONG_ < ?";
			parameterList.add(endTime);
		}
		if (StringTool.notEmpty(userName)) {
			sqlPlus += " AND NICKNAME_ LIKE ? ";
			userName = "%" + userName + "%";
			parameterList.add(userName);
		}
		sqlPlus += " ORDER BY CREATE_TIME_LONG_ DESC";
		sqlCount += sqlPlus;
		sql += sqlPlus;
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 获取用户信息
	 *
	 * @param appUserId 用户id
	 * @return
	 */
	public Map<String, Object> findUserInfo(String appUserId) throws SQLException {
		String sql = "SELECT iu.NICKNAME_,imt.END_TIME_LONG_ FROM ibsp_user iu "
				+ " LEFT JOIN ibsp_sys_manage_time imt ON iu.APP_USER_ID_=imt.APP_USER_ID_ WHERE iu.APP_USER_ID_=?";
		ArrayList<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 根据用户名查找用户Id
	 *
	 * @param userName 用户名
	 */
	public String findUserIdByName(String userName) throws SQLException {
		String sql = "SELECT APP_USER_ID_ FROM `ibsp_user` WHERE NICKNAME_ = ? AND USER_TYPE_= ? AND STATE_ !=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userName);
		parameterList.add(IbsTypeEnum.USER.name());
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findString("APP_USER_ID_", sql, parameterList);
	}

	/**
	 * 修改用户角色
	 * @param userId	用户id
	 * @param role		信息
	 */
	public void updateUserRole(String userId, Map<String, Object> role) throws SQLException {
		String sql="update ibsp_user set USER_TYPE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where APP_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(role.get("APP_GROUP_CODE_"));
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(userId);
		super.execute(sql,parameterList);
	}
}
