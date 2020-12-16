package com.cloud.recharge.card_user_relation.service;

import com.cloud.recharge.card_user_relation.entity.CardUserRelation;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 充值卡用户关联 服务类
 *
 * @author Robot
 */
public class CardUserRelationService extends BaseServiceProxy {

	/**
	 * 保存充值卡用户关联 对象数据
	 *
	 * @param entity CardUserRelation对象数据
	 */
	public String save(CardUserRelation entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除card_user_relation 的 CARD_USER_RELATION_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update card_user_relation set state_='DEL' where CARD_USER_RELATION_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CARD_USER_RELATION_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 card_user_relation 的 CARD_USER_RELATION_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update card_user_relation set state_='DEL' where CARD_USER_RELATION_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 card_user_relation  的 CARD_USER_RELATION_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from card_user_relation where CARD_USER_RELATION_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CARD_USER_RELATION_ID_主键id数组的数据
	 *
	 * @param idArray 要删除card_user_relation 的 CARD_USER_RELATION_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from card_user_relation where CARD_USER_RELATION_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CardUserRelation实体信息
	 *
	 * @param entity 充值卡用户关联 实体
	 */
	public void update(CardUserRelation entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据card_user_relation表主键查找 充值卡用户关联 实体
	 *
	 * @param id card_user_relation 主键
	 * @return 充值卡用户关联 实体
	 */
	public CardUserRelation find(String id) throws Exception {
		return dao.find(CardUserRelation.class, id);
	}

	/**
	 * 更新关联信息
	 */
	public void updateRelation(String adminUserId, String usedUserId, String usedUserName, Date nowTime)
			throws SQLException {
		String id = findId(usedUserId);
		String sql;
		List<Object> parameters = new ArrayList<>(8);
		if (StringTool.isEmpty(id)) {
			sql = "INSERT INTO card_user_relation (CARD_USER_RELATION_ID_,USED_USER_ID_,USED_USER_NAME_,ADMIN_USER_ID_, "
					+ " CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) VALUES (?,?,?,?,?,?,?,?) ";
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(usedUserId);
			parameters.add(usedUserName);
			parameters.add(adminUserId);
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(System.currentTimeMillis());
			parameters.add(StateEnum.OPEN.name());
		} else {
			sql = "UPDATE card_user_relation set USED_USER_NAME_ = ?,ADMIN_USER_ID_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
					+ " WHERE CARD_USER_RELATION_ID_ = ?";
			parameters.add(usedUserName);
			parameters.add(adminUserId);
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(id);
		}
		super.execute(sql, parameters);
	}

	/**
	 * 查询主键
	 *
	 * @param usedUserId 使用用户主键
	 * @return 关联主键
	 */
	private String findId(String usedUserId) throws SQLException {
		String sql = "SELECT CARD_USER_RELATION_ID_ as key_ FROM card_user_relation where USED_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(usedUserId);
		parameterList.add(StateEnum.DEL.name());
		return super.findString(sql, parameterList);
	}
}
