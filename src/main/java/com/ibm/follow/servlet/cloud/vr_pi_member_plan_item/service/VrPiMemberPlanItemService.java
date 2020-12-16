package com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.entity.VrPiMemberPlanItem;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * VR会员方案详情 服务类
 *
 * @author Robot
 */
public class VrPiMemberPlanItemService extends BaseServiceProxy {

	/**
	 * 保存VR会员方案详情 对象数据
	 *
	 * @param entity VrPiMemberPlanItem对象数据
	 */
	public String save(VrPiMemberPlanItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除vr_pi_member_plan_item 的 VR_PI_MEMBER_PLAN_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_pi_member_plan_item set state_='DEL' where VR_PI_MEMBER_PLAN_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_PI_MEMBER_PLAN_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 vr_pi_member_plan_item 的 VR_PI_MEMBER_PLAN_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_pi_member_plan_item set state_='DEL' where VR_PI_MEMBER_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 vr_pi_member_plan_item  的 VR_PI_MEMBER_PLAN_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_pi_member_plan_item where VR_PI_MEMBER_PLAN_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_PI_MEMBER_PLAN_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除vr_pi_member_plan_item 的 VR_PI_MEMBER_PLAN_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_pi_member_plan_item where VR_PI_MEMBER_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrPiMemberPlanItem实体信息
	 *
	 * @param entity VR会员方案详情 实体
	 */
	public void update(VrPiMemberPlanItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_pi_member_plan_item表主键查找 VR会员方案详情 实体
	 *
	 * @param id vr_pi_member_plan_item 主键
	 * @return VR会员方案详情 实体
	 */
	public VrPiMemberPlanItem find(String id) throws Exception {
		return dao.find(VrPiMemberPlanItem.class, id);
	}

	/**
	 * 保存会员方案详情数据
	 * @param planItem 方案详情数据
	 * @param gameCode 游戏编码
	 * @param memberId 会员Id
	 * @param nowTime 时间
	 */
	public void save(List<Map<String, Object>> planItem, String gameCode, String memberId, Date nowTime) throws SQLException {
		String sql = "INSERT INTO `vr_pi_member_plan_item` ( `VR_PI_MEMBER_PLAN_ITEM_ID_`,`GAME_CODE_`,`PLAN_ID_`,`VR_MEMBER_ID_`,  `PROFIT_LIMIT_MAX_`, `LOSS_LIMIT_MIN_`, " +
				"`FUNDS_LIST_`, `FOLLOW_PERIOD_`, `MONITOR_PERIOD_`, `BET_MODE_`, `FUND_SWAP_MODE_`, " +
				"`PERIOD_ROLL_MODE_`, `PLAN_GROUP_DATA_`, `PLAN_GROUP_ACTIVE_KEY_`, `EXPAND_INFO_`, " +
				"`CREATE_TIME_`, `CREATE_TIME_LONG_`, `UPDATE_TIME_`, `UPDATE_TIME_LONG_`, `STATE_`)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?);";
		List<Object> parameterList = new ArrayList<>();
		String hmSql = "INSERT INTO `vr_plan_hm` (`PLAN_ITEM_TABLE_ID_`,`GAME_CODE_`, `PLAN_ID_`,`VR_MEMBER_ID_`,`VR_PLAN_HM_ID_`,`PLAN_CODE_`, " +
				" `PLAN_NAME_`,  `SN_`, `PLAN_STATE_`,`CREATE_TIME_`, `CREATE_TIME_LONG_`, `UPDATE_TIME_`, `UPDATE_TIME_LONG_`, `STATE_`) " +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		String pk;
		for (Map<String, Object> item : planItem) {
			pk = RandomTool.getNumLetter32();
			parameterList.add(pk);
			parameterList.add(gameCode);
			parameterList.add(item.get("PLAN_ID_"));
			parameterList.add(memberId);
			parameterList.add(item.get("PROFIT_LIMIT_MAX_"));
			parameterList.add(item.get("LOSS_LIMIT_MIN_"));
			parameterList.add(item.get("FUNDS_LIST_"));
			parameterList.add(item.get("FOLLOW_PERIOD_"));
			parameterList.add(item.get("MONITOR_PERIOD_"));
			parameterList.add(item.get("BET_MODE_"));
			parameterList.add(item.get("FUND_SWAP_MODE_"));
			parameterList.add(item.get("PERIOD_ROLL_MODE_"));
			parameterList.add(item.get("PLAN_GROUP_DATA_"));
			parameterList.add(item.get("PLAN_GROUP_ACTIVE_KEY_"));
			parameterList.add(item.get("EXPAND_INFO_"));
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(IbmStateEnum.OPEN.name());
			dao.execute(sql,parameterList);
			parameterList.clear();

			parameterList.add(pk);
			parameterList.add(gameCode);
			parameterList.add(item.get("PLAN_ID_"));
			parameterList.add(memberId);
			parameterList.add( RandomTool.getNumLetter32());
			parameterList.add(item.get("PLAN_CODE_"));
			parameterList.add(item.get("PLAN_NAME_"));
			parameterList.add(item.get("SN_"));
			parameterList.add(IbmTypeEnum.FALSE.name());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(IbmStateEnum.OPEN.name());
			dao.execute(hmSql,parameterList);
			parameterList.clear();

		}
	}

	/**
	 * 查询用户方案设置详情
	 * @param memberId 会员Id
	 * @param gameCode 游戏编码
	 * @param planId 方案Id
	 * @return
	 * @throws SQLException
	 */
	public Map<String,Object> findPlanItem(String memberId,String gameCode,String planId) throws SQLException {
		String sql = "SELECT VR_MEMBER_ID_,GAME_CODE_,PROFIT_LIMIT_MAX_,LOSS_LIMIT_MIN_,FUNDS_LIST_,FOLLOW_PERIOD_,MONITOR_PERIOD_,BET_MODE_," +
				"FUND_SWAP_MODE_,PERIOD_ROLL_MODE_,PLAN_GROUP_DATA_,PLAN_GROUP_ACTIVE_KEY_,EXPAND_INFO_" +
				" FROM `vr_pi_member_plan_item` WHERE VR_MEMBER_ID_ = ? and GAME_CODE_=? AND PLAN_ID_= ? AND STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(memberId);
		parameterList.add(gameCode);
		parameterList.add(planId);
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findMap(sql,parameterList);
	}

}
