package com.cloud.recharge.card_admin_token.service;

import com.cloud.recharge.card_admin_token.entity.CardAdminToken;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 开卡用户令牌 服务类
 *
 * @author Robot
 */
public class CardAdminTokenService extends BaseServiceProxy {

	/**
	 * 保存开卡用户令牌 对象数据
	 *
	 * @param entity CardAdminToken对象数据
	 */
	public String save(CardAdminToken entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除card_admin_token 的 CARD_ADMIN_TOKEN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update card_admin_token set state_='DEL' where CARD_ADMIN_TOKEN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CARD_ADMIN_TOKEN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 card_admin_token 的 CARD_ADMIN_TOKEN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update card_admin_token set state_='DEL' where CARD_ADMIN_TOKEN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 card_admin_token  的 CARD_ADMIN_TOKEN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from card_admin_token where CARD_ADMIN_TOKEN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CARD_ADMIN_TOKEN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除card_admin_token 的 CARD_ADMIN_TOKEN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from card_admin_token where CARD_ADMIN_TOKEN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CardAdminToken实体信息
	 *
	 * @param entity 开卡用户令牌 实体
	 */
	public void update(CardAdminToken entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据card_admin_token表主键查找 开卡用户令牌 实体
	 *
	 * @param id card_admin_token 主键
	 * @return 开卡用户令牌 实体
	 */
	public CardAdminToken find(String id) throws Exception {
		return dao.find(CardAdminToken.class, id);
	}

	/**
	 * 查询用户主键
	 *
	 * @param token 用户token
	 * @return 用户主键
	 */
	public String findUserId(String token) throws SQLException {
		String sql = "SELECT APP_USER_ID_ AS key_ FROM card_admin_token WHERE "
				+ " VALUE_ = ?  AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(token);


		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}
}
