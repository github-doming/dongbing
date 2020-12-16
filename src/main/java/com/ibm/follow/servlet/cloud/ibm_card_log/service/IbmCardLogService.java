package com.ibm.follow.servlet.cloud.ibm_card_log.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.follow.servlet.cloud.ibm_card_log.entity.IbmCardLog;

import java.util.ArrayList;
import java.util.List;

/**
* IBM_充值卡日志 服务类
 * @author Robot
 */
public class IbmCardLogService extends BaseServicePlus {

	/**
	 * 保存IBM_充值卡日志 对象数据
	 * @param entity IbmCardLog对象数据
	 */
	public String save(IbmCardLog entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_card_log 的 IBM_CARD_LOG_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_card_log set state_='DEL' where IBM_CARD_LOG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CARD_LOG_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_card_log 的 IBM_CARD_LOG_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_card_log set state_='DEL' where IBM_CARD_LOG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_card_log  的 IBM_CARD_LOG_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_card_log where IBM_CARD_LOG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CARD_LOG_ID_主键id数组的数据
	 * @param idArray 要删除ibm_card_log 的 IBM_CARD_LOG_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_card_log where IBM_CARD_LOG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCardLog实体信息
	 * @param entity IBM_充值卡日志 实体
	 */
	public void update(IbmCardLog entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_card_log表主键查找 IBM_充值卡日志 实体
	 * @param id ibm_card_log 主键
	 * @return IBM_充值卡日志 实体
	 */
	public IbmCardLog find(String id) throws Exception {
		return (IbmCardLog) this.dao.find(IbmCardLog. class,id);

	}
}
