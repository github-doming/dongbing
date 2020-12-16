package com.ibm.follow.servlet.cloud.ibm_cms_remind.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.entity.IbmCmsRemind;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * IBM消息提醒 服务类
 *
 * @author Robot
 */
public class IbmCmsRemindService extends BaseServicePlus {

	/**
	 * 保存IBM消息提醒 对象数据
	 *
	 * @param entity IbmCmsRemind对象数据
	 */
	public String save(IbmCmsRemind entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_cms_remind 的 IBM_CMS_REMIND_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cms_remind set state_='DEL' where IBM_CMS_REMIND_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CMS_REMIND_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_cms_remind 的 IBM_CMS_REMIND_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_cms_remind set state_='DEL' where IBM_CMS_REMIND_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_cms_remind  的 IBM_CMS_REMIND_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cms_remind where IBM_CMS_REMIND_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CMS_REMIND_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cms_remind 的 IBM_CMS_REMIND_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_cms_remind where IBM_CMS_REMIND_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCmsRemind实体信息
	 *
	 * @param entity IBM消息提醒 实体
	 */
	public void update(IbmCmsRemind entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cms_remind表主键查找 IBM消息提醒 实体
	 *
	 * @param id ibm_cms_remind 主键
	 * @return IBM消息提醒 实体
	 */
	public IbmCmsRemind find(String id) throws Exception {
		return (IbmCmsRemind) this.dao.find(IbmCmsRemind.class, id);

	}

	/**
	 * 查询用户提醒消息
	 *
	 * @param userId 用户ID
	 * @return 消息主键
	 */
	public String findCmsNotifyId(String userId) throws Exception {
		String sql = "SELECT CMS_NOTIFY_ID_ FROM ibm_cms_remind WHERE USER_ID_ = ? and STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findString("CMS_NOTIFY_ID_", sql, parameters);
	}

	/**
	 * 更新消息
	 *
	 * @param cmsNotifyId 消息主键
	 * @param date        时间
	 */
	public int updateRemind(String cmsNotifyId, Date date) throws SQLException {
		String sql = "UPDATE `ibm_cms_remind` SET IS_CANCEL_= ?,`UPDATE_TIME_`=?,`UPDATE_TIME_LONG_`=?  WHERE `CMS_NOTIFY_ID_`=? AND STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(true);
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(cmsNotifyId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.execute(sql, parameters);
	}
}
