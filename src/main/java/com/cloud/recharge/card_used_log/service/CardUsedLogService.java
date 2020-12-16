package com.cloud.recharge.card_used_log.service;

import com.cloud.recharge.card_used_log.entity.CardUsedLog;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 充值卡使用日志 服务类
 *
 * @author Robot
 */
public class CardUsedLogService extends BaseServiceProxy {

	/**
	 * 保存充值卡使用日志 对象数据
	 *
	 * @param entity CardUsedLog对象数据
	 */
	public String save(CardUsedLog entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除card_used_log 的 CARD_USED_LOG_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update card_used_log set state_='DEL' where CARD_USED_LOG_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CARD_USED_LOG_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 card_used_log 的 CARD_USED_LOG_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update card_used_log set state_='DEL' where CARD_USED_LOG_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 card_used_log  的 CARD_USED_LOG_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from card_used_log where CARD_USED_LOG_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CARD_USED_LOG_ID_主键id数组的数据
	 *
	 * @param idArray 要删除card_used_log 的 CARD_USED_LOG_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from card_used_log where CARD_USED_LOG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CardUsedLog实体信息
	 *
	 * @param entity 充值卡使用日志 实体
	 */
	public void update(CardUsedLog entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据card_used_log表主键查找 充值卡使用日志 实体
	 *
	 * @param id card_used_log 主键
	 * @return 充值卡使用日志 实体
	 */
	public CardUsedLog find(String id) throws Exception {
		return dao.find(CardUsedLog.class, id);
	}
	/**
	 * 保存使用信息
	 *
	 * @param usedUserId  使用者主键
	 * @param adminUserId 卡拥有者主键
	 * @param cardId      卡主键
	 * @param nowTime     使用时间
	 */
	public void save(String usedUserId, String adminUserId, String cardId, Date nowTime) throws Exception {
		CardUsedLog usedLog = new CardUsedLog();
		usedLog.setCardRechargeId(cardId);
		usedLog.setUsedUserId(usedUserId);
		usedLog.setAdminUserId(adminUserId);
		usedLog.setCreateTime(nowTime);
		usedLog.setCreateTimeLong(System.currentTimeMillis());
		usedLog.setUpdateTimeLong(System.currentTimeMillis());
		usedLog.setState(StateEnum.OPEN.name());
		save(usedLog);
	}
}
