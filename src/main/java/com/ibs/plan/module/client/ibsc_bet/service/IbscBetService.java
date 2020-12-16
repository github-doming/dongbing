package com.ibs.plan.module.client.ibsc_bet.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.ibsc_bet.entity.IbscBet;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSC客户端_投注 服务类
 *
 * @author Robot
 */
public class IbscBetService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_投注 对象数据
	 *
	 * @param entity IbscBet对象数据
	 */
	public String save(IbscBet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_bet 的 IBSC_BET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_bet set state_='DEL' where IBSC_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_BET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_bet 的 IBSC_BET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_bet set state_='DEL' where IBSC_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_bet  的 IBSC_BET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_bet where IBSC_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_BET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_bet 的 IBSC_BET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_bet where IBSC_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscBet实体信息
	 *
	 * @param entity IBSC客户端_投注 实体
	 */
	public void update(IbscBet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_bet表主键查找 IBSC客户端_投注 实体
	 *
	 * @param id ibsc_bet 主键
	 * @return IBSC客户端_投注 实体
	 */
	public IbscBet find(String id) throws Exception {
		return (IbscBet) dao.find(IbscBet.class, id);

	}

	/**
	 * 获取投注信息,只获取执行状态为success的
	 *
	 * @param gameCode 游戏编码
	 * @param drawType 类型
	 * @param period   期数
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> mapBetInfo(GameUtil.Code gameCode, String drawType, Object period)
			throws SQLException {
		String sql = "SELECT IBSC_BET_ID_ AS BET_ID_, EXIST_HM_ID_,PLAN_CODE_,PLAN_GROUP_KEY_,FUND_GROUP_KEY_, "
				+ " BET_FUND_T_, BET_CONTENT_, BET_CONTENT_LEN_,PERIOD_,BASE_PERIOD_,EXPAND_INFO_ FROM ibsc_bet WHERE GAME_CODE_ = ? "
				+ " and GAME_DRAW_TYPE_=? AND PERIOD_ = ? and BET_TYPE_=? AND BET_STATE_ = ? AND STATE_ =? ORDER BY EXIST_HM_ID_";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(gameCode.name());
		parameterList.add(drawType);
		parameterList.add(period);
		parameterList.add(IbsTypeEnum.PLAN.name());
		parameterList.add(IbsStateEnum.SUCCESS.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		List<Map<String, Object>> betList = super.findMapList(sql, parameterList);
		if (ContainerTool.isEmpty(betList)) {
			return new HashMap<>(1);
		}
		Map<String, List<Map<String, Object>>> mapExecBet = new HashMap<>(betList.size() / 4);

		for (Map<String, Object> bet : betList) {
			String existHmId = bet.remove("EXIST_HM_ID_").toString();
			if (mapExecBet.containsKey(existHmId)) {
				mapExecBet.get(existHmId).add(bet);
			} else {
				List<Map<String, Object>> execBetItems = new ArrayList<>(4);
				execBetItems.add(bet);
				mapExecBet.put(existHmId, execBetItems);
			}
		}
		return mapExecBet;
	}

	/**
	 * 批量更新结算信息
	 *
	 * @param betList    投注结果信息
	 * @param drawNumber 开奖号码
	 */
	public void updateResult(List<Map<String, Object>> betList, String drawNumber) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("update ibsc_bet set BET_STATE_ =?,DRAW_NUMBER_ =?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ =?,");
		sql.append("PROFIT_T_= CASE IBSC_BET_ID_");
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbsStateEnum.FINISH.name());
		parameters.add(drawNumber);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		StringBuilder sqlPlus = new StringBuilder();

		List<Object> parametersPlus = new ArrayList<>();

		for (Map<String, Object> map : betList) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(map.get("BET_ID_"));
			parameters.add(map.get("PROFIT_T_"));

			sqlPlus.append("?,");
			parametersPlus.add(map.get("BET_ID_"));
		}
		sql.append(" end where IBSC_BET_ID_ in (");
		sql.append(sqlPlus);
		parameters.addAll(parametersPlus);
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 批量添加
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏编码
	 * @param drawType  类型
	 * @param period    期数
	 * @param betType   投注类型
	 * @param betItems  投注信息
	 * @return 投注主键数组
	 */
	public List<String> batchSave(String existHmId, GameUtil.Code gameCode, String drawType, Object period,
								  IbsTypeEnum betType, JSONArray betItems) throws SQLException {
		StringBuilder sql = new StringBuilder("insert into ibsc_bet ");
		sql.append(" (IBSC_BET_ID_,EXIST_HM_ID_,PLAN_CODE_,GAME_CODE_,GAME_DRAW_TYPE_,PERIOD_,PLAN_GROUP_KEY_"
				+ " ,FUND_GROUP_KEY_,BET_TYPE_,BET_CONTENT_LEN_,BET_CONTENT_,BET_FUND_T_,BET_STATE_,CREATE_TIME_, "
				+ " CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters = new ArrayList<>();
		Date nowTime = new Date();
		List<String> betIds = new ArrayList<>();
		JSONObject betItem;
		for (Object object : betItems) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			betItem = (JSONObject) object;
			String betId = RandomTool.getNumLetter32();
			betItem.put("clientBetId", betId);
			parameters.add(betId);
			parameters.add(existHmId);
			parameters.add(betItem.get("planCode"));
			parameters.add(gameCode.name());
			parameters.add(drawType);
			parameters.add(period);
			parameters.add(betItem.get("activeKey"));
			parameters.add(betItem.get("fundsKey"));
			parameters.add(betType.name());
			parameters.add(betItem.get("betContentLen"));
			parameters.add(betItem.get("betContent"));
			parameters.add(betItem.get("betFundT"));
			parameters.add(IbsStateEnum.OPEN.name());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbsStateEnum.OPEN.name());
			betIds.add(betId);
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameters);
		return betIds;
	}
	/**
	 * 批量添加
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏编码
	 * @param drawType  类型
	 * @param period    期数
	 * @param betType   投注类型
	 * @param betItems  投注信息
	 * @return 投注主键数组
	 */
	public List<String> batchSave(String existHmId, GameUtil.Code gameCode, String drawType, Object period,
			IbsTypeEnum betType, List<Map<String, Object>> betItems) throws SQLException {
		StringBuilder sql = new StringBuilder("insert into ibsc_bet ");
		sql.append(" (IBSC_BET_ID_,EXIST_HM_ID_,PLAN_CODE_,GAME_CODE_,GAME_DRAW_TYPE_,PERIOD_,BASE_PERIOD_,PLAN_GROUP_KEY_"
				+ " ,FUND_GROUP_KEY_,BET_TYPE_,BET_CONTENT_LEN_,BET_CONTENT_,BET_FUND_T_,BET_STATE_,EXPAND_INFO_,CREATE_TIME_, "
				+ " CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters = new ArrayList<>();
		Date nowTime = new Date();
		List<String> betIds = new ArrayList<>();
		for (Map<String, Object> betItem : betItems) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			String betId = RandomTool.getNumLetter32();
			betItem.put("clientBetId", betId);
			parameters.add(betId);
			parameters.add(existHmId);
			parameters.add(betItem.get("planCode"));
			parameters.add(gameCode.name());
			parameters.add(drawType);
			parameters.add(period);
			parameters.add(betItem.get("basePeriod"));
			parameters.add(betItem.get("activeKey"));
			parameters.add(betItem.get("fundsKey"));
			parameters.add(betType.name());
			parameters.add(betItem.get("betContentLen"));
			parameters.add(betItem.get("betContent"));
			parameters.add(betItem.get("betFundT"));
			parameters.add(IbsStateEnum.OPEN.name());
			parameters.add(betItem.get("expandInfo"));
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbsStateEnum.OPEN.name());
			betIds.add(betId);
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameters);
		return betIds;
	}

	/**
	 * 批量添加
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param gameCode     游戏编码
	 * @param gameDrawType 类型
	 * @param period       期数
	 * @param betType      投注类型
	 * @param betContent   投注正文
	 * @param funds        投注资金
	 * @return 投注主键数组
	 */
	public String save(String existHmId, GameUtil.Code gameCode, String gameDrawType, Object period,
					   IbsTypeEnum betType, String betContent, long funds, Date nowTime) throws SQLException {
		String sql = "insert into ibsc_bet (IBSC_BET_ID_,EXIST_HM_ID_,PLAN_CODE_,GAME_CODE_,GAME_DRAW_TYPE_,PERIOD_, "
				+ " BET_TYPE_,BET_CONTENT_LEN_,BET_CONTENT_,BET_FUND_T_,BET_STATE_,CREATE_TIME_,CREATE_TIME_LONG_, "
				+ " UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List<Object> parameters = new ArrayList<>();
		String betId = RandomTool.getNumLetter32();
		parameters.add(betId);
		parameters.add(existHmId);
		parameters.add(betType.name());
		parameters.add(gameCode.name());
		parameters.add(gameDrawType);
		parameters.add(period);
		parameters.add(betType.name());
		parameters.add(betContent.split("#").length);
		parameters.add(betContent);
		parameters.add(funds);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(IbsStateEnum.OPEN.name());
		super.execute(sql, parameters);
		return betId;
	}

	public void update(Collection<String> betIds, String msg, IbsStateEnum betState, Date nowTime) throws SQLException {
		update(betIds, msg, betState, IbsStateEnum.OPEN, nowTime);
	}

	/**
	 * 更新
	 *
	 * @param betIds          投注主键数组
	 * @param msg             投注消息
	 * @param betState        投注状态
	 * @param historyBetState 历史投注状态
	 * @param nowTime         时间
	 */
	public void update(Collection<String> betIds, String msg, IbsStateEnum betState, IbsStateEnum historyBetState, Date nowTime) throws SQLException {
		StringBuilder sql = new StringBuilder("update ibsc_bet set ");
		sql.append("BET_STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBSC_BET_ID_ in (");
		List<Object> parameters = new ArrayList<>();
		parameters.add(betState.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(msg);
		for (String betId : betIds) {
			sql.append("?,");
			parameters.add(betId);
		}
		sql.delete(sql.length() - 1, sql.length()).append(") AND BET_STATE_ = ? AND STATE_ = ?");
		parameters.add(historyBetState.name());
		parameters.add(IbsStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

	/**
	 * 获取投注信息
	 *
	 * @param betIds 投注主键数组
	 * @return 投注信息
	 */
	public Map<String, Object> mapByIds(List<String> betIds) throws SQLException {
		StringBuilder sql = new StringBuilder("select IBSC_BET_ID_ as key_,BET_CONTENT_ as value_ ")
				.append(" from ibsc_bet where IBSC_BET_ID_ in (");
		List<Object> parameters = new ArrayList<>();
		for (String betId : betIds) {
			sql.append("?,");
			parameters.add(betId);
		}
		sql.delete(sql.length() - 1, sql.length()).append(") AND BET_STATE_ = ? AND STATE_ = ?");
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findKVMap(sql.toString(), parameters);
	}
}
