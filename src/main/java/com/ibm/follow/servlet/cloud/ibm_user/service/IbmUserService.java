package com.ibm.follow.servlet.cloud.ibm_user.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_user.entity.IbmUser;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 投注用户表 服务类
 *
 * @author Robot
 */
public class IbmUserService extends BaseServiceProxy {

	/**
	 * 保存投注用户表 对象数据
	 *
	 * @param entity IbmUser对象数据
	 */
	public String save(IbmUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_user 的 IBM_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_user set state_='DEL' where IBM_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBM_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_user 的 IBM_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_user set state_='DEL' where IBM_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_user  的 IBM_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_user where IBM_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBM_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_user 的 IBM_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_user where IBM_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmUser实体信息
	 *
	 * @param entity 投注用户表 实体
	 */
	public void update(IbmUser entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_user表主键查找 投注用户表 实体
	 *
	 * @param id ibm_user 主键
	 * @return 投注用户表 实体
	 */
	public IbmUser find(String id) throws Exception {
		return dao.find(IbmUser.class, id);
	}

	/**
	 * 查询是否存在
	 *
	 * @param userId 用户主键
	 * @return 存在 true
	 */
	public boolean findExist(String userId) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ from  ibm_user where " +
				" APP_USER_ID_ = ? and USER_TYPE_ != ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(IbmTypeEnum.USER.name());
		parameterList.add(StateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.findString(sql, parameterList));
	}

	/**
	 * 查询是否存在
	 *
	 * @param userId 用户主键
	 * @return 存在 true
	 */
	public Map<String,Object> userInfo(String userId) throws SQLException {
		String sql = "SELECT NICKNAME_,USER_TYPE_,imt.END_TIME_LONG_ from ibm_user iu left join ibm_manage_time imt"
				+ " on iu.APP_USER_ID_=imt.APP_USER_ID_ where iu.APP_USER_ID_ = ?  and iu.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(StateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 查询是否存在
	 *
	 * @param userId 用户主键
	 * @return 存在 true
	 */
	public boolean findExist(String userId, IbmTypeEnum userType) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ from  ibm_user where " +
				" APP_USER_ID_ = ? and USER_TYPE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(userType.name());
		parameterList.add(StateEnum.OPEN.name());
		return ContainerTool.notEmpty(super.findString(sql, parameterList));
	}

	public IbmUser findUserByToken(String token) throws Exception {
		String sql = "SELECT iu.* FROM ibm_user iu LEFT JOIN ibm_user_token iut on " +
				" iu.APP_USER_ID_ = iut.APP_USER_ID_ and iut.STATE_ = ? where iut.VALUE_ = ? and iu.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(token);
		parameterList.add(StateEnum.OPEN.name());
		return super.dao.findObject(IbmUser.class, sql, parameterList);
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
		String sqlCount = "SELECT COUNT(*) FROM ibm_user ";
		String sql = "SELECT NICKNAME_,APP_USER_ID_,CREATE_TIME_LONG_  FROM ibm_user  ";
		ArrayList<Object> parameterList = new ArrayList<>();
		String sqlPlus = " WHERE USER_TYPE_ = ? AND state_ != ? ";
		parameterList.add(IbmTypeEnum.USER.name());
		parameterList.add(IbmStateEnum.DEL.name());

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
	 * 获取用户主页信息
	 *
	 * @param userId 用户id
	 * @return 用户主页信息
	 */
	public Map<String, Object> findHomeInfo(String userId) throws SQLException {
		String sql = "SELECT au.NICKNAME_, it.END_TIME_LONG_ FROM `ibm_user` au "
				+ " LEFT JOIN ibm_manage_time it ON au.APP_USER_ID_ = it.APP_USER_ID_ "
				+ " WHERE au.APP_USER_ID_ = ?  AND it.state_ != ? AND au.state_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);

	}

}
