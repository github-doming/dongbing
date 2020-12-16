package com.cloud.recharge.card_recharge.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.cloud.recharge.card_recharge.entity.CardRecharge;
import com.cloud.recharge.connector.core.CardTool;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 充值卡 服务类
 *
 * @author Robot
 */
public class CardRechargeService extends BaseServiceProxy {

	/**
	 * 保存充值卡 对象数据
	 *
	 * @param entity CardRecharge对象数据
	 */
	public String save(CardRecharge entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除card_recharge 的 CARD_RECHARGE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update card_recharge set state_='DEL' where CARD_RECHARGE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CARD_RECHARGE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 card_recharge 的 CARD_RECHARGE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update card_recharge set state_='DEL' where CARD_RECHARGE_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 card_recharge  的 CARD_RECHARGE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from card_recharge where CARD_RECHARGE_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CARD_RECHARGE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除card_recharge 的 CARD_RECHARGE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from card_recharge where CARD_RECHARGE_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CardRecharge实体信息
	 *
	 * @param entity 充值卡 实体
	 */
	public void update(CardRecharge entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据card_recharge表主键查找 充值卡 实体
	 *
	 * @param id card_recharge 主键
	 * @return 充值卡 实体
	 */
	public CardRecharge find(String id) throws Exception {
		return dao.find(CardRecharge.class, id);
	}
	/**
	 * 查询充值卡列表
	 *
	 * @param cardTreeId   卡种主键
	 * @param userId       用户Id
	 * @param cardPassword 卡密
	 * @param cardState    卡状态
	 * @param startTime    开始时间
	 * @param endTime      结束时间
	 * @param pageIndex    起始页
	 * @param pageSize     页大小
	 * @return page
	 */
	public PageCoreBean<Map<String, Object>> listCard(String cardTreeId, String cardState, String cardPassword,
			String userId, Long startTime, Long endTime, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "SELECT count(*) ";
		String sql = "SELECT CARD_RECHARGE_ID_,CARD_TREE_ID_,CARD_PASSWORD_,CARD_TREE_NAME_,CARD_TREE_POINT_, "
				+ " USE_USER_ID_,USER_NAME_,CARD_RECHARGE_STATE_,STATE_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,CREATOR_NAME_, "
				+ " OWNER_NAME_,DESC_ ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(StateEnum.DEL.name());

		String sqlPlus = " FROM card_recharge WHERE STATE_ != ? ";
		if (StringTool.notEmpty(cardTreeId)) {
			sqlPlus += " AND CARD_TREE_ID_ = ? ";
			parameters.add(cardTreeId);
		}
		if (StringTool.notEmpty(cardPassword)) {
			sqlPlus += " AND CARD_PASSWORD_ = ? ";
			parameters.add(cardPassword);
		}
		if (StringTool.notEmpty(cardState)) {
			sqlPlus += " AND CARD_RECHARGE_STATE_ = ? ";
			parameters.add(cardState);
		}
		if (StringTool.notEmpty(userId)) {
			sqlPlus += " AND (CREATE_USER_ID_ =? OR OWNER_USER_ID_ = ? ) ";
			parameters.add(userId);
			parameters.add(userId);
		}
		if (startTime != null && endTime != null) {
			sqlPlus += " AND CREATE_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}

		return dao.page(sql + sqlPlus, parameters, pageIndex, pageSize, sqlCount + sqlPlus, parameters);
	}

	/**
	 * 查找卡的状态
	 *
	 * @param cardId 卡主键
	 * @return 卡状态
	 */
	public String findCardState(String cardId) throws SQLException {
		String sql = "SELECT CARD_RECHARGE_STATE_ as key_ FROM card_recharge WHERE CARD_RECHARGE_ID_ = ? AND STATE_ != ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(cardId);
		parameters.add(StateEnum.DEL.name());
		return super.findString(sql, parameters);
	}

	/**
	 * 更新充值卡
	 */
	public void update(String cardId, String cardState, String state, String desc, Date nowTime) throws SQLException {
		String sql = "UPDATE card_recharge set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		if (StringTool.notEmpty(cardState)) {
			sql += " ,CARD_RECHARGE_STATE_ = ?";
			parameters.add(cardState);
		}
		if (StringTool.notEmpty(state)) {
			sql += " ,STATE_ = ?";
			parameters.add(state);
		}
		if (StringTool.notEmpty(desc)) {
			sql += " ,DESC_ = ?";
			parameters.add(desc);
		}
		sql += " WHERE CARD_RECHARGE_ID_ = ? ";
		parameters.add(cardId);
		super.execute(sql, parameters);
	}

	/**
	 * 查询自己创建的特殊开卡
	 *
	 * @return OWNER_NAME_, CARD_TREE_NAME_，CARD_TREE_POINT_，CARD_PRICE_T_
	 */
	public List<Map<String, Object>> listSpecialReport4Create(String userId,String ownerUserId, String cardTreeId, Long startTime,
			Long endTime) throws SQLException {
		String sql = "SELECT OWNER_NAME_ CREATOR_NAME_,CARD_TREE_NAME_,SUM(CARD_TREE_POINT_) AS POINT_TOTAL_, "
				+ " SUM(CARD_PRICE_T_) AS PRICE_T_ FROM card_recharge "
				+ " WHERE CREATE_USER_ID_ = ? and OWNER_USER_ID_!=? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(ownerUserId);
		parameters.add(StateEnum.OPEN.name());
		if (StringTool.notEmpty(cardTreeId)) {
			sql += " AND CARD_TREE_ID_ = ? ";
			parameters.add(cardTreeId);
		}
		if (startTime != null && endTime != null) {
			sql += " AND CREATE_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		sql += "GROUP BY OWNER_USER_ID_,CARD_TREE_ID_ ORDER BY OWNER_USER_ID_";
		return super.findMapList(sql, parameters);
	}

	/**
	 * 查询自己拥有的特殊开卡
	 *
	 * @return CREATOR_NAME_, CARD_TREE_NAME_，CARD_TREE_POINT_，CARD_PRICE_T_
	 */
	public List<Map<String, Object>> listSpecialReport4Owner(String userId, String cardTreeId, Long startTime,
			Long endTime) throws SQLException {
		String sql = "SELECT CREATOR_NAME_ ,CARD_TREE_NAME_,SUM(CARD_TREE_POINT_) AS POINT_TOTAL_, "
				+ " SUM(CARD_PRICE_T_) AS PRICE_T_ FROM card_recharge "
				+ " WHERE OWNER_USER_ID_ = ? and CREATE_USER_ID_!=? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		if (StringTool.notEmpty(cardTreeId)) {
			sql += " AND CARD_TREE_ID_ = ? ";
			parameters.add(cardTreeId);
		}
		if (startTime != null && endTime != null) {
			sql += " AND CREATE_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		sql += "GROUP BY CREATE_USER_ID_,CARD_TREE_ID_ ORDER BY CREATE_USER_ID_";
		return super.findMapList(sql, parameters);
	}

	/**
	 * 查询充值卡用于激活的信息
	 * @param cardPassword 卡密
	 * @return  CARD_RECHARGE_ID_,CARD_TREE_POINT_,CARD_TREE_ID_,CARD_PRICE_T_
	 */
	public Map<String, Object> findInfo4Active(String cardPassword) throws SQLException {
		String sql = "SELECT CARD_RECHARGE_ID_,CARD_TREE_POINT_,CARD_TREE_ID_,CARD_PRICE_T_,OWNER_USER_ID_,OWNER_NAME_, "
				+ " CARD_TREE_NAME_,CREATE_USER_ID_ FROM card_recharge "
				+ " WHERE CARD_PASSWORD_ = ? AND CARD_RECHARGE_STATE_ != ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(cardPassword);
		parameters.add(CardTool.State.ACTIVATE.name());
		parameters.add(StateEnum.OPEN.name());
		return super.findMap(sql, parameters);
	}
	/**
	 * 激活充值卡
	 * @param cardRechargeId 充值卡主键
	 * @param useUserId 使用者主键
	 * @param userName 使用者名称
	 * @param nowTime 激活时间
	 */
	public void updateActive(String cardRechargeId, String useUserId, String userName, Date nowTime) throws SQLException {
		String sql = "UPDATE card_recharge set USE_USER_ID_ = ?,USER_NAME_ = ?,CARD_RECHARGE_STATE_ = ?, "
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE CARD_RECHARGE_ID_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(useUserId);
		parameters.add(userName);
		parameters.add(CardTool.State.ACTIVATE.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(cardRechargeId);
		super.execute(sql,parameters);
	}
}
