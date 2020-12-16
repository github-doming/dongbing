package com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.entity.IbmExecBetItemT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.tools.*;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmExecBetItemTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmExecBetItemT对象数据
	 */
	public String save(IbmExecBetItemT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_exec_bet_item的 IBM_EXEC_BET_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_exec_bet_item set state_='DEL' where IBM_EXEC_BET_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EXEC_BET_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_exec_bet_item的 IBM_EXEC_BET_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_exec_bet_item set state_='DEL' where IBM_EXEC_BET_ITEM_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_exec_bet_item的 IBM_EXEC_BET_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_exec_bet_item where IBM_EXEC_BET_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EXEC_BET_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_exec_bet_item的 IBM_EXEC_BET_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_exec_bet_item where IBM_EXEC_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmExecBetItemT实体信息
	 *
	 * @param entity IbmExecBetItemT实体
	 */
	public void update(IbmExecBetItemT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_exec_bet_item表主键查找IbmExecBetItemT实体
	 *
	 * @param id ibm_exec_bet_item 主键
	 * @return IbmExecBetItemT实体
	 */
	public IbmExecBetItemT find(String id) throws Exception {
		return (IbmExecBetItemT) this.dao.find(IbmExecBetItemT.class, id);

	}

	/**
	 * 获取分页Map数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_exec_bet_item where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_exec_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_exec_bet_item  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmExecBetItemT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmExecBetItemT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_exec_bet_item where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_exec_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_exec_bet_item  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmExecBetItemT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_exec_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmExecBetItemT数据信息
	 *
	 * @return 可用<IbmExecBetItemT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_exec_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmExecBetItemT.class, sql);
	}

	/**
	 * 查询投注记录数
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 * @param pageSize         页面大小
	 * @return 投注记录数
	 */
	public PageCoreBean<Map<String, Object>> find(String handicapMemberId, String gameId, Integer pageSize,
			String tableName) throws SQLException {
		String sqlCount = "select count(*) from ibm_exec_bet_item where HANDICAP_MEMBER_ID_=? and GAME_ID_=? and STATE_=? ";
		String sql =
				"SELECT bet.PERIOD_, bet.PLAN_GROUP_DESC_, bet.FUND_T_, bet.BET_CONTENT_, bet.FUNDS_INDEX_, pk10.DRAW_NUMBER_, bet.EXEC_STATE_, bet.PROFIT_T_, bet.ODDS_ "
						+ " FROM ibm_exec_bet_item bet LEFT JOIN " + tableName
						+ " pk10 ON bet.PERIOD_ = pk10.PERIOD_ WHERE "
						+ "bet.HANDICAP_MEMBER_ID_ = ? AND bet.GAME_ID_ = ? AND bet.STATE_ = ? ORDER BY bet.CREATE_TIME_ DESC ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return this.dao.page(sql, parameterList, 0, pageSize, sqlCount, parameterList);
	}

	/**
	 * 获取需要发送的信息
	 *
	 * @param planId    方案id
	 * @param period    期数
	 * @param tableName 表名
	 * @return 转码信息
	 */
	public Map<String, JSONObject> findSendInfo(String planId, Object period, String tableName) throws SQLException {
		String sql = "select HANDICAP_MEMBER_ID_,IBM_EXEC_BET_ITEM_ID_ ,BET_CONTENT_,FUND_T_ from " + tableName
				+ " where PLAN_ID_ = ? and PERIOD_= ? AND BET_MODE_ =?"
				+ " and EXEC_STATE_ = ? and STATE_ != ? order by HANDICAP_MEMBER_ID_";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(planId);
		parameterList.add(period);
		parameterList.add(IbmTypeEnum.REAL.name());
		parameterList.add(IbmTypeEnum.READY.name());
		parameterList.add(IbmStateEnum.DEL.name());
		List<Map<String, Object>> info = super.dao.findMapList(sql, parameterList);
		if (ContainerTool.isEmpty(info)) {
			return null;
		}
		Map<String, JSONObject> sendInfo = new HashMap<>(info.size() / 2);

		info.forEach(map -> {
			String handicapMemberId = map.get("HANDICAP_MEMBER_ID_").toString();
			String execBetItemId = map.get("IBM_EXEC_BET_ITEM_ID_").toString();
			JSONObject jObj = JSONObject.fromObject(map);
			jObj.remove("HANDICAP_MEMBER_ID_");
			jObj.remove("ROW_NUM");
			if (sendInfo.containsKey(handicapMemberId)) {
				JSONObject send = sendInfo.get(handicapMemberId);
				send.put("EXEC_BET_ITEM_IDS_", send.getString("EXEC_BET_ITEM_IDS_").concat(",").concat(execBetItemId));
				send.getJSONArray("BET_INFO").add(jObj);
			} else {
				JSONObject send = new JSONObject();
				send.put("EXEC_BET_ITEM_IDS_", execBetItemId);
				JSONArray betInfo = new JSONArray();
				betInfo.add(jObj);
				send.put("BET_INFO", betInfo);
				sendInfo.put(handicapMemberId, send);
			}
		});

		return sendInfo;
	}

	/**
	 * 批量保存投注信息
	 *
	 * @param list      投注信息
	 * @param tableName 表名
	 */
	public void saveManualBet(List<Map<String, Object>> list, String tableName) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		sql.append("insert into ").append(tableName);
		sql.append(" (IBM_EXEC_BET_ITEM_ID_,HANDICAP_ID_,GAME_ID_,PLAN_ID_,"
				+ "HANDICAP_MEMBER_ID_,PERIOD_,FUND_T_,PLAN_ITEM_TABLE_ID_,PLAN_GROUP_KEY_,PLAN_GROUP_DESC_,BET_CONTENT_"
				+ ",BET_CONTENT_LEN_,BET_MODE_," + "BET_TYPE_,FUNDS_INDEX_,EXEC_STATE_,CREATE_TIME_,CREATE_TIME_LONG_,"
				+ "UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) values ");
		Date nowTime = new Date();
		for (Map<String, Object> execBetItem : list) {
			String execBetItemId = RandomTool.getNumLetter32();
			// 将ID回传
			execBetItem.put("IBM_EXEC_BET_ITEM_ID_", execBetItemId);
			sql.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameters.add(execBetItemId);
			parameters.add(execBetItem.get("HANDICAP_ID_"));
			parameters.add(execBetItem.get("GAME_ID_"));
			parameters.add("000_"+IbmStateEnum.MANUAL.name());
			parameters.add(execBetItem.get("HANDICAP_MEMBER_ID_"));
			parameters.add(execBetItem.get("PERIOD_"));
			parameters.add(NumberTool.intValueT(execBetItem.get("FUND_T_")));
			parameters.add(IbmStateEnum.MANUAL.name());
			parameters.add(IbmStateEnum.MANUAL.getMsg());
			parameters.add(IbmStateEnum.MANUAL.getMsgCn());
			parameters.add(execBetItem.get("BET_CONTENT_"));
			parameters.add(execBetItem.get("BET_CONTENT_LEN_"));
			parameters.add(IbmTypeEnum.REAL.name());
			parameters.add(PlanTool.BET_TYPE_MANUAL);
			parameters.add(1);
			parameters.add(IbmStateEnum.READY.name());
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(IbmStateEnum.OPEN.name());
		}
		sql.deleteCharAt(sql.length() - 1);
		dao.execute(sql.toString(), parameters);
	}

	/**
	 * 批量修改执行状态
	 *
	 * @param execBetItemIds 执行投注项主键数组
	 * @param period         期数
	 * @param execState      执行状态
	 * @param tableName      表名
	 */
	public void updateExecState(Object[] execBetItemIds, String period, String execState, String tableName)
			throws SQLException {
		String sql = "update " + tableName
				+ " set EXEC_STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_EXEC_BET_ITEM_ID_ in(";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(execState);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		StringBuilder sqlBuilder = new StringBuilder();
		for (Object execBetItemId : execBetItemIds) {
			sqlBuilder.append("?,");
			parameterList.add(execBetItemId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(" ) and PERIOD_ = ? AND EXEC_STATE_ != ?");
		sql = sql.concat(sqlBuilder.toString());
		parameterList.add(period);
		parameterList.add(IbmStateEnum.FAIL.name());

		this.dao.execute(sql, parameterList);
	}

	/**
	 * 更新执行描述
	 *
	 * @param betInfo   投注信息
	 * @param tableName 表名
	 */
	public void updateExecFailDesc(Map<String, List<String>> betInfo, String tableName) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder("UPDATE ");
		sqlBuilder.append(tableName).append(" SET EXEC_STATE_ = ?, DESC_ = CASE IBM_EXEC_BET_ITEM_ID_ ");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.FAIL.name());
		for (Map.Entry<String, List<String>> entry : betInfo.entrySet()) {
			sqlBuilder.append(" when ? THEN ?");
			parameterList.add(entry.getKey());
			parameterList.add(String.join(",", entry.getValue()));
		}
		sqlBuilder.append(" end,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where IBM_EXEC_BET_ITEM_ID_ in( ");
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for (Map.Entry<String, List<String>> entry : betInfo.entrySet()) {
			sqlBuilder.append("?,");
			parameterList.add(entry.getKey());
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(","));
		sqlBuilder.append(")");
		this.dao.execute(sqlBuilder.toString(), parameterList);
	}

	/**
	 * 获取需要合并信息
	 *
	 * @param period 期数
	 * @return 需要合并的信息
	 */
	public Map<String, Map<String, int[][]>> listBallMergeInfo(Object period, String tableName) throws SQLException {
		//待合并的信息
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT iebi.HANDICAP_MEMBER_ID_,iebi.IBM_EXEC_BET_ITEM_ID_,iebi.BET_CONTENT_,iebi.BET_MODE_,iebi.FUND_T_ FROM ");
		sql.append(tableName);
		sql.append(" iebi LEFT JOIN ibm_hm_set ihs ON iebi.HANDICAP_MEMBER_ID_=ihs.HANDICAP_MEMBER_ID_ WHERE ");
		sql.append(
				" iebi.BET_TYPE_ = ? AND iebi.PERIOD_ = ? AND iebi.EXEC_STATE_ = ? AND ihs.BET_MERGER_ = ? AND iebi.STATE_ != ?");
		sql.append(" ORDER BY iebi.HANDICAP_MEMBER_ID_");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(PlanTool.BET_TYPE_CODE);
		parameterList.add(period);
		parameterList.add(IbmTypeEnum.READY.name());
		parameterList.add(IbmTypeEnum.OPEN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		List<Map<String, Object>> info = super.dao.findMapList(sql.toString(), parameterList);
		if (ContainerTool.isEmpty(info)) {
			return null;
		}
		Map<String, Map<String, int[][]>> turnInfo = new HashMap<>();
		List<String> execBetItemIds = new ArrayList<>();

		info.forEach(turnMap -> {
			String handicapMemberId = turnMap.get("HANDICAP_MEMBER_ID_").toString();
			putTurnInfo(turnInfo, execBetItemIds, turnMap, handicapMemberId);

		});
		sql.delete(0, sql.length());
		parameterList.clear();
		//将记录改为合并
		sql.append("update ").append(tableName);
		sql.append(" set EXEC_STATE_ = ?,STATE_ = ? ,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? ,BET_TYPE_ = ?");
		sql.append(" where IBM_EXEC_BET_ITEM_ID_ in('").append(String.join("','", execBetItemIds)).append("')");
		parameterList.add(IbmTypeEnum.MERGE.name());
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(PlanTool.BET_TYPE_MERGE);
		super.dao.execute(sql.toString(), parameterList);

		return turnInfo;
	}
	/**
	 * 获取需要合并信息
	 *
	 * @param period           期数
	 * @param handicapMemberId 盘口会员id
	 * @param tableName        表名
	 * @return 需要合并的信息
	 */
	public Map<String, Map<String, int[][]>> listMergeInfoByHm(Object period, String tableName, String handicapMemberId)
			throws SQLException {
		//待合并的信息
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT iebi.IBM_EXEC_BET_ITEM_ID_, iebi.BET_CONTENT_,iebi.BET_MODE_,iebi.FUND_T_");
		sql.append(" FROM ").append(tableName);
		sql.append(" iebi LEFT JOIN ibm_hm_set ihs ON iebi.HANDICAP_MEMBER_ID_ = ihs.HANDICAP_MEMBER_ID_");
		sql.append(" WHERE iebi.HANDICAP_MEMBER_ID_ = ? AND iebi.BET_TYPE_ = ?");
		sql.append(" AND iebi.PERIOD_ = ? AND iebi.EXEC_STATE_ = ? AND ihs.BET_MERGER_ = ? AND iebi.STATE_ != ? ");
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(handicapMemberId);
		parameterList.add(PlanTool.BET_TYPE_CODE);
		parameterList.add(period);
		parameterList.add(IbmTypeEnum.READY.name());
		parameterList.add(IbmTypeEnum.OPEN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		List<Map<String, Object>> info = super.dao.findMapList(sql.toString(), parameterList);
		if (ContainerTool.isEmpty(info)) {
			return null;
		}
		Map<String, Map<String, int[][]>> turnInfo = new HashMap<>();
		List<String> execBetItemIds = new ArrayList<>();

		info.forEach(turnMap -> putTurnInfo(turnInfo, execBetItemIds, turnMap, handicapMemberId));
		sql.delete(0, sql.length());
		parameterList.clear();

		//将记录改为合并
		sql.append("update ").append(tableName);
		sql.append(" set EXEC_STATE_ = ? ,STATE_ = ? ,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? ,BET_TYPE_ = ?");
		sql.append(" where IBM_EXEC_BET_ITEM_ID_ in('").append(String.join("','", execBetItemIds)).append("')");
		parameterList.add(IbmTypeEnum.MERGE.name());
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(PlanTool.BET_TYPE_MERGE);
		super.dao.execute(sql.toString(), parameterList);

		return turnInfo;
	}
	/**
	 * 放入转码信息
	 *
	 * @param turnInfo         转码信息
	 * @param execBetItemIds   执行项IDs
	 * @param turnMap          转码信息集
	 * @param handicapMemberId 盘口会员id
	 */
	private void putTurnInfo(Map<String, Map<String, int[][]>> turnInfo, List<String> execBetItemIds,
			Map<String, Object> turnMap, String handicapMemberId) {
		String betMode = turnMap.get("BET_MODE_").toString();
		String key = handicapMemberId + "#" + betMode;
		String[] betContents = turnMap.get("BET_CONTENT_").toString().split("#");
		int fundsT = Integer.parseInt(turnMap.get("FUND_T_").toString());
		execBetItemIds.add(turnMap.get("IBM_EXEC_BET_ITEM_ID_").toString());
		Map<String, int[][]> turn;
		String typeKey;
		Integer typeIndex;
		if (turnInfo.containsKey(key)) {
			turn = turnInfo.get(key);
		} else {
			turn = new HashMap<>(5);
		}
		for (String betContent : betContents) {
			String[] bets = betContent.split("\\|");
			int index = StringTool.getRankCn(bets[0]);
			//冠亚和
			if (index == 0) {
				Integer championSumIndex = GameTool.getChampionSumIndex(bets[1]);
				putFunds(fundsT, turn, "CHAMPION_SUM", championSumIndex, index, 1, 21);
				continue;
			}
			//去除第一个冠亚,从0开始
			index--;
			int type = StringTool.getTypeCn(bets[1]);
			typeKey = StringTool.getTypeEn(type);
			switch (type) {
				case 1:
					typeIndex = GameTool.sizeIndex(bets[1]);
					putFunds(fundsT, turn, typeKey, typeIndex, index, 10, 2);
					break;
				case 2:
					typeIndex = GameTool.parityIndex(bets[1]);
					putFunds(fundsT, turn, typeKey, typeIndex, index, 10, 2);
					break;
				case 3:
					typeIndex = GameTool.dragonIndex(bets[1]);
					putFunds(fundsT, turn, typeKey, typeIndex, index, 5, 2);
					break;
				default:
					typeKey = StringTool.getTypeEn(0);
					typeIndex = Integer.parseInt(bets[1]) - 1;
					putFunds(fundsT, turn, typeKey, typeIndex, index, 10, 10);
					break;
			}
		}
		turnInfo.put(key, turn);
	}

	/**
	 * 放入资金<br>
	 * 往turn 对象中key所对应的值的数组[index][typeIndex] 中加入资金
	 *
	 * @param fundsT    金额
	 * @param turn      存储map
	 * @param key       类型key
	 * @param typeIndex 类型索引
	 * @param index     位置索引
	 * @param x         排名范围
	 * @param y         类型范围
	 */
	private void putFunds(int fundsT, Map<String, int[][]> turn, String key, Integer typeIndex, int index, int x,
			int y) {
		if (typeIndex == null) {
			return;
		}
		if (turn.containsKey(key)) {
			turn.get(key)[index][typeIndex] += fundsT;
		} else {
			int[][] arr = new int[x][y];
			arr[index][typeIndex] = fundsT;
			turn.put(key, arr);
		}
	}

	/**
	 * 更新处理结果
	 *
	 * @param resultInfo 结果信息
	 * @param drawNumber 开奖号码
	 * @param tableName  表名
	 * @return 执行盘口会员方案组主键
	 */
	public Map<String, Object> updateReceiptResult(Map<String, Object> resultInfo, String drawNumber, String tableName)
			throws SQLException {
		String sql = "UPDATE " + tableName + " SET PROFIT_T_ = ?,ODDS_ = ?, EXEC_STATE_ = ?, UPDATE_TIME_ = ?, "
				+ " UPDATE_TIME_LONG_ = ?,DRAW_NUMBER_ = ? WHERE IBM_EXEC_BET_ITEM_ID_ = ? AND EXEC_STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(resultInfo.get("PROFIT_T_"));
		parameterList.add(resultInfo.get("ODDS_"));
		parameterList.add(IbmStateEnum.FINISH.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(drawNumber);
		parameterList.add(resultInfo.get("EXEC_BET_ITEM_ID_"));
		parameterList.add(IbmStateEnum.SUCCESS.name());
		int col = super.dao.execute(sql, parameterList);
		if (col != 1) {
			return null;
		}
		//投注结果
		sql = "SELECT EXEC_PLAN_GROUP_ID_,PERIOD_,PROFIT_T_,FUND_T_,BET_CONTENT_LEN_,IBM_EXEC_BET_ITEM_ID_ as "
				+ " EXEC_BET_ITEM_ID_,HANDICAP_MEMBER_ID_,PLAN_ID_ FROM `ibm_exec_bet_item` where IBM_EXEC_BET_ITEM_ID_ = ? "
				+ " and EXEC_PLAN_GROUP_ID_ is not null ";
		parameterList.clear();
		parameterList.add(resultInfo.get("EXEC_BET_ITEM_ID_"));
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 更新处理结果
	 *
	 * @param resultInfo 结果信息
	 * @param drawNumber 开奖号码
	 * @param tableName  表名
	 */
	public void updateResult(Map<String, Object> resultInfo, String drawNumber, String tableName) throws SQLException {
		String sql = "UPDATE " + tableName + " SET PROFIT_T_ = ? ,ODDS_ = ? ,EXEC_STATE_ = ? ,UPDATE_TIME_ = ?, "
				+ " UPDATE_TIME_LONG_ = ?,DRAW_NUMBER_ = ? WHERE IBM_EXEC_BET_ITEM_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(resultInfo.get("PROFIT_T_"));
		parameterList.add(resultInfo.get("ODDS_"));
		parameterList.add(IbmStateEnum.FINISH.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(drawNumber);
		parameterList.add(resultInfo.get("EXEC_BET_ITEM_ID_"));
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取某个盘口的真实信息
	 *
	 * @param period    期数
	 * @param tableName 表名
	 * @return 合并信息
	 */
	public Map<String, List<Map<String, Object>>> mapExecBetItemInfo(Object period, String tableName)
			throws SQLException {
		return mapExecBetItem(period, IbmTypeEnum.SUCCESS, IbmTypeEnum.REAL, IbmStateEnum.OPEN, tableName);
	}

	/**
	 * 获取某个盘口的模拟信息
	 *
	 * @param period    期数
	 * @param tableName 表名
	 * @return 模拟信息
	 */
	public Map<String, List<Map<String, Object>>> mapVrExecBetItemInfo(Object period, String tableName)
			throws SQLException {
		return mapExecBetItem(period, IbmTypeEnum.READY, IbmTypeEnum.VIRTUAL, IbmStateEnum.OPEN, tableName);

	}

	/**
	 * 获取某个盘口的真实合并信息
	 *
	 * @param period    期数
	 * @param tableName 表名
	 * @return 合并信息
	 */
	public Map<String, List<Map<String, Object>>> mapMergeExecBetItemInfo(Object period, String tableName)
			throws SQLException {
		return mapExecBetItem(period, IbmTypeEnum.MERGE, IbmTypeEnum.REAL, IbmStateEnum.CLOSE, tableName);

	}
	/**
	 * 获取某个盘口的模拟合并信息
	 *
	 * @param period    期数
	 * @param tableName 表名
	 * @return 合并信息
	 */
	public Map<String, List<Map<String, Object>>> mapMergeVrExecBetItemInfo(Object period, String tableName)
			throws SQLException {
		return mapExecBetItem(period, IbmTypeEnum.MERGE, IbmTypeEnum.VIRTUAL, IbmStateEnum.CLOSE, tableName);
	}

	/**
	 * 获取投注项执行信息
	 *
	 * @param period    期数
	 * @param merge     是否合并
	 * @param type      是否虚拟
	 * @param close     是否关闭
	 * @param tableName 表名
	 * @return 执行信息
	 */
	private Map<String, List<Map<String, Object>>> mapExecBetItem(Object period, IbmTypeEnum merge, IbmTypeEnum type,
			IbmStateEnum close, String tableName) throws SQLException {
		String sql = "SELECT EXEC_PLAN_GROUP_ID_, IBM_EXEC_BET_ITEM_ID_ as EXEC_BET_ITEM_ID_, PLAN_ID_,FUNDS_INDEX_, "
				+ " PLAN_ITEM_TABLE_ID_,PLAN_GROUP_KEY_,HANDICAP_MEMBER_ID_, FUND_T_, BET_CONTENT_, BET_CONTENT_LEN_ FROM  "
				+ tableName + " WHERE PERIOD_ = ? AND EXEC_STATE_ = ? AND BET_MODE_ = ? "
				+ " and EXEC_PLAN_GROUP_ID_ != '' and STATE_ = ? ORDER BY HANDICAP_MEMBER_ID_";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(period);
		parameterList.add(merge.name());
		parameterList.add(type.name());
		parameterList.add(close.name());
		List<Map<String, Object>> execBetItemList = super.dao.findMapList(sql, parameterList);

		Map<String, List<Map<String, Object>>> mapExecBetItem = new HashMap<>(execBetItemList.size() / 4);
		if (ContainerTool.isEmpty(execBetItemList)) {
			return mapExecBetItem;
		}
		execBetItemList.forEach(execBetItem -> {
			String handicapMemberId = execBetItem.get("HANDICAP_MEMBER_ID_").toString();
			execBetItem.remove("HANDICAP_MEMBER_ID_");
			if (mapExecBetItem.containsKey(handicapMemberId)) {
				mapExecBetItem.get(handicapMemberId).add(execBetItem);
			} else {
				List<Map<String, Object>> execBetItems = new ArrayList<>(4);
				execBetItems.add(execBetItem);
				mapExecBetItem.put(handicapMemberId, execBetItems);
			}
		});

		return mapExecBetItem;
	}
	/**
	 * 获取合并后的新的真实投注项执行信息
	 *
	 * @param period    期数
	 * @param tableName 表名
	 * @return 执行信息
	 */
	public Map<String, List<Map<String, Object>>> mapMergeNewInfo(Object period, String tableName) throws SQLException {
		return mapMergeExecBetItem(period, IbmTypeEnum.REAL, tableName);
	}

	/**
	 * 获取合并后的新的模拟投注项执行信息
	 *
	 * @param period    期数
	 * @param tableName 表名
	 * @return 执行信息
	 */
	public Map<String, List<Map<String, Object>>> mapMergeVrNewInfo(Object period, String tableName)
			throws SQLException {
		return mapMergeExecBetItem(period, IbmTypeEnum.VIRTUAL, tableName);
	}

	/**
	 * 获取合并后的新的投注项执行信息
	 *
	 * @param period    期数
	 * @param type      是否虚拟
	 * @param tableName 表名
	 * @return 执行信息
	 */
	private Map<String, List<Map<String, Object>>> mapMergeExecBetItem(Object period, IbmTypeEnum type,
			String tableName) throws SQLException {
		String sql = "SELECT EXEC_PLAN_GROUP_ID_, IBM_EXEC_BET_ITEM_ID_ as EXEC_BET_ITEM_ID_, PLAN_ID_, "
				+ " PLAN_ITEM_TABLE_ID_,PLAN_GROUP_KEY_,HANDICAP_MEMBER_ID_, FUND_T_, BET_CONTENT_, BET_CONTENT_LEN_ FROM  "
				+ tableName + " WHERE PERIOD_ = ? AND (EXEC_STATE_ = ? OR EXEC_STATE_ = ?) AND BET_MODE_ = ? "
				+ " and EXEC_PLAN_GROUP_ID_ is null and STATE_ = ? ORDER BY HANDICAP_MEMBER_ID_";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(period);
		parameterList.add(IbmTypeEnum.READY.name());
		parameterList.add(IbmTypeEnum.SUCCESS.name());
		parameterList.add(type.name());
		parameterList.add(IbmTypeEnum.OPEN.name());
		List<Map<String, Object>> execBetItemList = super.dao.findMapList(sql, parameterList);

		Map<String, List<Map<String, Object>>> mapExecBetItem = new HashMap<>(execBetItemList.size() / 4);
		if (ContainerTool.isEmpty(execBetItemList)) {
			return mapExecBetItem;
		}
		execBetItemList.forEach(execBetItem -> {
			String handicapMemberId = execBetItem.get("HANDICAP_MEMBER_ID_").toString();
			execBetItem.remove("HANDICAP_MEMBER_ID_");
			if (mapExecBetItem.containsKey(handicapMemberId)) {
				mapExecBetItem.get(handicapMemberId).add(execBetItem);
			} else {
				List<Map<String, Object>> execBetItems = new ArrayList<>(4);
				execBetItems.add(execBetItem);
				mapExecBetItem.put(handicapMemberId, execBetItems);
			}
		});

		return mapExecBetItem;

	}

	/**
	 * 获取需要发送的合并信息
	 *
	 * @param period    期数
	 * @param tableName 表名
	 * @return 需要发送的合并信息
	 */
	public Map<String, JSONObject> listSendInfoByMerge(Object period, String tableName) throws SQLException {
		String sql = "select HANDICAP_MEMBER_ID_,IBM_EXEC_BET_ITEM_ID_ ,BET_CONTENT_,FUND_T_ from " + tableName
				+ " where BET_TYPE_= ? and PERIOD_= ? AND BET_MODE_ = ? "
				+ " and EXEC_STATE_ = ? and STATE_ != ? order by HANDICAP_MEMBER_ID_";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(3);
		parameterList.add(period);
		parameterList.add(IbmTypeEnum.REAL.name());
		parameterList.add(IbmTypeEnum.READY.name());
		parameterList.add(IbmStateEnum.DEL.name());
		List<Map<String, Object>> info = super.dao.findMapList(sql, parameterList);
		if (ContainerTool.isEmpty(info)) {
			return null;
		}
		Map<String, JSONObject> sendInfo = new HashMap<>(info.size() / 2);

		info.forEach(map -> {
			String handicapMemberId = map.get("HANDICAP_MEMBER_ID_").toString();
			String execBetItemId = map.get("IBM_EXEC_BET_ITEM_ID_").toString();
			JSONObject jObj = JSONObject.fromObject(map);
			jObj.remove("HANDICAP_MEMBER_ID_");
			jObj.remove("ROW_NUM");
			if (sendInfo.containsKey(handicapMemberId)) {
				JSONObject send = sendInfo.get(handicapMemberId);
				send.put("EXEC_BET_ITEM_IDS_", send.getString("EXEC_BET_ITEM_IDS_").concat(",").concat(execBetItemId));
				send.getJSONArray("BET_INFO").add(jObj);
			} else {
				JSONObject send = new JSONObject();
				send.put("EXEC_BET_ITEM_IDS_", execBetItemId);
				JSONArray betInfo = new JSONArray();
				betInfo.add(jObj);
				send.put("BET_INFO", betInfo);
				sendInfo.put(handicapMemberId, send);
			}
		});

		return sendInfo;
	}

	/**
	 * 获取盘口会员需要发送的信息
	 *
	 * @param period           期数
	 * @param handicapMemberId 盘口会员id
	 * @param tableName        表名
	 * @return 转码信息
	 */
	public JSONObject listSendInfoByHm(Object period, String tableName, String handicapMemberId) throws SQLException {
		String sql = "select IBM_EXEC_BET_ITEM_ID_ ,BET_CONTENT_,FUND_T_ from " + tableName
				+ " where HANDICAP_MEMBER_ID_ = ? and PERIOD_= ? AND BET_MODE_ = ? "
				+ " and EXEC_STATE_ = ? and STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(handicapMemberId);
		parameterList.add(period);
		parameterList.add(IbmTypeEnum.REAL.name());
		parameterList.add(IbmTypeEnum.READY.name());
		parameterList.add(IbmStateEnum.DEL.name());
		List<Map<String, Object>> info = super.dao.findMapList(sql, parameterList);
		if (ContainerTool.isEmpty(info)) {
			return null;
		}
		JSONObject send = new JSONObject();
		info.forEach(map -> {
			String execBetItemId = map.get("IBM_EXEC_BET_ITEM_ID_").toString();
			JSONObject jObj = JSONObject.fromObject(map);
			jObj.remove("ROW_NUM");
			if (ContainerTool.notEmpty(send)) {
				send.put("EXEC_BET_ITEM_IDS_", send.getString("EXEC_BET_ITEM_IDS_").concat(",").concat(execBetItemId));
				send.getJSONArray("BET_INFO").add(jObj);
			} else {
				send.put("EXEC_BET_ITEM_IDS_", execBetItemId);
				JSONArray betInfo = new JSONArray();
				betInfo.add(jObj);
				send.put("BET_INFO", betInfo);
			}
		});
		return send;
	}

	/**
	 * @param handicapMemberId 盘口会员ID
	 * @param period           期数
	 * @param tableName        表名
	 * @Description: 通过盘口会员ID逻辑删除
	 * <p>
	 * 参数说明
	 */
	public void delByHmIdAndPeriod(String handicapMemberId, Object period, String tableName) throws SQLException {
		String sql = "update " + tableName
				+ " set STATE_ = ?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_MEMBER_ID_ = ? AND PERIOD_ = ? AND EXEC_STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.READY.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 迁移数据
	 *
	 * @param nowTime 迁移时间
	 */
	public void migration(Date nowTime) throws SQLException {
		String sql = "INSERT INTO ibm_exec_bet_item_old (IBM_EXEC_BET_ITEM_OLD_ID_,HANDICAP_ID_,GAME_ID_,PLAN_ID_, "
				+ " HANDICAP_MEMBER_ID_,PERIOD_,FUND_T_,PLAN_GROUP_DESC_,BET_CONTENT_,BET_MODE_,DRAW_NUMBER_, "
				+ " FUNDS_INDEX_,EXEC_STATE_,PROFIT_T_,ODDS_,CREATE_TIME_,CREATE_TIME_LONG_,STATE_,DESC_)( "
				+ " SELECT IBM_EXEC_BET_ITEM_ID_,HANDICAP_ID_,GAME_ID_,PLAN_ID_,HANDICAP_MEMBER_ID_,PERIOD_, "
				+ " FUND_T_,PLAN_GROUP_DESC_,BET_CONTENT_,BET_MODE_,DRAW_NUMBER_,FUNDS_INDEX_,EXEC_STATE_, "
				+ " PROFIT_T_,ODDS_,CREATE_TIME_,CREATE_TIME_LONG_,STATE_,DESC_ FROM ibm_exec_bet_item "
				+ " WHERE CREATE_TIME_LONG_ < ?);";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(nowTime.getTime());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 清理冗余数据
	 *
	 * @param nowTime 清理时间
	 */
	public void clearRedundancy(Date nowTime) throws SQLException {
		String sql = "DELETE FROM ibm_exec_bet_item WHERE CREATE_TIME_LONG_ < ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(nowTime.getTime());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 保存数据
	 *
	 * @param execBetItemT 实体类
	 * @param tableName    表名
	 */
	public void save(IbmExecBetItemT execBetItemT, String tableName) throws SQLException {
		String sql = "INSERT INTO ".concat(tableName)
				.concat(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		List<Object> parameterList = new ArrayList<>(26);
		parameterList.add(0);
		parameterList.add(RandomTool.getNumLetter32());
		parameterList.add(execBetItemT.getExecPlanGroupId());
		parameterList.add(execBetItemT.getHandicapId());
		parameterList.add(execBetItemT.getGameId());
		parameterList.add(execBetItemT.getPlanId());
		parameterList.add(execBetItemT.getHandicapMemberId());
		parameterList.add(execBetItemT.getPeriod());
		parameterList.add(execBetItemT.getFundT());
		parameterList.add(execBetItemT.getPlanItemTableId());
		parameterList.add(execBetItemT.getPlanGroupKey());
		parameterList.add(execBetItemT.getPlanGroupDesc());
		parameterList.add(execBetItemT.getBetContent());
		parameterList.add(execBetItemT.getBetContentCode());
		parameterList.add(execBetItemT.getBetContentLen());
		parameterList.add(execBetItemT.getBetMode());
		parameterList.add(execBetItemT.getBetType());
		parameterList.add(execBetItemT.getDrawNumber());
		parameterList.add(execBetItemT.getFundsIndex());
		parameterList.add(execBetItemT.getExecState());
		parameterList.add(execBetItemT.getProfitT());
		parameterList.add(execBetItemT.getOdds());
		parameterList.add(execBetItemT.getCreateTime());
		parameterList.add(execBetItemT.getCreateTimeLong());
		parameterList.add(execBetItemT.getUpdateTime());
		parameterList.add(execBetItemT.getUpdateTimeLong());
		parameterList.add(execBetItemT.getState());
		parameterList.add(execBetItemT.getDesc());
		super.dao.execute(sql, parameterList);
	}

	//TODO  测试区

	/**
	 * 获取测试数据
	 *
	 * @return 测试数据
	 */
	public List list() throws Exception {
		String sql = " select * FROM sub_iebi_idc_xyft";
		return super.dao.findObjectList(IbmExecBetItemT.class, sql);
	}
	/**
	 * 保存大数据
	 *
	 * @param list 测试数据
	 * @param size 测试数据长度
	 * @param len  长度
	 */
	public void saveBigData(List list, int size, int len) throws SQLException {
		StringBuilder sql = new StringBuilder("INSERT INTO sub_iebi_ws2_xyft VALUES ");
		List<Object> parameterList = new ArrayList<>(len * 25);
		IbmExecBetItemT execBetItemT;
		Date nowTime = new Date();
		for (int i = 0; i < len; i++) {
			execBetItemT = (IbmExecBetItemT) list.get(RandomTool.getInt(size));
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(execBetItemT.getIdx());
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(execBetItemT.getExecPlanGroupId());
			parameterList.add(execBetItemT.getHandicapId());
			parameterList.add(execBetItemT.getGameId());
			parameterList.add(execBetItemT.getPlanId());
			parameterList.add(execBetItemT.getHandicapMemberId());
			parameterList.add(execBetItemT.getPeriod());
			parameterList.add(execBetItemT.getFundT());
			parameterList.add(execBetItemT.getPlanGroupDesc());
			parameterList.add(execBetItemT.getBetContent());
			parameterList.add(execBetItemT.getBetContentCode());
			parameterList.add(execBetItemT.getBetContentLen());
			parameterList.add(execBetItemT.getBetMode());
			parameterList.add(execBetItemT.getBetType());
			parameterList.add(execBetItemT.getDrawNumber());
			parameterList.add(execBetItemT.getFundsIndex());
			parameterList.add(execBetItemT.getExecState());
			parameterList.add(execBetItemT.getProfitT());
			parameterList.add(execBetItemT.getOdds());
			parameterList.add(nowTime);
			parameterList.add(System.currentTimeMillis());
			parameterList.add(nowTime);
			parameterList.add(System.currentTimeMillis());
			parameterList.add(execBetItemT.getState());
			parameterList.add(execBetItemT.getDesc());
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 保存大数据
	 *
	 * @param list  测试数据
	 * @param idMap 盘口会员id
	 * @param len   长度
	 */
	public void saveBigData(List list, Map<String, Object> idMap, int len) throws SQLException {
		String handicapMemberId = idMap.get("IBM_HANDICAP_MEMBER_ID_").toString();
		String handicapId = idMap.get("HANDICAP_ID_").toString();

		StringBuilder sql = new StringBuilder("INSERT INTO sub_iebi_ws2_xyft VALUES ");
		List<Object> parameterList = new ArrayList<>(len * 25);

		IbmExecBetItemT execBetItemT;
		Date nowTime = new Date();
		int size = list.size();
		Object period = PeriodTool.findXYFTPeriod();
		for (int i = 0; i < len; i++) {
			execBetItemT = (IbmExecBetItemT) list.get(RandomTool.getInt(size));
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(execBetItemT.getIdx());
			parameterList.add(RandomTool.getNumLetter32().concat("_") + System.nanoTime());
			parameterList.add(execBetItemT.getExecPlanGroupId());
			parameterList.add(handicapId);
			parameterList.add(execBetItemT.getGameId());
			parameterList.add(execBetItemT.getPlanId());
			parameterList.add(handicapMemberId);
			parameterList.add(period);
			parameterList.add(execBetItemT.getFundT());
			parameterList.add(execBetItemT.getPlanGroupDesc());
			parameterList.add(execBetItemT.getBetContent());
			parameterList.add(execBetItemT.getBetContentCode());
			parameterList.add(execBetItemT.getBetContentLen());
			parameterList.add(execBetItemT.getBetMode());
			parameterList.add(execBetItemT.getBetType());
			parameterList.add(execBetItemT.getDrawNumber());
			parameterList.add(execBetItemT.getFundsIndex());
			parameterList.add(execBetItemT.getExecState());
			parameterList.add(execBetItemT.getProfitT());
			parameterList.add(execBetItemT.getOdds());
			parameterList.add(nowTime);
			parameterList.add(System.currentTimeMillis());
			parameterList.add(nowTime);
			parameterList.add(System.currentTimeMillis());
			parameterList.add(execBetItemT.getState());
			parameterList.add(execBetItemT.getDesc());
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		super.dao.execute(sql.toString(), parameterList);

	}

	/**
	 * 获取待迁移总数
	 *
	 * @param endTime 迁移最终时间
	 * @return 待迁移总数
	 */
	public int findMigrationCnt(long endTime) throws SQLException {
		String sql = "SELECT count(*) CNT FROM ibm_exec_bet_item " + " WHERE CREATE_TIME_LONG_ < ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(endTime);
		Map<String, Object> cntMap = super.dao.findMap(sql, parameterList);

		return NumberTool.getInteger(cntMap.get("CNT"));
	}

	/**
	 * 清理冗余数据
	 *
	 * @param endTime 清迁移最终时间
	 */
	public void clearRedundancy(long endTime) throws SQLException {
		String sql = "DELETE FROM ibm_exec_bet_item WHERE CREATE_TIME_LONG_ < ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(endTime);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 生成备份表
	 *
	 * @param day 日期
	 */
	public void generateBakTable(String day) throws SQLException {
		String tableName = "ibm_exec_bet_item_".concat(day);
		String sql = "DROP TABLE IF EXISTS ".concat(tableName);
		super.dao.execute(sql, null);
		sql = "CREATE TABLE ".concat(tableName).concat(" LIKE ibm_exec_bet_item_");
		super.dao.execute(sql, null);
	}

	/**
	 * 异库迁移
	 *
	 * @param migrationInfoList 迁移数据
	 */
	public void otherMigration(List<Map<String, Object>> migrationInfoList) throws SQLException {
		String tableName = "ibm_exec_bet_item_".concat(DateTool.getDay(new Date()));
		StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName)
				.append("(IBM_EXEC_BET_ITEM_ID_,HANDICAP_ID_,GAME_ID_,PLAN_ID_,HANDICAP_MEMBER_ID_,PERIOD_,FUND_T_,PLAN_GROUP_DESC_,BET_CONTENT_,BET_MODE_,DRAW_NUMBER_,FUNDS_INDEX_,EXEC_STATE_,PROFIT_T_,ODDS_,CREATE_TIME_,CREATE_TIME_LONG_,STATE_,DESC_) VALUES");
		List<Object> parameterList = new ArrayList<>(migrationInfoList.size() * 25);
		for (Map<String, Object> migrationInfo : migrationInfoList) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(migrationInfo.get("IBM_EXEC_BET_ITEM_ID_"));
			parameterList.add(migrationInfo.get("HANDICAP_ID_"));
			parameterList.add(migrationInfo.get("GAME_ID_"));
			parameterList.add(migrationInfo.get("PLAN_ID_"));
			parameterList.add(migrationInfo.get("HANDICAP_MEMBER_ID_"));
			parameterList.add(migrationInfo.get("PERIOD_"));
			parameterList.add(migrationInfo.get("FUND_T_"));
			parameterList.add(migrationInfo.get("PLAN_GROUP_DESC_"));
			parameterList.add(migrationInfo.get("BET_CONTENT_"));
			parameterList.add(migrationInfo.get("BET_MODE_"));
			parameterList.add(migrationInfo.get("DRAW_NUMBER_"));
			parameterList.add(migrationInfo.get("FUNDS_INDEX_"));
			parameterList.add(migrationInfo.get("EXEC_STATE_"));
			parameterList.add(migrationInfo.get("PROFIT_T_"));
			parameterList.add(migrationInfo.get("ODDS_"));
			parameterList.add(migrationInfo.get("CREATE_TIME_"));
			parameterList.add(migrationInfo.get("CREATE_TIME_LONG_"));
			parameterList.add(migrationInfo.get("STATE_"));
			parameterList.add(migrationInfo.get("DESC_"));
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 获取迁移信息列表
	 *
	 * @param endTime      迁移最终时间
	 * @param migrationNum 迁移数量
	 * @return 迁移信息列表
	 */
	public List<Map<String, Object>> listMigrationInfo(long endTime, int migrationNum) throws SQLException {
		String sql = "SELECT IBM_EXEC_BET_ITEM_ID_,HANDICAP_ID_,GAME_ID_,PLAN_ID_,HANDICAP_MEMBER_ID_,PERIOD_, "
				+ " FUND_T_,PLAN_GROUP_DESC_,BET_CONTENT_,BET_MODE_,DRAW_NUMBER_,FUNDS_INDEX_,EXEC_STATE_, "
				+ " PROFIT_T_,ODDS_,CREATE_TIME_,CREATE_TIME_LONG_,STATE_,DESC_ FROM ibm_exec_bet_item WHERE"
				+ " CREATE_TIME_LONG_ < ? LIMIT ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(endTime);
		parameterList.add(migrationNum);
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取当期投注总额和当期投注总数
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param period           期数
	 * @param tableName        表名
	 * @param data             data
	 */
	public void findSum(String handicapMemberId, Object period, String tableName, Map<String, Object> data)
			throws SQLException {
		String sql =
				" SELECT SUM(FUND_T_*BET_CONTENT_LEN_) as amount ,SUM(BET_CONTENT_LEN_) as number FROM " + tableName
						+ " WHERE PERIOD_= ? and HANDICAP_MEMBER_ID_ = ? " + " and STATE_ != ? and BET_TYPE_ != ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(period);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(PlanTool.BET_TYPE_MERGE);
		Map<String, Object> map = super.dao.findMap(sql, parameterList);

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
	 * 获取投注结果
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param tableName        表名
	 * @param periods          期数s
	 * @return 投注结果
	 */
	public List<Map<String, Object>> findBetResult(String handicapMemberId, String tableName, Object[] periods)
			throws SQLException {
		String sql = "SELECT PERIOD_, SUM(BET_CONTENT_LEN_) as betSum, SUM(PROFIT_T_ > 0) as winCount, "
				+ " SUM(PROFIT_T_ < 0)as failCount,SUM(FUND_T_ * BET_CONTENT_LEN_) as betAmountT,SUM(PROFIT_T_) as profitT"
				+ " FROM " + tableName
				+ " WHERE HANDICAP_MEMBER_ID_= ? and STATE_ != ? and BET_TYPE_ != ? and PERIOD_ IN (";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(PlanTool.BET_TYPE_MERGE);
		for (Object period : periods) {
			sql = sql.concat("?,");
			parameterList.add(period.toString());
		}
		sql = sql.substring(0, sql.lastIndexOf(',')).concat(") GROUP BY PERIOD_ ORDER BY UPDATE_TIME_LONG_ DESC");
		return super.dao.findMapList(sql, parameterList);
	}
}
