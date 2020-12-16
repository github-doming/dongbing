package com.cloud.user.user_login_log.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.cloud.user.user_login_log.entity.UserLoginLog;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 用户登录日志 服务类
 * @author Robot
 */
public class UserLoginLogService extends BaseServiceProxy {

	/**
	 * 保存用户登录日志 对象数据
	 * @param entity UserLoginLog对象数据
	 */
	public String save(UserLoginLog entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除user_login_log 的 LOGIN_LOG_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update user_login_log set state_='DEL' where LOGIN_LOG_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除LOGIN_LOG_ID_主键id数组的数据
	 * @param idArray 要删除 user_login_log 的 LOGIN_LOG_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update user_login_log set state_='DEL' where LOGIN_LOG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 user_login_log  的 LOGIN_LOG_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from user_login_log where LOGIN_LOG_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除LOGIN_LOG_ID_主键id数组的数据
	 * @param idArray 要删除user_login_log 的 LOGIN_LOG_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from user_login_log where LOGIN_LOG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新UserLoginLog实体信息
	 * @param entity 用户登录日志 实体
	 */
	public void update(UserLoginLog entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据user_login_log表主键查找 用户登录日志 实体
	 * @param id user_login_log 主键
	 * @return 用户登录日志 实体
	 */
	public UserLoginLog find(String id) throws Exception {
		return dao.find(UserLoginLog.class,id);
	}

	/**
	 * 获取登录日志信息
	 * @param userPath		用戶路徑
	 * @param channelType		平台类型
	 * @param pageIndex		页数
	 * @param pageSize		条数
	 * @return
	 */
	public PageCoreBean<Map<String, Object>> listLog(String userPath,String channelType,Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "SELECT count(*) from (";
		String sql = "SELECT LOGIN_LOG_ID_ ID_,USER_NAME_,IP_,CHANNEL_TYPE_,OPERT_CONTENT_,CREATE_TIME_LONG_ FROM `user_login_log` " +
				"WHERE STATE_ !=? AND USER_PATH_ like ? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(StateEnum.DEL.name());
		parameters.add(userPath.concat("%"));
		if(StringTool.notEmpty(channelType)){
			sql+=" and CHANNEL_TYPE_=? ";
			parameters.add(channelType);
		}
		sql+=" ORDER BY LOGIN_LOG_ID_ DESC";
		sqlCount = sqlCount + sql + ") AS t  ";
		return dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}
}
