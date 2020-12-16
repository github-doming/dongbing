package com.ibs.plan.module.cloud.ibsp_cms_announce.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_cms_announce.entity.IbspCmsAnnounce;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* IBSP消息公告 服务类
 * @author Robot
 */
public class IbspCmsAnnounceService extends BaseServiceProxy {

	/**
	 * 保存IBSP消息公告 对象数据
	 * @param entity IbspCmsAnnounce对象数据
	 */
	public String save(IbspCmsAnnounce entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_cms_announce 的 IBSP_CMS_ANNOUNCE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_cms_announce set state_='DEL' where IBSP_CMS_ANNOUNCE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_CMS_ANNOUNCE_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_cms_announce 的 IBSP_CMS_ANNOUNCE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_cms_announce set state_='DEL' where IBSP_CMS_ANNOUNCE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_cms_announce  的 IBSP_CMS_ANNOUNCE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_cms_announce where IBSP_CMS_ANNOUNCE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_CMS_ANNOUNCE_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_cms_announce 的 IBSP_CMS_ANNOUNCE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_cms_announce where IBSP_CMS_ANNOUNCE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspCmsAnnounce实体信息
	 * @param entity IBSP消息公告 实体
	 */
	public void update(IbspCmsAnnounce entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_cms_announce表主键查找 IBSP消息公告 实体
	 * @param id ibsp_cms_announce 主键
	 * @return IBSP消息公告 实体
	 */
	public IbspCmsAnnounce find(String id) throws Exception {
		return dao.find(IbspCmsAnnounce.class,id);
	}

	/**
	 * 更新消息
	 * @param cmsNotifyId 消息主键
	 * @param channel 平台
	 * @param cancelTimeLong 过期时间
	 * @param state 状态
	 * @param date 时间
	 */
	public int updateAnnounce(String cmsNotifyId,String channel,long cancelTimeLong, String state, Date date) throws SQLException {
		String sql = "UPDATE `ibsp_cms_announce` SET ANNOUNCE_CHANNEL_=?,CANCEL_TIME_LONG_=?,`IS_CANCEL_`=? ,`UPDATE_TIME_`=?, `UPDATE_TIME_LONG_`=?,STATE_=?  WHERE `CMS_NOTIFY_ID_`=? AND STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(channel);
		parameters.add(cancelTimeLong);
		parameters.add(IbsStateEnum.CANCEL.name().equals(state));
		parameters.add(date);
		parameters.add(System.currentTimeMillis());
		parameters.add(state);
		parameters.add(cmsNotifyId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.dao.execute(sql, parameters);
	}
}
