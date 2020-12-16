package com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.entity.IbmExecPlanGroupT;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmExecPlanGroupTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmExecPlanGroupT对象数据
	 */
	public String save(IbmExecPlanGroupT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_exec_plan_group的 IBM_EXEC_PLAN_GROUP_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_exec_plan_group set state_='DEL' where IBM_EXEC_PLAN_GROUP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EXEC_PLAN_GROUP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_exec_plan_group的 IBM_EXEC_PLAN_GROUP_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_exec_plan_group set state_='DEL' where IBM_EXEC_PLAN_GROUP_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_exec_plan_group的 IBM_EXEC_PLAN_GROUP_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_exec_plan_group where IBM_EXEC_PLAN_GROUP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EXEC_PLAN_GROUP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_exec_plan_group的 IBM_EXEC_PLAN_GROUP_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_exec_plan_group where IBM_EXEC_PLAN_GROUP_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmExecPlanGroupT实体信息
	 *
	 * @param entity IbmExecPlanGroupT实体
	 */
	public void update(IbmExecPlanGroupT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_exec_plan_group表主键查找IbmExecPlanGroupT实体
	 *
	 * @param id ibm_exec_plan_group 主键
	 * @return IbmExecPlanGroupT实体
	 */
	public IbmExecPlanGroupT find(String id) throws Exception {
		return (IbmExecPlanGroupT) this.dao.find(IbmExecPlanGroupT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_exec_plan_group where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_exec_plan_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_exec_plan_group  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmExecPlanGroupT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmExecPlanGroupT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_exec_plan_group where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_exec_plan_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_exec_plan_group  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmExecPlanGroupT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_exec_plan_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmExecPlanGroupT数据信息
	 *
	 * @return 可用<IbmExecPlanGroupT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_exec_plan_group  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmExecPlanGroupT.class, sql);
	}

	/**
	 * 查找历史数据
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param planItemTableId  方案详情表id
	 * @param planGroupKey     方案组key
	 * @return IBM_EXEC_PLAN_GROUP_ID_    &nbsp;&nbsp;盘口会员方案执行详情主键, <br>
	 * FUND_GROUP_KEY_ 						&nbsp;&nbsp;资金组key, <br>
	 * EXEC_RESULT_ 						&nbsp;&nbsp;执行结果
	 */
	public Map<String, Object> findHistory(String handicapMemberId, String planItemTableId, String planGroupKey)
			throws Exception {
		String sql = "SELECT IBM_EXEC_PLAN_GROUP_ID_, FUND_GROUP_KEY_, EXEC_RESULT_,STATE_ FROM ibm_exec_plan_group "
				+ " WHERE HANDICAP_MEMBER_ID_ = ? AND PLAN_ITEM_TABLE_ID_ = ? AND PLAN_GROUP_KEY_ = ? "
				+ " ORDER BY CREATE_TIME_LONG_ DESC LIMIT 1";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(planItemTableId);
		parameterList.add(planGroupKey);
		Map<String, Object> historyInfo = super.dao.findMap(sql, parameterList);
		//没查到数据
		if (ContainerTool.isEmpty(historyInfo)) {
			return null;
		} else if (IbmStateEnum.OPEN.name().equals(historyInfo.get("STATE_"))) {
			historyInfo.remove("STATE_");
			return historyInfo;
		}
		//状态被关闭
		return null;
	}

	/**
	 * 方案组炸了
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 是 ，停止
	 */
	public boolean isBlastStop(String handicapMemberId) {
		String sql = "SELECT BLAST_STOP_ FROM `ibm_hm_set` where HANDICAP_MEMBER_ID_ = ? and state_!='DEL'";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(handicapMemberId);
		try {
			String blastStop = super.dao.findString("BLAST_STOP_", sql, parameters);
			if (IbmTypeEnum.CLOSE.name().equals(blastStop)) {
				return false;
			}
			parameters.clear();
			sql = "update ibm_hm_game_set set BET_STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
					+ " where HANDICAP_MEMBER_ID_ = ? and BET_STATE_ = ? and state_!='DEL'";
			parameters.add(IbmTypeEnum.FALSE.name());
			parameters.add(new Date());
			parameters.add(System.currentTimeMillis());
			parameters.add("自动启动炸停止");
			parameters.add(handicapMemberId);
			parameters.add(IbmTypeEnum.TRUE.name());
			super.dao.execute(sql, parameters);
			return true;
		} catch (SQLException e) {
			log.error("自动启动炸停止失败", e);
		}
		return false;
	}

	/**
	 * 获取期期滚历史信息
	 *
	 * @param gameId     游戏id
	 * @param handicapId 盘口id
	 * @param period     期数
	 * @param planId     方案id
	 * @return 期期滚历史信息
	 */
	public List<IbmExecPlanGroupT> listPeriodRoll(String gameId, String handicapId, Object period, String planId)
			throws Exception {
		String sql = "SELECT * FROM ibm_exec_plan_group WHERE PERIOD_ = ? AND PLAN_ID_ = ? AND GAME_ID_ = ? "
				+ " AND HANDICAP_ID_ = ? and BET_MODE_ = ? AND state_ = ? ";
		List<Object> parameterList = new ArrayList<>(12);
		parameterList.add(period);
		parameterList.add(planId);
		parameterList.add(gameId);
		parameterList.add(handicapId);
		parameterList.add(IbmModeEnum.BET_MODE_PERIOD_ROLL.name());
		parameterList.add(IbmStateEnum.OPEN.name());

		return (List<IbmExecPlanGroupT>) super.dao.findObjectList(IbmExecPlanGroupT.class, sql, parameterList);
	}
	/**
	 * 获取编码列表信息
	 *
	 * @param planId     方案id
	 * @param period     期数
	 * @param handicapId 盘口id
	 * @return 编码列表信息
	 */
	public List<Map<String, Object>> listCodingInfo(String planId, Object period, String handicapId)
			throws SQLException {
		String sql =
				"SELECT IBM_EXEC_PLAN_GROUP_ID_,PLAN_ITEM_TABLE_ID_,PLAN_GROUP_KEY_,PLAN_GROUP_VALUE_,BASE_PERIOD_,  "
						+ " FUND_GROUP_KEY_,FUND_GROUP_VALUE_,HANDICAP_MEMBER_ID_ FROM ibm_exec_plan_group WHERE "
						+ " PERIOD_ = ? AND PLAN_ID_ = ? AND HANDICAP_ID_ = ? AND EXEC_RESULT_ = ? AND state_ = ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(period);
		parameterList.add(planId);
		parameterList.add(handicapId);
		parameterList.add(IbmTypeEnum.READY.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 更新未投注信息
	 *
	 * @param handicapMemberIds 未投注信息
	 */
	public void closeUnBetInfo(List<String> handicapMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder("update ibm_exec_plan_group set EXEC_RESULT_ = ?,DESC_ = ?,UPDATE_TIME_");
		sql.append(" = ?,UPDATE_TIME_LONG_ = ?,STATE_ = ? where EXEC_RESULT_ = ? and HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>(6 + handicapMemberIds.size());
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add("UN_TURN");
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(IbmTypeEnum.READY.name());
		for (String handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 获取盘口会员的编码列表信息
	 *
	 * @param planItemId       方案详情id
	 * @param period           期数
	 * @param handicapMemberId 盘口会员id
	 * @return 编码列表信息
	 */
	public List<Map<String, Object>> listCodingInfoByHm(String planItemId, Object period, String handicapMemberId)
			throws SQLException {
		String sql =
				"SELECT IBM_EXEC_PLAN_GROUP_ID_,PLAN_ITEM_TABLE_ID_,PLAN_GROUP_VALUE_, BASE_PERIOD_, PLAN_GROUP_KEY_, "
						+ " FUND_GROUP_VALUE_, FUND_GROUP_KEY_,HANDICAP_MEMBER_ID_ FROM ibm_exec_plan_group WHERE "
						+ " PERIOD_ = ? AND PLAN_ITEM_TABLE_ID_ = ? AND HANDICAP_MEMBER_ID_ = ? AND EXEC_RESULT_ = ? AND state_ = ? ";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(period);
		parameterList.add(planItemId);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmTypeEnum.READY.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 重置用户方案
	 *
	 * @param planItemTableIds 方案详情表id列表
	 */
	public void replayPlan(List<String> planItemTableIds) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"UPDATE ibm_exec_plan_group SET STATE_ = ? WHERE STATE_ = ? AND PLAN_ITEM_TABLE_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.REPLAY.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String planItemTableId : planItemTableIds) {
			sql.append("?,");
			parameterList.add(planItemTableId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 重置盘口会员的所有执行信息
	 *
	 * @param clazzName        执行class名称
	 * @param handicapMemberId 盘口会员id
	 */
	public void replayAll4Hm(String clazzName, String handicapMemberId) throws SQLException {
		String sql = "UPDATE ibm_exec_plan_group SET STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " WHERE HANDICAP_MEMBER_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.REPLAY.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName + "-重置执行情况");
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 重置盘口会员的所有执行信息
	 *
	 * @param clazzName        执行class名称
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 */
	public void replayAll4Hm(String clazzName, String handicapMemberId, String gameId) throws SQLException {
		String sql = "UPDATE ibm_exec_plan_group SET STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " WHERE HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(IbmStateEnum.REPLAY.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName + "-重置游戏执行情况");
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 更新执行结果
	 *
	 * @param execPlanGroupId 执行方案组id
	 * @param execResult      执行结果
	 * @param nowTime         更新时间
	 */
	public void updateResult(String execPlanGroupId, String execResult, Date nowTime,String className) throws SQLException {
		String sql = "UPDATE `ibm_exec_plan_group` SET DESC_=?, EXEC_RESULT_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ " WHERE IBM_EXEC_PLAN_GROUP_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(className+"更新执行结果");
		parameterList.add(execResult);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(execPlanGroupId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取编码列表信息
	 *
	 * @param planId           方案id
	 * @param gameId           游戏id
	 * @param period           期数
	 * @param handicapMemberId 盘口会员id
	 * @return 编码列表信息
	 */
	public List<Map<String, Object>> listCodingInfoByHm(String planId, String gameId, Object period,
			String handicapMemberId) throws SQLException {
		String sql =
				"SELECT PLAN_GROUP_VALUE_, BASE_PERIOD_, PLAN_GROUP_KEY_, IBM_EXEC_PLAN_GROUP_ID_, FUND_GROUP_VALUE_, "
						+ " FUND_GROUP_KEY_,ihs.BET_MODE_ FROM ibm_exec_plan_group iep "
						+ " LEFT JOIN ibm_hm_game_set ihs ON iep.HANDICAP_MEMBER_ID_ = ihs.HANDICAP_MEMBER_ID_ "
						+ " AND iep.GAME_ID_ = ihs.GAME_ID_ WHERE PERIOD_ = ? AND PLAN_ID_ = ? AND iep.GAME_ID_ = ? "
						+ " AND iep.HANDICAP_MEMBER_ID_ = ? AND EXEC_RESULT_ = ? AND iep.state_ != ? AND ihs.state_ != ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(period);
		parameterList.add(planId);
		parameterList.add(gameId);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmTypeEnum.READY.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * @param handicapMemberId 盘口会员ID
	 * @param period           期数
	 * @Description: 通过盘口会员ID逻辑删除
	 * <p>
	 * 参数说明
	 */
	public void delByHmIdAndPeriod(String handicapMemberId, String period,String className) throws SQLException {
		String sql = "update ibm_exec_plan_group set DESC_=?, STATE_ = ? where HANDICAP_MEMBER_ID_ = ? AND PERIOD_ = ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(className+"通过盘口会员ID逻辑删除");
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(handicapMemberId);
		parameterList.add(period);
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 清理冗余数据
	 * 清理两天前创建的数据
	 *
	 * @param nowTime 清理时间
	 */
	public void clearRedundancy(Date nowTime, String ruleTime) throws Exception {
		String sql = "DELETE FROM ibm_exec_plan_group WHERE CREATE_TIME_LONG_ < ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(DateTool.getAfterRule(nowTime, ruleTime).getTime());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 重置盘口会员所有游戏资金历史记录
	 *
	 * @param handicapMemberId 盘口会员id
	 */
	public void resetHistory(String handicapMemberId,String className) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select IBM_EXEC_PLAN_GROUP_ID_ from ibm_exec_plan_group where HANDICAP_MEMBER_ID_=? and STATE_=?");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<String> groupIds = super.dao.findStringList("IBM_EXEC_PLAN_GROUP_ID_", sql.toString(), parameterList);
		if (ContainerTool.isEmpty(groupIds)) {
			return;
		}
		parameterList.clear();
		sql.delete(0, sql.length());
		sql.append(
				"update ibm_exec_plan_group set  DESC_=?, STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_EXEC_PLAN_GROUP_ID_ in(");
		parameterList.add(className+"重置盘口会员所有游戏资金历史记录");
		parameterList.add(IbmStateEnum.READY.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for (String groupId : groupIds) {
			sql.append("?,");
			parameterList.add(groupId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameterList);
	}
	/**
	 * 重置游戏资金方案
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 */
	public void resetPlan(String handicapMemberId, String gameId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select IBM_EXEC_PLAN_GROUP_ID_ from ibm_exec_plan_group where HANDICAP_MEMBER_ID_=? and GAME_ID_=? and STATE_=?");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<String> groupIds = super.dao.findStringList("IBM_EXEC_PLAN_GROUP_ID_", sql.toString(), parameterList);
		if (ContainerTool.isEmpty(groupIds)) {
			return;
		}
		sql.delete(0, sql.length());
		parameterList.clear();
		sql.append(
				"update ibm_exec_plan_group set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_EXEC_PLAN_GROUP_ID_ in(");
		parameterList.add(IbmStateEnum.READY.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for (String groupId : groupIds) {
			sql.append("?,");
			parameterList.add(groupId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameterList);
	}
}
