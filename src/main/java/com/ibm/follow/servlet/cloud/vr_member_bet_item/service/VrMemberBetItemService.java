package com.ibm.follow.servlet.cloud.vr_member_bet_item.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.vr_member_bet_item.entity.VrMemberBetItem;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* VR_虚拟会员投注信息 服务类
 * @author Robot
 */
public class VrMemberBetItemService extends BaseServiceProxy {

	/**
	 * 保存VR_虚拟会员投注信息 对象数据
	 * @param entity VrMemberBetItem对象数据
	 */
	public String save(VrMemberBetItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_member_bet_item 的 VR_MEMBER_BET_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_member_bet_item set state_='DEL' where VR_MEMBER_BET_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_MEMBER_BET_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除 vr_member_bet_item 的 VR_MEMBER_BET_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_member_bet_item set state_='DEL' where VR_MEMBER_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_member_bet_item  的 VR_MEMBER_BET_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_member_bet_item where VR_MEMBER_BET_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_MEMBER_BET_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除vr_member_bet_item 的 VR_MEMBER_BET_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_member_bet_item where VR_MEMBER_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrMemberBetItem实体信息
	 * @param entity VR_虚拟会员投注信息 实体
	 */
	public void update(VrMemberBetItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_member_bet_item表主键查找 VR_虚拟会员投注信息 实体
	 * @param id vr_member_bet_item 主键
	 * @return VR_虚拟会员投注信息 实体
	 */
	public VrMemberBetItem find(String id) throws Exception {
		return dao.find(VrMemberBetItem.class,id);
	}

	/**
	 * 获取投注信息
	 * @param gameCode	 游戏编码
	 * @param startTime    开始时间
	 * @param endTime      结束时间
	 */
	public Map<String, List<Map<String, Object>>> mapBetItemInfo(GameUtil.Code gameCode,Date startTime, Date endTime) throws SQLException {
		String sql = "SELECT VR_MEMBER_BET_ITEM_ID_ AS BET_ID_,VR_MEMBER_ID_,GAME_CODE_,HANDICAP_CODE_, "
				+ " PERIOD_,BET_FUND_T_, BET_CONTENT_, BET_CONTENT_LEN_ FROM vr_member_bet_item WHERE GAME_CODE_=? and"
				+ " CREATE_TIME_LONG_ > ? AND CREATE_TIME_LONG_ < ? AND STATE_ = ? order by CREATE_TIME_LONG_";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(gameCode.name());
		parameterList.add(startTime.getTime());
		parameterList.add(endTime.getTime());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findKeyMaps(sql,parameterList,"HANDICAP_CODE_");
	}
	/**
	 * 获取投注信息
	 *
	 * @param gameCode      游戏编码
	 * @param period        期数
	 * @param handicapCodes 盘口codes
	 */
	public Map<String, List<Map<String, Object>>> mapBetItemInfo(GameUtil.Code gameCode, Object period, List<String> handicapCodes) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT VR_MEMBER_BET_ITEM_ID_ AS BET_ID_,VR_MEMBER_ID_, "
				+ " BET_FUND_T_, BET_CONTENT_, BET_CONTENT_LEN_ FROM vr_member_bet_item WHERE GAME_CODE_ = ?"
				+ " AND PERIOD_ = ? AND STATE_ = ? and HANDICAP_CODE_ in(");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(gameCode.name());
		parameterList.add(period);
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String handicapCode : handicapCodes) {
			sql.append("?,");
			parameterList.add(handicapCode);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(") ORDER BY VR_MEMBER_ID_");
		return super.findKeyMaps(sql,parameterList,"VR_MEMBER_ID_");
	}
}
