package com.ibm.old.v1.cloud.ibm_exec_bet_result.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.cloud.ibm_exec_bet_result.entity.IbmExecBetResult;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmExecBetResultService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmExecBetResult对象数据
	 */
	public String save(IbmExecBetResult entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_exec_bet_result的 IBM_EXEC_BET_RESULT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_exec_bet_result set state_='DEL' where IBM_EXEC_BET_RESULT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EXEC_BET_RESULT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_exec_bet_result的 IBM_EXEC_BET_RESULT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_exec_bet_result set state_='DEL' where IBM_EXEC_BET_RESULT_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_exec_bet_result的 IBM_EXEC_BET_RESULT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_exec_bet_result where IBM_EXEC_BET_RESULT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EXEC_BET_RESULT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_exec_bet_result的 IBM_EXEC_BET_RESULT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_exec_bet_result where IBM_EXEC_BET_RESULT_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmExecBetResult实体信息
	 *
	 * @param entity IbmExecBetResult实体
	 */
	public void update(IbmExecBetResult entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_exec_bet_result表主键查找IbmExecBetResult实体
	 *
	 * @param id ibm_exec_bet_result 主键
	 * @return IbmExecBetResultT.实体
	 */
	public IbmExecBetResult find(String id) throws Exception {
		return (IbmExecBetResult) this.dao.find(IbmExecBetResult.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_exec_bet_result where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_exec_bet_result  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_exec_bet_result  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmExecBetResultT.数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmExecBetResult 数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_exec_bet_result where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_exec_bet_result  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_exec_bet_result  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmExecBetResult.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_exec_bet_result  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmExecBetResultT.数据信息
	 *
	 * @return 可用<IbmExecBetResult>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_exec_bet_result  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmExecBetResult.class, sql);
	}

	/**
	 * 更新处理结果信息
	 *
	 * @param planId           方案Id
	 * @param handicapMemberId 盘口会员id
	 * @param planItemTableId  方案详情id
	 * @param planGroupKey     方案组Key
	 * @param execPlanGroupId  执行方案组id
	 * @param execResult       执行结果
	 * @param nowTime          更新时间
	 */
	public void updateResult(String gameId, String planId, String handicapId, String handicapMemberId,
			String planItemTableId, String planGroupKey, String execPlanGroupId, String execResult, Date nowTime,String className)
			throws Exception {
		String sql = "SELECT IBM_EXEC_BET_RESULT_ID_ FROM IBM_EXEC_BET_RESULT WHERE HANDICAP_MEMBER_ID_ = ? AND "
				+ " PLAN_ITEM_TABLE_ID_ = ? AND PLAN_GROUP_KEY_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapMemberId);
		parameterList.add(planItemTableId);
		parameterList.add(planGroupKey);
		parameterList.add(IbmStateEnum.OPEN.name());
		String execBetResultId = super.dao.findString("IBM_EXEC_BET_RESULT_ID_", sql, parameterList);
		if (StringTool.notEmpty(execBetResultId)) {
			sql = "UPDATE IBM_EXEC_BET_RESULT SET EXEC_PLAN_GROUP_ID_ = ?,DESC_=?,EXEC_RESULT_ = ?,UPDATE_TIME_ = ?,"
					+ "UPDATE_TIME_LONG_ = ? where IBM_EXEC_BET_RESULT_ID_ = ?";
			parameterList.clear();
			parameterList.add(execPlanGroupId);
			parameterList.add(className+"更新处理结果信息");
			parameterList.add(execResult);
			parameterList.add(nowTime);
			parameterList.add(System.currentTimeMillis());
			parameterList.add(execBetResultId);
			super.dao.execute(sql, parameterList);
		} else {
			IbmExecBetResult execBetResult = new IbmExecBetResult();
			execBetResult.setGameId(gameId);
			execBetResult.setPlanId(planId);
			execBetResult.setHandicapId(handicapId);
			execBetResult.setHandicapMemberId(handicapMemberId);
			execBetResult.setExecPlanGroupId(execPlanGroupId);
			execBetResult.setPlanItemTableId(planItemTableId);
			execBetResult.setPlanGroupKey(planGroupKey);
			execBetResult.setExecResult(execResult);
			execBetResult.setCreateTime(nowTime);
			execBetResult.setCreateTimeLong(System.currentTimeMillis());
			execBetResult.setUpdateTime(nowTime);
			execBetResult.setUpdateTimeLong(System.currentTimeMillis());
			execBetResult.setState(IbmStateEnum.OPEN.name());
			this.save(execBetResult);
		}
	}
	/**
	 * 获取执行投注结果
	 *
	 * @param gameId            游戏主键
	 * @param handicapMemberIds 盘口会员主键s
	 * @return 执行投注结果
	 */
	public Map<Object, Object> mapStopIncrease(String gameId, Collection<String> handicapMemberIds)
			throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT CONCAT_WS('#',PLAN_ITEM_TABLE_ID_,HANDICAP_MEMBER_ID_,");
		sql.append(" PLAN_GROUP_KEY_) AS key_,EXEC_RESULT_ AS value_ FROM `ibm_exec_bet_result` where ");
		sql.append(" STATE_ = ? AND HANDICAP_MEMBER_ID_ IN (SELECT HANDICAP_MEMBER_ID_ FROM ibm_hm_game_set WHERE")
				.append(" GAME_ID_ = ? AND INCREASE_STATE_ = ? AND HANDICAP_MEMBER_ID_ IN (");
		List<Object> parameterList = new ArrayList<>(handicapMemberIds.size() + 3);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.STOP.name());
		for (String handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append("))");
		return super.findKVMap(sql.toString(), parameterList);
	}

	public void mapExecInfo(Map<String, String> openItemIds) {
	}
	/**
	 * 重置执行情况
	 *
	 * @param clazzName 执行class名称
	 * @param handicapMemberId 盘口会员id
	 */
	public void replayAll4Hm(String clazzName, String handicapMemberId) throws SQLException {
		String sql = "UPDATE IBM_EXEC_BET_RESULT SET STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ?"
				+ " WHERE HANDICAP_MEMBER_ID_ = ? and  STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.REPLAY.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName+"-重置执行情况");
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 重置执行情况
	 *
	 * @param clazzName 执行class名称
	 * @param handicapMemberId 盘口会员id
	 * @param gameId 游戏id
	 */
	public void replayAll4Hm(String clazzName, String handicapMemberId, String gameId) throws SQLException {
		String sql = "UPDATE IBM_EXEC_BET_RESULT SET STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ?"
				+ " WHERE HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and  STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.REPLAY.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName+"-重置游戏执行情况");
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}
}
