package com.ibs.plan.module.cloud.ibsp_hm_bet_item.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.entity.IbspHmBetItem;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSP_盘口会员投注 服务类
 *
 * @author Robot
 */
public class IbspHmBetItemService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员投注 对象数据
	 *
	 * @param entity IbspHmBetItem对象数据
	 */
	public String save(IbspHmBetItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_hm_bet_item 的 IBSP_HM_BET_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_hm_bet_item set state_='DEL' where IBSP_HM_BET_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_HM_BET_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_hm_bet_item 的 IBSP_HM_BET_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_hm_bet_item set state_='DEL' where IBSP_HM_BET_ITEM_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_hm_bet_item  的 IBSP_HM_BET_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_hm_bet_item where IBSP_HM_BET_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_HM_BET_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_hm_bet_item 的 IBSP_HM_BET_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_hm_bet_item where IBSP_HM_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHmBetItem实体信息
	 *
	 * @param entity IBSP_盘口会员投注 实体
	 */
	public void update(IbspHmBetItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_hm_bet_item表主键查找 IBSP_盘口会员投注 实体
	 *
	 * @param id ibsp_hm_bet_item 主键
	 * @return IBSP_盘口会员投注 实体
	 */
	public IbspHmBetItem find(String id) throws Exception {
		return dao.find(IbspHmBetItem.class, id);
	}
	/**
	 * 获取当期投注总额和当期投注总数
	 *
	 * @param gameId           游戏id
	 * @param handicapMemberId 盘口会员id
	 * @param period           期数
	 * @param data             data
	 */
	public void findSum(String gameId, String handicapMemberId, Object period, Map<String, Object> data)
			throws SQLException {
		String sql = "select SUM(FUND_T_) as amount,SUM(BET_CONTENT_LEN_) as number FROM ibsp_hm_bet_item"
				+ " WHERE HANDICAP_MEMBER_ID_ = ? and GAME_ID_=? and PERIOD_= ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(period);
		parameterList.add(IbsStateEnum.DEL.name());
		Map<String, Object> map = super.dao.findMap(sql, parameterList);

		sql = "SELECT count(IBSP_HM_BET_ITEM_ID_) as waiting FROM ibsp_hm_bet_item WHERE HANDICAP_MEMBER_ID_ = ?"
				+ " and GAME_ID_=? and PERIOD_= ? and STATE_ != ? and EXEC_STATE_=? ";
		parameterList.add(IbsStateEnum.PROCESS.name());
		//等待投注数
		data.put("waiting", Integer.parseInt(super.dao.findString("waiting", sql, parameterList)));
		//当期投注总额
		data.put("amount", 0);
		//当期投注总数
		data.put("number", 0);
		//期数
		data.put("period", period);
		if (StringTool.notEmpty(map.get("amount")) && StringTool.notEmpty(map.get("number"))) {
			data.put("amount", NumberTool.doubleT(map.get("amount")));
			data.put("number", Integer.parseInt(map.get("number").toString()));
		}
	}
	/**
	 * 查询新的投注记录数
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param checkTime        检查时间
	 * @param gameId           游戏ID
	 * @param number           条数
	 * @return 投注记录数
	 */
	public List<Map<String, Object>> listNewBetInfo(String gameId, String handicapMemberId, long checkTime, int number)
			throws SQLException {
		String sql = "SELECT IBSP_HM_BET_ITEM_ID_ as BET_ITEM_ID_,PERIOD_,PLAN_GROUP_DESC_,FUNDS_INDEX_,FUND_T_,"
				+ "BET_CONTENT_,DRAW_NUMBER_,EXEC_STATE_,PROFIT_T_,BET_MODE_,BET_TYPE_,DESC_ FROM"
				+ " ibsp_hm_bet_item  WHERE GAME_ID_ = ? AND  HANDICAP_MEMBER_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(gameId);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		if (number == 0) {
			sql += " AND CREATE_TIME_LONG_ >= ? ORDER BY CREATE_TIME_LONG_ desc";
			parameterList.add(checkTime);
		} else {
			sql += " AND EXEC_STATE_!=? ORDER BY CREATE_TIME_LONG_ desc limit " + number;
			parameterList.add(IbsStateEnum.FAIL.name());
		}
		return this.dao.findMapList(sql, parameterList);
	}

	/**
	 * 开奖期更新数据
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param checkTime        检查时间
	 * @param gameId           游戏ID
	 * @return 开奖期更新数据
	 */
	public List<Map<String, Object>> listDrawInfo(String gameId, String handicapMemberId, long checkTime)
			throws SQLException {
		String sql =
				"SELECT IBSP_HM_BET_ITEM_ID_ as BET_ITEM_ID_,DRAW_NUMBER_,BET_MODE_,EXEC_STATE_,PROFIT_T_,DESC_,BET_TYPE_ FROM ibsp_hm_bet_item "
						+ "WHERE HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? AND GAME_ID_ = ? AND CREATE_TIME_LONG_ <= ? "
						+ "AND UPDATE_TIME_LONG_ >= ? order by UPDATE_TIME_LONG_ desc";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(gameId);
		parameterList.add(checkTime);
		parameterList.add(checkTime);
		return this.dao.findMapList(sql, parameterList);
	}
	/**
	 * 获取投注信息
	 *
	 * @param hmBetItemId 投注信息主键
	 * @return 投注信息
	 */
	public Map<String, Object> findBetInfo(String hmBetItemId) throws SQLException {
		String sql =
				"SELECT IBSP_HM_BET_ITEM_ID_ as BET_ITEM_ID_,PERIOD_,BET_CONTENT_,FUND_T_ as FUNDS_T_ FROM ibsp_hm_bet_item "
						+ " where IBSP_HM_BET_ITEM_ID_ = ? and state_ = ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(hmBetItemId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 获取投注信息
	 *
	 * @param clientBetIds 客户端投注ids
	 * @return 投注信息
	 */
	public Map<String, Object> findBetInfos(JSONArray clientBetIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("select IBSP_HM_BET_ITEM_ID_ as key_,BET_CONTENT_ as value_ from ibsp_hm_bet_item"
				+ " where CLIENT_BET_ID_ in(");
		List<Object> parameters = new ArrayList<>(clientBetIds);
		for(int i=0,j=clientBetIds.size();i<j;i++){
			sql.append("?,");
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.findKVMap(sql.toString(),parameters);
	}
	/**
	 * 获取待结算投注信息
	 *
	 * @param gameId      游戏主键
	 * @param period      期数
	 * @param handicapIds 盘口主键数组
	 * @return 待结算投注信息
	 */
	public Map<String, List<Map<String, Object>>> mapBetInfo(String gameId, Object period, List<String> handicapIds)
			throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBSP_HM_BET_ITEM_ID_ AS BET_ID_,HANDICAP_MEMBER_ID_,PLAN_ID_"
				+ ",BET_MODE_,FUND_T_,BET_CONTENT_LEN_,BET_CONTENT_ FROM ibsp_hm_bet_item WHERE "
				+ " GAME_ID_ = ? AND PERIOD_ = ? AND EXEC_STATE_ = ? AND STATE_ = ?  AND HANDICAP_ID_ in(");
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(gameId);
		parameterList.add(period);
		parameterList.add(IbsStateEnum.SUCCESS.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String handicapId : handicapIds) {
			sql.append("?,");
			parameterList.add(handicapId);

		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(") ORDER BY HANDICAP_MEMBER_ID_");
		return super.findKeyMaps(sql,parameterList,"HANDICAP_MEMBER_ID_");
	}

	/**
	 * 批量更新结算信息
	 *
	 * @param betInfos   投注结果信息
	 * @param drawNumber 开奖号码
	 * @param nowTime    更新时间
	 */
	public void updateResult(List<Map<String, Object>> betInfos, String drawNumber, Date nowTime) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"update ibsp_hm_bet_item set EXEC_STATE_ = ?,DRAW_NUMBER_ =?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ =?,");
		sql.append("PROFIT_T_= CASE IBSP_HM_BET_ITEM_ID_");
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsStateEnum.FINISH.name());
		parameters.add(drawNumber);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		StringBuilder sqlPlus = new StringBuilder();

		List<Object> parametersPlus = new ArrayList<>();

		for (Map<String, Object> map : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(map.get("BET_ID_"));
			parameters.add(map.get("PROFIT_T_"));

			sqlPlus.append("?,");
			parametersPlus.add(map.get("BET_ID_"));
		}
		sql.append(" end where IBSP_HM_BET_ITEM_ID_ in (");
		sql.append(sqlPlus);
		parameters.addAll(parametersPlus);
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

	private static final String PLAN_GROUP_DESC_FORMAT = "方案:%03d-组:%2s";
	/**
	 * 批量保存
	 */
	public void batchSave(JSONArray betItems, String handicapMemberId, String period, String betMode, String handicapId,
			String gameId, String execState,Map<String,Object> planSnInfo, Date nowTime) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ibsp_hm_bet_item(IBSP_HM_BET_ITEM_ID_,CLIENT_BET_ID_,HANDICAP_MEMBER_ID_,HANDICAP_ID_,"
				+ "GAME_ID_,PLAN_ID_,PERIOD_,BET_MODE_,BET_TYPE_,PLAN_GROUP_DESC_,BET_CONTENT_LEN_,BET_CONTENT_,"
				+ "FUND_T_,FUNDS_INDEX_,EXEC_STATE_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,"
				+ "UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters = new ArrayList<>();
		JSONObject betItem;
		for (int i = 0; i < betItems.size(); i++) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			betItem = betItems.getJSONObject(i);
			int sn=NumberTool.getInteger(planSnInfo.getOrDefault(betItem.getString("planCode"),1));
			PlanUtil.Code planCode = PlanUtil.Code.valueOf(betItem.getString("planCode"));
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(betItem.get("clientBetId"));
			parameters.add(handicapMemberId);
			parameters.add(handicapId);
			parameters.add(gameId);
			parameters.add(PlanUtil.id(planCode));
			parameters.add(period);
			parameters.add(betMode);
			parameters.add(IbsTypeEnum.PLAN.ordinal());
			parameters.add(String.format(PLAN_GROUP_DESC_FORMAT,sn,StringTool.addOne( betItem.getString("activeKey"))));
			parameters.add(betItem.get("betContentLen"));
			parameters.add(betItem.get("betContent"));
			parameters.add(betItem.get("betFundT"));
			parameters.add(betItem.get("fundsKey"));
			parameters.add(execState);
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(IbsStateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql.toString(), parameters);
	}

	/**
	 * 修改投注结果
	 *
	 * @param betItemId   投注详情主键
	 * @param clientBetId 客户端投注主键
	 * @param execState   执行状态
	 * @param nowTime     更新时间
	 * @param msg         消息
	 */
	public void updateResult(String betItemId, String clientBetId, IbsStateEnum execState, Date nowTime, String msg)
			throws SQLException {
		String sql = "update ibsp_hm_bet_item set CLIENT_BET_ID_ = ?,EXEC_STATE_ = ?,UPDATE_TIME_ = ?, "
				+ " UPDATE_TIME_LONG_ =?,DESC_ = ?  where IBSP_HM_BET_ITEM_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(clientBetId);
		parameters.add(execState.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(msg);
		parameters.add(betItemId);
		parameters.add(IbsStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

	/**
	 * 修改投注结果
	 *
	 * @param clientBetIds 客户端投注主键
	 * @param nowTime      更新时间
	 * @param msg          描述
	 */
	public void updateResult(JSONArray clientBetIds,IbsStateEnum execState, Date nowTime, String msg) throws SQLException {
		StringBuilder sql = new StringBuilder("update ibsp_hm_bet_item set EXEC_STATE_ = ?,UPDATE_TIME_ = ?, "
				+ " UPDATE_TIME_LONG_ =?,DESC_ =?  where CLIENT_BET_ID_ in( ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(execState.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(msg);
		for (Object betId : clientBetIds) {
			sql.append("?,");
			parameters.add(betId);
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") and STATE_ = ?");
		parameters.add(IbsStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

	/**
	 * 修改投注结果
	 *
	 * @param data    投注结果
	 * @param nowTime 更新时间
	 * @param msg     描述
	 */
	public void updateResult(JSONObject data, Date nowTime, String msg) throws SQLException {
		StringBuilder sql = new StringBuilder("update ibsp_hm_bet_item set EXEC_STATE_ = ?,UPDATE_TIME_ = ?,");
		sql.append(" UPDATE_TIME_LONG_ =?,DESC_ = CASE CLIENT_BET_ID_ ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsStateEnum.FAIL.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		StringBuilder sqlPlus = new StringBuilder();

		List<Object> parametersPlus = new ArrayList<>();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(entry.getKey());
			parameters.add(entry.getValue() + "#" + msg);

			sqlPlus.append("?,");
			parametersPlus.add(entry.getKey());
		}
		sql.append(" end where CLIENT_BET_ID_ in (");
		sql.append(sqlPlus);
		parameters.addAll(parametersPlus);
		sql.deleteCharAt(sql.lastIndexOf(",")).append(") and STATE_ = ?");
		parameters.add(IbsStateEnum.OPEN.name());
		super.execute(sql.toString(), parameters);
	}
	/**
	 * 清除表格
	 *
	 * @param gameId           游戏id
	 * @param handicapMemberId 盘口会员id
	 * @param time             已开奖期数时间
	 */
	public void clearForm(String gameId, String handicapMemberId, long time) throws SQLException {
		String sql="update ibsp_hm_bet_item set STATE_=?,UPDATE_TIME_LONG_=? where HANDICAP_MEMBER_ID_=? and "
				+ " GAME_ID_=? and STATE_=? and CREATE_TIME_LONG_<?";
		List<Object> parameters=new ArrayList<>(6);
		parameters.add(IbsStateEnum.CLOSE.name());
		parameters.add(System.currentTimeMillis());
		parameters.add(handicapMemberId);
		parameters.add(gameId);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(time);
		super.dao.execute(sql,parameters);
	}
	/**
	 * 保存错误信息
	 *
	 * @param errorMap 错误信息map
	 */
	public void updateErrorInfo(Map<String, String> errorMap) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		sql.append("select IBSP_HM_BET_ITEM_ID_ as key_,DESC_ as value_ from ibsp_hm_bet_item where IBSP_HM_BET_ITEM_ID_ in (");
		for (String key : errorMap.keySet()) {
			sql.append("?,");
			parameters.add(key);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		Map<String, String> map = findKVMap(sql.toString(), parameters);
		if (ContainerTool.isEmpty(map)) {
			return;
		}
		sql.delete(0, sql.length());
		parameters.clear();

		StringBuilder sqlPlus = new StringBuilder();
		sql.append("update ibsp_hm_bet_item set UPDATE_TIME_LONG_=?,DESC_= CASE IBSP_HM_BET_ITEM_ID_");
		parameters.add(System.currentTimeMillis());
		for (Map.Entry<String, String> entry : errorMap.entrySet()) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(entry.getKey());
			parameters.add(StringTool.notEmpty(map.get(entry.getKey())) ? map.get(entry.getKey()).concat(",").concat(entry.getValue()) : entry.getValue());

			sqlPlus.append("?,");
		}
		parameters.addAll(errorMap.keySet());
		sql.append(" end where IBSP_HM_BET_ITEM_ID_ in (");
		sql.append(sqlPlus).replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(), parameters);
	}
}
