package com.ibs.plan.module.client.ibsc_plan_group_result.service;


import com.common.util.BaseGameUtil;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.ibsc_plan_group_result.entity.IbscPlanGroupResult;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSC客户端_执行结果 服务类
 *
 * @author Robot
 */
public class IbscPlanGroupResultService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_执行结果 对象数据
	 *
	 * @param entity IbscExecResult对象数据
	 */
	public String save(IbscPlanGroupResult entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_exec_result 的 IBSC_EXEC_RESULT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_plan_group_result set state_='DEL' where IBSC_EXEC_RESULT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_EXEC_RESULT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_plan_group_result 的 IBSC_EXEC_RESULT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_plan_group_result set state_='DEL' where IBSC_EXEC_RESULT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_plan_group_result  的 IBSC_EXEC_RESULT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_plan_group_result where IBSC_EXEC_RESULT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_EXEC_RESULT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_exec_result 的 IBSC_EXEC_RESULT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_plan_group_result where IBSC_EXEC_RESULT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscExecResult实体信息
	 *
	 * @param entity IBSC客户端_执行结果 实体
	 */
	public void update(IbscPlanGroupResult entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_exec_result表主键查找 IBSC客户端_执行结果 实体
	 *
	 * @param id ibsc_plan_group_result 主键
	 * @return IBSC客户端_执行结果 实体
	 */
	public IbscPlanGroupResult find(String id) throws Exception {
		return (IbscPlanGroupResult) dao.find(IbscPlanGroupResult.class, id);

	}

	/**
	 * 更新方案组执行结果
	 *
	 * @param existHmId    已存在盘口会员id
	 * @param gameCode     游戏code
	 * @param betInfo      投注信息
	 * @param result       执行结果
	 */
	public void updateResult(String existHmId, GameUtil.Code gameCode, Map<String, Object> betInfo, String result) throws Exception {
		String sql = "select IBSC_PLAN_GROUP_RESULT_ID_ from ibsc_plan_group_result where EXIST_HM_ID_=? and GAME_CODE_=? and PLAN_CODE_=? "
				+ " and PLAN_GROUP_KEY_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(existHmId);
		parameterList.add(gameCode.name());
		parameterList.add(betInfo.get("PLAN_CODE_"));
		parameterList.add(betInfo.get("PLAN_GROUP_KEY_"));
		parameterList.add(IbsStateEnum.OPEN.name());
		String execResultId = super.dao.findString("IBSC_PLAN_GROUP_RESULT_ID_", sql, parameterList);
		Date nowTime=new Date();
		if (StringTool.isEmpty(execResultId)) {
			IbscPlanGroupResult execResult = new IbscPlanGroupResult();
			execResult.setExistHmId(existHmId);
			execResult.setGameCode(gameCode.name());
			execResult.setPlanCode(betInfo.get("PLAN_CODE_"));
			execResult.setPlanGroupKey(betInfo.get("PLAN_GROUP_KEY_"));
			execResult.setFundGroupKey(betInfo.get("FUND_GROUP_KEY_"));
			execResult.setLastPeriod(betInfo.get("PERIOD_"));
			execResult.setBetContent(betInfo.get("BET_CONTENT_"));
			execResult.setBetFundT(betInfo.get("BET_FUND_T_"));
			execResult.setExecResult(result);
			execResult.setCreateTime(nowTime);
			execResult.setCreateTimeLong(nowTime.getTime());
			execResult.setUpdateTime(nowTime);
			execResult.setUpdateTimeLong(nowTime.getTime());
			execResult.setState(IbsStateEnum.OPEN.name());
			this.save(execResult);
		} else {
			parameterList.clear();
			sql="update ibsc_plan_group_result set EXEC_RESULT_=?,FUND_GROUP_KEY_=?,LAST_PERIOD_=?,BET_CONTENT_=?,BET_FUND_T_=?,"
					+ "UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBSC_PLAN_GROUP_RESULT_ID_=?";
			parameterList.add(result);
			parameterList.add(betInfo.get("FUND_GROUP_KEY_"));
			parameterList.add(betInfo.get("PERIOD_"));
			parameterList.add(betInfo.get("BET_CONTENT_"));
			parameterList.add(betInfo.get("BET_FUND_T_"));
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(execResultId);
			super.dao.execute(sql,parameterList);
		}
	}
	/**
	 * 更新方案组执行结果
	 *
	 * @param betInfos     投注信息
	 */
	public void updateResult(List<Map<String, Object>> betInfos) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("update ibsc_plan_group_result set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,");
		sql.append("EXEC_RESULT_= CASE IBSC_PLAN_GROUP_RESULT_ID_");
		List<Object> parameters = new ArrayList<>();
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());

		StringBuilder sqlPlus = new StringBuilder();
		List<Object> parametersPlus = new ArrayList<>();
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("EXEC_RESULT_"));

			sqlPlus.append("?,");
			parametersPlus.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
		}
		sql.append(" end,FUND_GROUP_KEY_ = CASE IBSC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("FUND_GROUP_KEY_"));
		}
		sql.append(" end,LAST_PERIOD_ = CASE IBSC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("PERIOD_"));
		}
		sql.append(" end,BASE_PERIOD_ = CASE IBSC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("BASE_PERIOD_"));
		}
		sql.append(" end,BET_CONTENT_ = CASE IBSC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("BET_CONTENT_"));
		}
		sql.append(" end,EXPAND_INFO_ = CASE IBSC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("EXPAND_INFO_"));
		}
		sql.append(" end,BET_FUND_T_ = CASE IBSC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("BET_FUND_T_"));
		}
		sql.append(" end where IBSC_PLAN_GROUP_RESULT_ID_ in (");
		sql.append(sqlPlus).replace(sql.length() - 1, sql.length(), ")");
		parameters.addAll(parametersPlus);
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 批量添加
	 * @param existHmId		已存在盘口会员id
	 * @param gameCode		游戏编码
	 * @param betInfos		投注信息
	 */
	public void batchSave(String existHmId, BaseGameUtil.Code gameCode, List<Map<String, Object>> betInfos) throws SQLException {
		StringBuilder sql=new StringBuilder("insert into ibsc_plan_group_result");
		sql.append("(IBSC_PLAN_GROUP_RESULT_ID_,EXIST_HM_ID_,GAME_CODE_,PLAN_CODE_,PLAN_GROUP_KEY_,FUND_GROUP_KEY_,"
				+ "LAST_PERIOD_,BASE_PERIOD_,BET_CONTENT_,BET_FUND_T_,EXEC_RESULT_,EXPAND_INFO_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,"
				+ "UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameterList = new ArrayList<>();
		Date nowTime=new Date();
		for(Map<String,Object> betInfo:betInfos){
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(existHmId);
			parameterList.add(gameCode.name());
			parameterList.add(betInfo.get("PLAN_CODE_"));
			parameterList.add(betInfo.get("PLAN_GROUP_KEY_"));
			parameterList.add(betInfo.get("FUND_GROUP_KEY_"));
			parameterList.add(betInfo.get("PERIOD_"));
			parameterList.add(betInfo.get("BASE_PERIOD_"));
			parameterList.add(betInfo.get("BET_CONTENT_"));
			parameterList.add(betInfo.get("BET_FUND_T_"));
			parameterList.add(betInfo.get("EXEC_RESULT_"));
			parameterList.add(betInfo.get("EXPAND_INFO_"));
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(StateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameterList);
	}

	/**
	 * 获取方案组历史投注信息
	 * @param existHmId        已存在盘口会员id
	 * @param gameCode        游戏编码
	 */
	public Map<String,Map<String,Object>> findHistory(String existHmId, GameUtil.Code gameCode) throws SQLException {
		String sql="select PLAN_CODE_,PLAN_GROUP_KEY_,FUND_GROUP_KEY_,LAST_PERIOD_,BET_CONTENT_,BET_FUND_T_,EXEC_RESULT_"
				+ ",BASE_PERIOD_,EXPAND_INFO_ from ibsc_plan_group_result where EXIST_HM_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(existHmId);
		parameterList.add(gameCode.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		List<Map<String, Object>> list=super.dao.findMapList(sql,parameterList);

		Map<String,Map<String,Object>> execResultMap=new HashMap<>(list.size());
		for(Map<String, Object> map:list){
			String planGroupKey=map.remove("PLAN_CODE_").toString().concat("#").concat(map.remove("PLAN_GROUP_KEY_").toString());
			execResultMap.put(planGroupKey,map);
		}
		return execResultMap;
	}
	/**
	 * 获取方案组历史投注信息
	 * @param existHmId        已存在盘口会员id
	 * @param gameCode        游戏编码
	 */
	public Map<String, Object> findHistoryResult(String existHmId, BaseGameUtil.Code gameCode) throws SQLException {
		String sql="select IBSC_PLAN_GROUP_RESULT_ID_ as PLAN_GROUP_RESULT_ID_,PLAN_CODE_,PLAN_GROUP_KEY_ from ibsc_plan_group_result where"
				+ " EXIST_HM_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(existHmId);
		parameterList.add(gameCode.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		List<Map<String, Object>> list=super.dao.findMapList(sql,parameterList);

		Map<String,Object> execResultMap=new HashMap<>(list.size());
		for(Map<String, Object> map:list){
			String planGroupKey=map.remove("PLAN_CODE_").toString().concat("#").concat(map.remove("PLAN_GROUP_KEY_").toString());
			execResultMap.put(planGroupKey,map.get("PLAN_GROUP_RESULT_ID_"));
		}
		return execResultMap;
	}
	/**
	 * 方案重置
	 * @param existHmId 已存在盘口会员id
	 */
	public void updatePlanReset(String existHmId) throws SQLException {
		String sql="update ibsc_plan_group_result set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where EXIST_HM_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.RESET.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("方案重置");
		parameterList.add(existHmId);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql,parameterList);
	}

	/**
	 * 游戏方案重置
	 * @param existHmId		已存在盘口会员id
	 * @param gameCode		游戏编码
	 * @param planCodes		方案code
	 */
	public void updatePlanReset(String existHmId, String gameCode, String[] planCodes) throws SQLException {
		StringBuilder sql= new StringBuilder("update ibsc_plan_group_result set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where EXIST_HM_ID_ =? and GAME_CODE_=? and STATE_ =? and PLAN_CODE_ in(");
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(IbsStateEnum.RESET.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("方案重置");
		parameterList.add(existHmId);
		parameterList.add(gameCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		for(String planCode:planCodes){
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		dao.execute(sql.toString(),parameterList);
	}
}
