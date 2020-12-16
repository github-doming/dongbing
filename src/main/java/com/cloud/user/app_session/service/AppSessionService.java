package com.cloud.user.app_session.service;

import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.app_session.entity.AppSession;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * APP会话 服务类
 *
 * @author Robot
 */
public class AppSessionService extends BaseServiceProxy {

	/**
	 * 保存APP会话 对象数据
	 *
	 * @param entity AppSession对象数据
	 */
	public String save(AppSession entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除app_session 的 APP_SESSION_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update app_session set state_='DEL' where APP_SESSION_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_SESSION_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 app_session 的 APP_SESSION_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update app_session set state_='DEL' where APP_SESSION_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 app_session  的 APP_SESSION_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from app_session where APP_SESSION_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_SESSION_ID_主键id数组的数据
	 *
	 * @param idArray 要删除app_session 的 APP_SESSION_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from app_session where APP_SESSION_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新AppSession实体信息
	 *
	 * @param entity APP会话 实体
	 */
	public void update(AppSession entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据app_session表主键查找 APP会话 实体
	 *
	 * @param id app_session 主键
	 * @return APP会话 实体
	 */
	public AppSession find(String id) throws Exception {
		return dao.find(AppSession.class, id);
	}

	/**
	 * 创建一个新的会话
	 *
	 * @param channelType 会话类型
	 * @return 会话id
	 */
	public String newAppSessionByType(ChannelTypeEnum channelType) throws Exception {
		Date nowTime = new Date();
		String sessionId = RandomTool.getNumLetter32();
		AppSession session = new AppSession();
		session.setAppSessionId(sessionId);
		session.setType(channelType.name());
		session.setCreateTime(nowTime);
		session.setCreateTimeLong(System.currentTimeMillis());
		session.setUpdateTime(nowTime);
		session.setUpdateTimeLong(System.currentTimeMillis());
		session.setState(StateEnum.OPEN.name());
		save(session);
		return sessionId;
	}

	/**
	 * 查找会话是否存在
	 * @param sessionId 会话主键
	 * @param channelType 会话类型
	 * @return 存在 true
	 */
	public boolean findExist(String sessionId, ChannelTypeEnum channelType) throws SQLException {
		String sql = "select STATE_ from app_session where APP_SESSION_ID_ = ? and TYPE_ = ? and state_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(sessionId);
		parameterList.add(channelType.name());
		parameterList.add(StateEnum.OPEN.name());
		return ContainerTool.notEmpty(dao.findMap(sql, parameterList));
	}
}
