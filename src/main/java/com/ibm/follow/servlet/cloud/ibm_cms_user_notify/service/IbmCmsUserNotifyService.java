package com.ibm.follow.servlet.cloud.ibm_cms_user_notify.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.entity.IbmCmsUserNotify;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * IBM用户消息列表 服务类
 *
 * @author Robot
 */
public class IbmCmsUserNotifyService extends BaseServicePlus {

	/**
	 * 保存IBM用户消息列表 对象数据
	 *
	 * @param entity IbmCmsUserNotify对象数据
	 */
	public String save(IbmCmsUserNotify entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_cms_user_notify 的 IBM_CMS_USER_NOTIFY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cms_user_notify set state_='DEL' where IBM_CMS_USER_NOTIFY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CMS_USER_NOTIFY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_cms_user_notify 的 IBM_CMS_USER_NOTIFY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_cms_user_notify set state_='DEL' where IBM_CMS_USER_NOTIFY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_cms_user_notify  的 IBM_CMS_USER_NOTIFY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cms_user_notify where IBM_CMS_USER_NOTIFY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CMS_USER_NOTIFY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cms_user_notify 的 IBM_CMS_USER_NOTIFY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_cms_user_notify where IBM_CMS_USER_NOTIFY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCmsUserNotify实体信息
	 *
	 * @param entity IBM用户消息列表 实体
	 */
	public void update(IbmCmsUserNotify entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cms_user_notify表主键查找 IBM用户消息列表 实体
	 *
	 * @param id ibm_cms_user_notify 主键
	 * @return IBM用户消息列表 实体
	 */
	public IbmCmsUserNotify find(String id) throws Exception {
		return (IbmCmsUserNotify) this.dao.find(IbmCmsUserNotify.class, id);

	}

	/**
	 * 获取用户未读消息条数
	 *
	 * @param userId 用户ID
	 * @return 条数
	 */
	public int getUnreadMsgByUserId(String userId) throws SQLException {
		String sql = "SELECT COUNT(*) count FROM `ibm_cms_user_notify` WHERE USER_ID_ = ? and IS_READ_ = ? AND STATE_ = ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(0);
		parameters.add(IbmStateEnum.OPEN.name());
		return Integer.parseInt(dao.findString("count", sql, parameters));

	}

	/**
	 * 更新一条未读消息
	 *
	 * @param cmsNotifyId 消息主键
	 * @param date        日期
	 */
	public void updateReadState(String cmsNotifyId, Date date) throws SQLException {
		String sql = "UPDATE `ibm_cms_user_notify` SET `IS_READ_`= ? ,`UPDATE_TIME_`= ?, `UPDATE_TIME_LONG_`= ?  WHERE `CMS_NOTIFY_ID_`= ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(1);
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(cmsNotifyId);
		dao.execute(sql, parameters);
	}

	/**
	 * 阅读所有未读的用户消息
	 *
	 * @param userId 用户Id
	 * @param date   时间
	 */
	public void updateReadAll(String userId, Date date) throws SQLException {
		String sql = "UPDATE `ibm_cms_user_notify` SET `IS_READ_`= ? ,`UPDATE_TIME_`= ?, `UPDATE_TIME_LONG_`= ?  WHERE `USER_ID_`= ? AND `IS_READ_` = ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(true);
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(userId);
		parameters.add(false);
		dao.execute(sql, parameters);
	}

	/**
	 * 删除一条消息
	 *
	 * @param cmsNotifyId 主键Id
	 * @param date        时间
	 */
	public void delUserMsg(String cmsNotifyId, Date date) throws SQLException {
		String sql = "UPDATE `ibm_cms_user_notify` SET `STATE_`= ? ,`UPDATE_TIME_`= ?, `UPDATE_TIME_LONG_`= ?  WHERE `CMS_NOTIFY_ID_`= ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(cmsNotifyId);
		dao.execute(sql, parameters);
	}

}
