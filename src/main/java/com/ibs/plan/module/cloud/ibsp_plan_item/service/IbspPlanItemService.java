package com.ibs.plan.module.cloud.ibsp_plan_item.service;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.enums.IbsModeEnum;
import com.ibs.plan.module.cloud.ibsp_plan_item.entity.IbspPlanItem;
import com.ibs.plan.module.cloud.ibsp_plan_user.entity.IbspPlanUser;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @Description: 方案详情服务类
 * @Author: null
 * @Date: 2020-06-01 17:11
 * @Version: v1.0
 */
public class IbspPlanItemService extends BaseServiceProxy {
	/**
	 * 保存IBSP方案详情信息 对象数据
	 *
	 * @param entity IbspPlanItem对象数据
	 */
	public String save(IbspPlanItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_plan_item 的 IBSP_PLAN_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_plan_item set state_='DEL' where IBSP_PLAN_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PLAN_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_plan_item 的 IBSP_PLAN_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_plan_item set state_='DEL' where IBSP_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_plan_item  的 IBSP_PLAN_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_plan_item where IBSP_PLAN_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PLAN_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_plan_item 的 IBSP_PLAN_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_plan_item where IBSP_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspPlanItem实体信息
	 *
	 * @param entity IBSP方案详情信息 实体
	 */
	public void update(IbspPlanItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_plan_item表主键查找 IBSP方案详情信息 实体
	 *
	 * @param id ibsp_plan_item 主键
	 * @return IBSP方案详情信息 实体
	 */
	public IbspPlanItem find(String id) throws Exception {
		return dao.find(IbspPlanItem.class, id);
	}

	/**
	 * 获取方案详情信息
	 *
	 * @param planItemTableInfo 方案详情表信息
	 * @return
	 */
	public Map<String, Object> findPlanItemInfo(Map<String, Object> planItemTableInfo) throws SQLException {
		List<Object> parameters = new ArrayList<>();
		parameters.add(planItemTableInfo.get("PLAN_ITEM_TABLE_ID_"));
		String sql = "select PROFIT_LIMIT_MAX_,LOSS_LIMIT_MIN_,FUNDS_LIST_,FOLLOW_PERIOD_,MONITOR_PERIOD_,BET_MODE_,FUND_SWAP_MODE_"
				+ ",PERIOD_ROLL_MODE_,PLAN_GROUP_DATA_,PLAN_GROUP_ACTIVE_KEY_,EXPAND_INFO_ from ibs_plan_library."
				+ planItemTableInfo.get("PLAN_ITEM_TABLE_NAME_") + " where " + planItemTableInfo.get("PLAN_ITEM_TABLE_NAME_")
				+ "_ID_=?";
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 修改方案详情信息
	 *
	 * @param planItemTableInfo 方案详情表信息
	 * @param planItem          详情信息
	 */
	public void update(Map<String, Object> planItemTableInfo, PlanItem planItem) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("update ibs_plan_library.").append(planItemTableInfo.get("PLAN_ITEM_TABLE_NAME_"))
				.append(" set PROFIT_LIMIT_MAX_=?,LOSS_LIMIT_MIN_=?,FUNDS_LIST_=?,PLAN_GROUP_DATA_=?,PLAN_GROUP_ACTIVE_KEY_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?");
		List<Object> parameters = new ArrayList<>();
		parameters.add(planItem.getProfitLimitMax());
		parameters.add(planItem.getLossLimitMin());
		parameters.add(planItem.getFundsList());
		parameters.add(planItem.getPlanGroupData());
		parameters.add(planItem.getPlanGroupActiveKey());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());

		if (StringTool.notEmpty(planItem.getFollowPeriod())) {
			sql.append(",FOLLOW_PERIOD_=?");
			parameters.add(planItem.getFollowPeriod());
		}
		if (StringTool.notEmpty(planItem.getMonitorPeriod())) {
			sql.append(",MONITOR_PERIOD_=?");
			parameters.add(planItem.getMonitorPeriod());
		}
		if (StringTool.notEmpty(planItem.getBetMode())) {
			sql.append(",BET_MODE_=?");
			parameters.add(planItem.getBetMode());
		}
		if (StringTool.notEmpty(planItem.getFundSwapMode())) {
			sql.append(",FUND_SWAP_MODE_=?");
			parameters.add(planItem.getFundSwapMode());
		}
		if (StringTool.notEmpty(planItem.getPeriodRollMode())) {
			sql.append(",PERIOD_ROLL_MODE_=?");
			parameters.add(planItem.getPeriodRollMode());
		}
		if (StringTool.notEmpty(planItem.getExpandInfo())) {
			sql.append(",EXPAND_INFO_=?");
			parameters.add(planItem.getExpandInfo());
		}
		sql.append(" where ").append(planItemTableInfo.get("PLAN_ITEM_TABLE_NAME_")).append("_ID_=?");
		parameters.add(planItemTableInfo.get("PLAN_ITEM_TABLE_ID_"));
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 获取初始化方案信息
	 *
	 * @param planCodes 方案codes
	 * @return
	 */
	public Map<Object, Map<String, Object>> findInitInfo(Set<String> planCodes) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT GAME_TYPE_,PLAN_CODE_,PLAN_ITEM_TABLE_NAME_,PROFIT_LIMIT_MAX_,LOSS_LIMIT_MIN_,FUNDS_LIST_,FOLLOW_PERIOD_,"
						+ "MONITOR_PERIOD_,BET_MODE_,FUND_SWAP_MODE_,PERIOD_ROLL_MODE_,PLAN_GROUP_DATA_,EXPAND_INFO_"
						+ " FROM ibsp_plan_item WHERE STATE_ = ? AND PLAN_CODE_ IN (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String planCode : planCodes) {
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameterList);
		if (ContainerTool.isEmpty(list)) {
			return new HashMap<>(2);
		}
		Map<Object, Map<String, Object>> initPlanInfo = new HashMap<>(list.size());

		for (Map<String, Object> map : list) {
			Object planCode = map.remove("PLAN_CODE_");
			Object planGroupData = map.remove("PLAN_GROUP_DATA_");
			String gameType = map.remove("GAME_TYPE_").toString();
			if (initPlanInfo.containsKey(planCode)) {
				initPlanInfo.get(planCode).put(gameType, planGroupData);
			} else {
				initPlanInfo.put(planCode, map);
				map.put(gameType, planGroupData);
			}
		}
		return initPlanInfo;
	}

	/**
	 * 删除方案详情信息
	 *
	 * @param planInfos 方案详情信息
	 */
	public void clearPlanItem(Map<String, Object> planInfos) throws SQLException {
		String sql = "update ibs_plan_library.%s set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where %s_ID_=?";
		List<Object> parameterPlus = new ArrayList<>();
		parameterPlus.add(IbsStateEnum.DEL.name());
		parameterPlus.add(new Date());
		parameterPlus.add(System.currentTimeMillis());
		List<Object> parameterList = new ArrayList<>();
		for (Map.Entry<String, Object> entry : planInfos.entrySet()) {
			parameterList.addAll(parameterPlus);
			parameterList.add(entry.getValue());
			super.dao.execute(String.format(sql, entry.getKey(), entry.getKey()), parameterList);
			parameterList.clear();
		}
	}

	/**
	 * 删除方案详情信息
	 *
	 * @param planUser 方案用户
	 */
	public void delPlanItem(IbspPlanUser planUser) throws SQLException {
		String sql = "update ibs_plan_library.%s set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where %s_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(planUser.getPlanItemTableId());
		super.dao.execute(String.format(sql, planUser.getPlanItemTableName(), planUser.getPlanItemTableName()), parameterList);
	}

	/**
	 * 获取方案游戏初始化信息
	 *
	 * @param planCode 方案编码
	 * @return
	 */
	public IbspPlanItem findPlanGameInit(String planCode) throws Exception {
		String sql = "select * from ibsp_plan_item where PLAN_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(planCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbspPlanItem.class, sql, parameterList);
	}

	/**
	 * 添加方案详情信息
	 *
	 * @param appUserId    用户id
	 * @param planItemInit 方案详情初始化信息
	 * @param planGame     方案游戏信息
	 * @param gameId       游戏id
	 * @return
	 */
	public String savePlanItem(String appUserId, IbspPlanItem planItemInit, Map<String, Object> planGame, String gameId) throws SQLException {
		Date nowTime = new Date();
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ibs_plan_library.").append(planItemInit.getPlanItemTableName()).append("(").append(planItemInit.getPlanItemTableName())
				.append("_ID_,PLAN_ID_,GAME_ID_,APP_USER_ID_,PROFIT_LIMIT_MAX_,LOSS_LIMIT_MIN_,FUNDS_LIST_,PLAN_GROUP_DATA_,");
		List<Object> parameterList = new ArrayList<>(19);
		String planItemId = RandomTool.getNumLetter32();
		parameterList.add(planItemId);
		parameterList.add(planGame.get("PLAN_ID_"));
		parameterList.add(gameId);
		parameterList.add(appUserId);
		parameterList.add(planItemInit.getProfitLimitMax());
		parameterList.add(planItemInit.getLossLimitMin());
		parameterList.add(planItemInit.getFundsList());
		parameterList.add(planItemInit.getPlanGroupData());
		StringBuilder sqlPlus = new StringBuilder();
		sqlPlus.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?");
		if (StringTool.notEmpty(planItemInit.getFollowPeriod())) {
			sql.append("FOLLOW_PERIOD_,");
			sqlPlus.append(",?");
			parameterList.add(planItemInit.getFollowPeriod());
		}
		if (StringTool.notEmpty(planItemInit.getMonitorPeriod())) {
			sql.append("MONITOR_PERIOD_,");
			sqlPlus.append(",?");
			parameterList.add(planItemInit.getMonitorPeriod());
		}
		if (StringTool.notEmpty(planItemInit.getBetMode())) {
			sql.append("BET_MODE_,");
			sqlPlus.append(",?");
			parameterList.add(planItemInit.getBetMode());
		}
		if (StringTool.notEmpty(planItemInit.getFundSwapMode())) {
			sql.append("FUND_SWAP_MODE_,");
			sqlPlus.append(",?");
			parameterList.add(planItemInit.getFundSwapMode());
		}
		if (StringTool.notEmpty(planItemInit.getPeriodRollMode())) {
			sql.append("PERIOD_ROLL_MODE_,");
			sqlPlus.append(",?");
			parameterList.add(planItemInit.getPeriodRollMode());
		}
		if (StringTool.notEmpty(planItemInit.getExpandInfo())) {
			sql.append("EXPAND_INFO_,");
			sqlPlus.append(",?");
			parameterList.add(planItemInit.getExpandInfo());
		}
		sql.append("CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) ").append(sqlPlus).append(")");
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql.toString(), parameterList);

		return planItemId;
	}

	/**
	 * TODO
	 * 新增方案详情--
	 * @param gameType 添加的游戏分类列表
	 * @param planCode 方案code
	 * @param itemTableName 表名
	 * @throws SQLException
	 */
	public void saveItem(Set<Object> gameType, String planCode, String itemTableName, JSONObject planGroupDataInfos, Date nowTime) throws SQLException {
		String sql = "INSERT INTO `ibsp_plan_item` ( `PLAN_CODE_`,`PLAN_ITEM_TABLE_NAME_`, `PROFIT_LIMIT_MAX_`, " +
				"`LOSS_LIMIT_MIN_`, `FUNDS_LIST_`,`FOLLOW_PERIOD_`, `MONITOR_PERIOD_`, `BET_MODE_`, `FUND_SWAP_MODE_`, " +
				"`PERIOD_ROLL_MODE_`,  `PLAN_GROUP_ACTIVE_KEY_`, `CREATE_TIME_`," +
				" `CREATE_TIME_LONG_`, `UPDATE_TIME_`, `UPDATE_TIME_LONG_`, `STATE_`,`IBSP_PLAN_ITEM_ID_`, `GAME_TYPE_`,`PLAN_GROUP_DATA_`, `EXPAND_INFO_` ) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?)";

		List<Object> parameterList = new ArrayList<>(20);
		parameterList.add(planCode);
		parameterList.add(itemTableName);
		parameterList.add(10000);
		parameterList.add(-10000);
		parameterList.add("2,4,8,16,32");
		parameterList.add(0);
		parameterList.add(0);
		parameterList.add(IbsModeEnum.BET_MODE_REGULAR.name());
		parameterList.add(IbsModeEnum.FUND_SWAP_MODE_NO_SWAP_ON_RESET.name());
		parameterList.add(IbsModeEnum.PERIOD_ROLL_MODE_ALL.name());
		parameterList.add("");
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbsStateEnum.OPEN.name());
		for (Object type : gameType) {
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(type);
			JSONObject dataJson = planGroupDataInfos.getJSONObject(type.toString());
			parameterList.add(dataJson.getJSONObject("planGroupData").toString());
			parameterList.add(dataJson.getJSONObject("expandInfo").toString());
			super.dao.execute(sql,parameterList);
			parameterList.remove(parameterList.size()-1);
			parameterList.remove(parameterList.size()-1);
			parameterList.remove(parameterList.size()-1);
			parameterList.remove(parameterList.size()-1);
		}
	}

	/**
	 * 获取方案详情信息
	 * @param planCode 方案编码
	 * @return 详情信息
	 */
	public Map<String,Map<String,Object>> findPlanItemInfo(String planCode) throws SQLException {
		String sql = "SELECT ip.SN_,ip.plan_name_,ipt.GAME_TYPE_,ipt.PROFIT_LIMIT_MAX_,ipt.LOSS_LIMIT_MIN_," +
				"ipt.FUNDS_LIST_,ipt.FOLLOW_PERIOD_,ipt.MONITOR_PERIOD_,ipt.EXPAND_INFO_,ipt.BET_MODE_," +
				"ipt.FUND_SWAP_MODE_,ipt.PERIOD_ROLL_MODE_,ipt.PLAN_GROUP_DATA_,ipt.PLAN_GROUP_ACTIVE_KEY_ " +
				" FROM ibsp_plan_item ipt LEFT JOIN  ibsp_plan ip ON ip.PLAN_CODE_=ipt.PLAN_CODE_ " +
				" WHERE ip.PLAN_CODE_ = ? AND ip.STATE_!=? AND ipt.STATE_!=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(planCode);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findKeyMap(sql,parameterList,"GAME_TYPE_");
	}

}
