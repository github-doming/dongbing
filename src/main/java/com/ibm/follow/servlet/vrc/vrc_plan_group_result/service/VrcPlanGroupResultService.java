package com.ibm.follow.servlet.vrc.vrc_plan_group_result.service;

import com.common.util.BaseGameUtil;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.vrc.vrc_plan_group_result.entity.VrcPlanGroupResult;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * VRC客户端_方案组执行结果 服务类
 *
 * @author Robot
 */
public class VrcPlanGroupResultService extends BaseServiceProxy {

	/**
	 * 保存VRC客户端_方案组执行结果 对象数据
	 *
	 * @param entity VrcPlanGroupResult对象数据
	 */
	public String save(VrcPlanGroupResult entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除vrc_plan_group_result 的 VRC_PLAN_GROUP_RESULT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vrc_plan_group_result set state_='DEL' where VRC_PLAN_GROUP_RESULT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VRC_PLAN_GROUP_RESULT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 vrc_plan_group_result 的 VRC_PLAN_GROUP_RESULT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vrc_plan_group_result set state_='DEL' where VRC_PLAN_GROUP_RESULT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 vrc_plan_group_result  的 VRC_PLAN_GROUP_RESULT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vrc_plan_group_result where VRC_PLAN_GROUP_RESULT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VRC_PLAN_GROUP_RESULT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除vrc_plan_group_result 的 VRC_PLAN_GROUP_RESULT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vrc_plan_group_result where VRC_PLAN_GROUP_RESULT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrcPlanGroupResult实体信息
	 *
	 * @param entity VRC客户端_方案组执行结果 实体
	 */
	public void update(VrcPlanGroupResult entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vrc_plan_group_result表主键查找 VRC客户端_方案组执行结果 实体
	 *
	 * @param id vrc_plan_group_result 主键
	 * @return VRC客户端_方案组执行结果 实体
	 */
	public VrcPlanGroupResult find(String id) throws Exception {
		return dao.find(VrcPlanGroupResult.class, id);
	}

	/**
	 * 更新方案组执行结果
	 *
	 * @param betInfos     投注信息
	 */
	public void updateResult(List<Map<String, Object>> betInfos) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("update vrc_plan_group_result set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,");
		sql.append("EXEC_RESULT_= CASE VRC_PLAN_GROUP_RESULT_ID_");
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
		sql.append(" end,FUND_GROUP_KEY_ = CASE VRC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("FUND_GROUP_KEY_"));
		}
		sql.append(" end,LAST_PERIOD_ = CASE VRC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("PERIOD_"));
		}
		sql.append(" end,BET_CONTENT_ = CASE VRC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("BET_CONTENT_"));
		}
		sql.append(" end,BET_FUND_T_ = CASE VRC_PLAN_GROUP_RESULT_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(betInfo.get("PLAN_GROUP_RESULT_ID_"));
			parameters.add(betInfo.get("BET_FUND_T_"));
		}
		sql.append(" end where VRC_PLAN_GROUP_RESULT_ID_ in (");
		sql.append(sqlPlus).replace(sql.length() - 1, sql.length(), ")");
		parameters.addAll(parametersPlus);
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 获取方案组历史投注信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏编码
	 */
	public Map<String, Map<String, Object>> findHistory(String existHmId, GameUtil.Code gameCode) throws SQLException {
		String sql = "select PLAN_CODE_,PLAN_GROUP_KEY_,FUND_GROUP_KEY_,LAST_PERIOD_,BET_CONTENT_,BET_FUND_T_,EXEC_RESULT_"
				+ " from vrc_plan_group_result where EXIST_MEMBER_VR_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(existHmId);
		parameterList.add(gameCode.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		List<Map<String, Object>> list = super.dao.findMapList(sql, parameterList);

		Map<String, Map<String, Object>> execResultMap = new HashMap<>(list.size());
		for (Map<String, Object> map : list) {
			String planGroupKey = map.remove("PLAN_CODE_").toString().concat("#").concat(map.remove("PLAN_GROUP_KEY_").toString());
			execResultMap.put(planGroupKey, map);
		}
		return execResultMap;
	}

	/**
	 * 获取方案组历史投注信息
	 *
	 * @param existHmVrId 已存在虚拟会员id
	 * @param gameCode    游戏编码
	 */
	public Map<String, Object> findHistoryResult(String existHmVrId, BaseGameUtil.Code gameCode) throws SQLException {
		String sql = "select VRC_PLAN_GROUP_RESULT_ID_ as PLAN_GROUP_RESULT_ID_,PLAN_CODE_,PLAN_GROUP_KEY_ from vrc_plan_group_result where"
				+ " EXIST_MEMBER_VR_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(existHmVrId);
		parameterList.add(gameCode.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		List<Map<String, Object>> list = super.dao.findMapList(sql, parameterList);

		Map<String, Object> execResultMap = new HashMap<>(list.size());
		for (Map<String, Object> map : list) {
			String planGroupKey = map.remove("PLAN_CODE_").toString().concat("#").concat(map.remove("PLAN_GROUP_KEY_").toString());
			execResultMap.put(planGroupKey, map.get("PLAN_GROUP_RESULT_ID_"));
		}
		return execResultMap;
	}

	/**
	 * 批量添加
	 * @param existHmId		已存在盘口会员id
	 * @param gameCode		游戏编码
	 * @param betInfos		投注信息
	 */
	public void batchSave(String existHmId, BaseGameUtil.Code gameCode, List<Map<String, Object>> betInfos) throws SQLException {
		StringBuilder sql=new StringBuilder("insert into vrc_plan_group_result");
		sql.append("(VRC_PLAN_GROUP_RESULT_ID_,EXIST_MEMBER_VR_ID_,GAME_CODE_,PLAN_CODE_,PLAN_GROUP_KEY_,FUND_GROUP_KEY_,"
				+ "LAST_PERIOD_,BET_CONTENT_,BET_FUND_T_,EXEC_RESULT_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,"
				+ "UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameterList = new ArrayList<>();
		Date nowTime=new Date();
		for(Map<String,Object> betInfo:betInfos){
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(existHmId);
			parameterList.add(gameCode.name());
			parameterList.add(betInfo.get("PLAN_CODE_"));
			parameterList.add(betInfo.get("PLAN_GROUP_KEY_"));
			parameterList.add(betInfo.get("FUND_GROUP_KEY_"));
			parameterList.add(betInfo.get("PERIOD_"));
			parameterList.add(betInfo.get("BET_CONTENT_"));
			parameterList.add(betInfo.get("BET_FUND_T_"));
			parameterList.add(betInfo.get("EXEC_RESULT_"));
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(StateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameterList);
	}

}
