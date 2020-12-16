package com.ibs.plan.module.cloud.ibsp_cms_user_notify.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.entity.IbspCmsUserNotify;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* IBSP用户消息列表 服务类
 * @author Robot
 */
public class IbspCmsUserNotifyService extends BaseServiceProxy {

	/**
	 * 保存IBSP用户消息列表 对象数据
	 * @param entity IbspCmsUserNotify对象数据
	 */
	public String save(IbspCmsUserNotify entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_cms_user_notify 的 IBSP_CMS_USER_NOTIFY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_cms_user_notify set state_='DEL' where IBSP_CMS_USER_NOTIFY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_CMS_USER_NOTIFY_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_cms_user_notify 的 IBSP_CMS_USER_NOTIFY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_cms_user_notify set state_='DEL' where IBSP_CMS_USER_NOTIFY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_cms_user_notify  的 IBSP_CMS_USER_NOTIFY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_cms_user_notify where IBSP_CMS_USER_NOTIFY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_CMS_USER_NOTIFY_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_cms_user_notify 的 IBSP_CMS_USER_NOTIFY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_cms_user_notify where IBSP_CMS_USER_NOTIFY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspCmsUserNotify实体信息
	 * @param entity IBSP用户消息列表 实体
	 */
	public void update(IbspCmsUserNotify entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_cms_user_notify表主键查找 IBSP用户消息列表 实体
	 * @param id ibsp_cms_user_notify 主键
	 * @return IBSP用户消息列表 实体
	 */
	public IbspCmsUserNotify find(String id) throws Exception {
		return dao.find(IbspCmsUserNotify.class,id);
	}

	/**
	 * 获取用户未读消息条数
	 *
	 * @param appUserId 用户ID
	 * @return 条数
	 */
	public Object getUnreadMsgByUserId(String appUserId) throws SQLException {
		String sql = "SELECT COUNT(*) count FROM `ibsp_cms_user_notify` WHERE USER_ID_ = ? and IS_READ_ = ? AND STATE_ = ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(appUserId);
		parameters.add(0);
		parameters.add(IbsStateEnum.OPEN.name());
		return Integer.parseInt(dao.findString("count", sql, parameters));
	}

	/**
	 * 更新一条未读消息
	 *
	 * @param cmsNotifyId 消息主键
	 * @param date        日期
	 */
	public void updateReadState(String cmsNotifyId, Date date) throws SQLException {
		String sql = "UPDATE `ibsp_cms_user_notify` SET `IS_READ_`= ? ,`UPDATE_TIME_`= ?, `UPDATE_TIME_LONG_`= ?  WHERE `CMS_NOTIFY_ID_`= ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(true);
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
		String sql = "UPDATE `ibsp_cms_user_notify` SET `IS_READ_`= ? ,`UPDATE_TIME_`= ?, `UPDATE_TIME_LONG_`= ?  WHERE `USER_ID_`= ? AND `IS_READ_` = ? ";
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
		String sql = "UPDATE `ibsp_cms_user_notify` SET `STATE_`= ? ,`UPDATE_TIME_`= ?, `UPDATE_TIME_LONG_`= ?  WHERE `CMS_NOTIFY_ID_`= ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(cmsNotifyId);
		dao.execute(sql, parameters);
	}

}
