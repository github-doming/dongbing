package com.ibm.follow.servlet.cloud.vr_fm_member_bet_item.service;

import com.common.util.BaseGameUtil;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_fm_member_bet_item.entity.VrFmMemberBetItem;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* VR_会员跟投信息 服务类
 * @author Robot
 */
public class VrFmMemberBetItemService extends BaseServiceProxy {

	/**
	 * 保存VR_会员跟投信息 对象数据
	 * @param entity VrFmMemberBetItem对象数据
	 */
	public String save(VrFmMemberBetItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_fm_member_bet_item 的 VR_FM_MEMBER_BET_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_fm_member_bet_item set state_='DEL' where VR_FM_MEMBER_BET_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_FM_MEMBER_BET_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除 vr_fm_member_bet_item 的 VR_FM_MEMBER_BET_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_fm_member_bet_item set state_='DEL' where VR_FM_MEMBER_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_fm_member_bet_item  的 VR_FM_MEMBER_BET_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_fm_member_bet_item where VR_FM_MEMBER_BET_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_FM_MEMBER_BET_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除vr_fm_member_bet_item 的 VR_FM_MEMBER_BET_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_fm_member_bet_item where VR_FM_MEMBER_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrFmMemberBetItem实体信息
	 * @param entity VR_会员跟投信息 实体
	 */
	public void update(VrFmMemberBetItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_fm_member_bet_item表主键查找 VR_会员跟投信息 实体
	 * @param id vr_fm_member_bet_item 主键
	 * @return VR_会员跟投信息 实体
	 */
	public VrFmMemberBetItem find(String id) throws Exception {
		return dao.find(VrFmMemberBetItem.class,id);
	}

	/**
	 * 修改跟投信息
	 * @param vrFollowBetId	虚拟跟投id
	 */
	public void updateProcessInfo(String vrFollowBetId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		sql.append("update vr_fm_member_bet_item set UPDATE_TIME_LONG_=?,EXEC_STATE_=? where VR_FOLLOW_BET_ID_ in(");
		parameters.add(System.currentTimeMillis());
		parameters.add(IbmStateEnum.SUCCESS.name());
		String[] followBetIds = vrFollowBetId.split(",");
		for (String followBetId : followBetIds) {
			sql.append("?,");
			parameters.add(followBetId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

	public void updateProcessInfo(List<String> haFollowBetIds, IbmStateEnum requestType) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		sql.append("update vr_fm_member_bet_item set UPDATE_TIME_LONG_=?,EXEC_STATE_=? where VR_FOLLOW_BET_ID_ in(");
		parameters.add(System.currentTimeMillis());
		parameters.add(requestType.name());
		for (String haFollowBetId : haFollowBetIds) {
			String[] followBetIds=haFollowBetId.split(",");
			for(String followBetId:followBetIds){
				sql.append("?,");
				parameters.add(followBetId);
			}
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 清空表格
	 * @param gameCode	游戏编码
	 * @param vrMemberId	虚拟会员id
	 * @param appUserId	用户id
	 * @param time			已开奖期数时间
	 */
	public void clearForm(BaseGameUtil.Code gameCode, String vrMemberId, String appUserId, long time) throws SQLException {
		String sql = "update vr_fm_member_bet_item set STATE_=?,UPDATE_TIME_LONG_=? where VR_FOLLOW_BET_ID_=? and USER_ID_=? and "
				+ " GAME_CODE_=? and STATE_=? and CREATE_TIME_LONG_<?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(IbmStateEnum.CLOSE.name());
		parameters.add(System.currentTimeMillis());
		parameters.add(vrMemberId);
		parameters.add(appUserId);
		parameters.add(gameCode.name());
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(time);
		super.dao.execute(sql, parameters);
	}

	/**
	 * 查询新的投注记录数
	 * @param gameCode		游戏编码
	 * @param vrMemberId		虚拟会员id
	 * @param appUserId		用户id
	 * @param checkTime		检验时间
	 * @param number			条数
	 * @return
	 */
	public List<Map<String, Object>> listNewBetInfo(String gameCode, String vrMemberId, String appUserId, long checkTime, int number) throws SQLException {
		String sql = "SELECT VR_FM_MEMBER_BET_ITEM_ID_,FOLLOW_MEMBER_ACCOUNT_,EXEC_STATE_,PERIOD_,BET_CONTENT_,BET_FUND_T_," +
				"FOLLOW_CONTENT_,FOLLOW_FUND_T_,CREATE_TIME_LONG_ FROM vr_fm_member_bet_item WHERE GAME_CODE_ = ?"
				+ " AND VR_MEMBER_ID_ = ? and USER_ID_=? AND STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(gameCode);
		parameterList.add(vrMemberId);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.OPEN.name());
		if (number == 0) {
			sql += " AND CREATE_TIME_LONG_ >= ? ORDER BY CREATE_TIME_LONG_ desc";
			parameterList.add(checkTime);
		} else {
			sql += " AND EXEC_STATE_!=? ORDER BY CREATE_TIME_LONG_ desc limit " + number;
			parameterList.add(IbmStateEnum.FAIL.name());
		}
		return this.dao.findMapList(sql, parameterList);
	}

	/**
	 * 开奖期更新数据
	 * @param gameCode	游戏编码
	 * @param vrMemberId	虚拟会员id
	 * @param appUserId	用户id
	 * @param checkTime	检验时间
	 * @return
	 */
	public List<Map<String, Object>> listDrawInfo(String gameCode, String vrMemberId, String appUserId, long checkTime) throws SQLException {
		String sql = "SELECT VR_FM_MEMBER_BET_ITEM_ID_,EXEC_STATE_ FROM vr_fm_member_bet_item WHERE VR_MEMBER_ID_ = ? and USER_ID_=? AND STATE_ != ? "
				+ " AND GAME_CODE_ = ? AND CREATE_TIME_LONG_ <= ? AND UPDATE_TIME_LONG_ >= ?  order by UPDATE_TIME_LONG_ desc";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(vrMemberId);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(gameCode);
		parameterList.add(checkTime);
		parameterList.add(checkTime);
		return this.dao.findMapList(sql, parameterList);
	}
}
