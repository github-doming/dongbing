package com.ibs.plan.module.cloud.ibsp_cms_remind.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_cms_remind.entity.IbspCmsRemind;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* IBSP消息提醒 服务类
 * @author Robot
 */
public class IbspCmsRemindService extends BaseServiceProxy {

	/**
	 * 保存IBSP消息提醒 对象数据
	 * @param entity IbspCmsRemind对象数据
	 */
	public String save(IbspCmsRemind entity) throws Exception {
		return super.dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_cms_remind 的 IBSP_CMS_REMIND_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_cms_remind set state_='DEL' where IBSP_CMS_REMIND_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_CMS_REMIND_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_cms_remind 的 IBSP_CMS_REMIND_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_cms_remind set state_='DEL' where IBSP_CMS_REMIND_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_cms_remind  的 IBSP_CMS_REMIND_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_cms_remind where IBSP_CMS_REMIND_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_CMS_REMIND_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_cms_remind 的 IBSP_CMS_REMIND_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_cms_remind where IBSP_CMS_REMIND_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspCmsRemind实体信息
	 * @param entity IBSP消息提醒 实体
	 */
	public void update(IbspCmsRemind entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_cms_remind表主键查找 IBSP消息提醒 实体
	 * @param id ibsp_cms_remind 主键
	 * @return IBSP消息提醒 实体
	 */
	public IbspCmsRemind find(String id) throws Exception {
		return dao.find(IbspCmsRemind.class,id);
	}
	/**
	 * 查询用户提醒消息
	 *
	 * @param appUserId 用户ID
	 * @return 消息主键
	 */
	public String findCmsNotifyId(String appUserId) throws SQLException {
		String sql = "SELECT CMS_NOTIFY_ID_ FROM ibsp_cms_remind WHERE USER_ID_ = ? and STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(appUserId);
		parameters.add(StateEnum.DEL.name());
		return super.dao.findString("CMS_NOTIFY_ID_", sql, parameters);
	}

	/**
	 * 更新消息
	 *
	 * @param cmsNotifyId 消息主键
	 * @param date        时间
	 */
	public int updateRemind(String cmsNotifyId, Date date) throws SQLException {
		String sql = "UPDATE `ibsp_cms_remind` SET IS_CANCEL_= ?,`UPDATE_TIME_`=?,`UPDATE_TIME_LONG_`=?  WHERE `CMS_NOTIFY_ID_`=? AND STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(true);
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(cmsNotifyId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.dao.execute(sql, parameters);
	}
}
