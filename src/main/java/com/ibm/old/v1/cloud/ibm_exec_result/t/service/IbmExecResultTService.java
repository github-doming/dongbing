package com.ibm.old.v1.cloud.ibm_exec_result.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_exec_result.t.entity.IbmExecResultT;
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
public class IbmExecResultTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmExecResultT对象数据
	 */
	public String save(IbmExecResultT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_exec_result的 IBM_EXEC_RESULT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_exec_result set state_='DEL' where IBM_EXEC_RESULT_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EXEC_RESULT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_exec_result的 IBM_EXEC_RESULT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_exec_result set state_='DEL' where IBM_EXEC_RESULT_ID_ in(" + stringBuilder.toString()
							+ ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_exec_result的 IBM_EXEC_RESULT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_exec_result where IBM_EXEC_RESULT_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EXEC_RESULT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_exec_result的 IBM_EXEC_RESULT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_exec_result where IBM_EXEC_RESULT_ID_ in(" + stringBuilder.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmExecResultT实体信息
	 *
	 * @param entity IbmExecResultT实体
	 */
	public void update(IbmExecResultT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_exec_result表主键查找IbmExecResultT实体
	 *
	 * @param id ibm_exec_result 主键
	 * @return IbmExecResultT实体
	 */
	public IbmExecResultT find(String id) throws Exception {
		return (IbmExecResultT) this.dao.find(IbmExecResultT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_exec_result where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_exec_result  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_exec_result  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmExecResultT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmExecResultT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_exec_result where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_exec_result  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_exec_result  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmExecResultT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_exec_result  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmExecResultT数据信息
	 *
	 * @return 可用<IbmExecResultT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_exec_result  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findObjectList(IbmExecResultT.class, sql);
	}

	/**
	 * 查找执行阶段信息
	 *
	 * @param planId     方案id
	 * @param gameId     游戏id
	 * @param handicapId 盘口id
	 * @param period     期数
	 * @return 存在true        不存在false
	 */
	public IbmExecResultT find(String planId, String gameId, String handicapId, Object period) throws Exception {
		super.doTransactionPost();
		String sql = "SELECT * FROM ibm_exec_result  where GAME_ID_ = ? and HANDICAP_ID_ = ?"
				+ " and  PLAN_ID_ = ? and PERIOD_ = ? and  state_ = ? " ;
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(gameId);
		parameters.add(handicapId);
		parameters.add(planId);
		parameters.add(period.toString());
		parameters.add(IbmStateEnum.OPEN.name());
		return (IbmExecResultT) super.dao.findObject(IbmExecResultT.class, sql, parameters);
	}

	/**
	 * 获取执行阶段信息
	 *
	 * @param planId     方案id
	 * @param gameId     游戏id
	 * @param handicapId 盘口id
	 * @param period     期数
	 * @return 返回相同的值
	 */
	public String findExecResult(String planId, String gameId, String handicapId, Object period) throws Exception {
		super.doTransactionPost();
		String sql = "SELECT EXEC_RESULT_ FROM ibm_exec_result WHERE GAME_ID_ = ? and HANDICAP_ID_ = ? "
				+ " and  PLAN_ID_ = ? and PERIOD_ = ? and  state_ = ? GROUP BY EXEC_RESULT_" ;
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(gameId);
		parameters.add(handicapId);
		parameters.add(planId);
		parameters.add(period.toString());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("EXEC_RESULT_", sql, parameters);
	}

	/**
	 * 获取所有方案的执行结果，存在不同的返回空，相同的返回值
	 *
	 * @param gameId     游戏id
	 * @param handicapId 盘口id
	 * @param period     期数
	 * @return 返回相同的值
	 */
	public String findExecResult(String gameId, String handicapId, Object period) throws Exception {
		super.doTransactionPost();
		String sql = "SELECT EXEC_RESULT_ FROM ibm_exec_result WHERE GAME_ID_ = ? and HANDICAP_ID_ = ? "
				+ " and PERIOD_ = ? and  state_ = ? GROUP BY EXEC_RESULT_" ;
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(gameId);
		parameters.add(handicapId);
		parameters.add(period.toString());
		parameters.add(IbmStateEnum.OPEN.name());
		List<String> execResultList = super.dao.findStringList("EXEC_RESULT_", sql, parameters);
		if (ContainerTool.notEmpty(execResultList) && execResultList.size() == 1) {
			return execResultList.get(0);
		}
		return null;
	}

	/**
	 * 更新执行状态
	 *
	 * @param gameId     游戏id
	 * @param handicapId 盘口id
	 * @param period     期数
	 * @param execResult 原执行状态
	 * @param result     想要修改状态
	 */
	public void updateExecResult(String gameId, String handicapId, Object period, String execResult, String result,String className)
			throws SQLException {
		String sql = "UPDATE ibm_exec_result SET EXEC_RESULT_ = ?, DESC_=?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ " WHERE GAME_ID_ = ? AND HANDICAP_ID_ = ? AND PERIOD_ = ? AND EXEC_RESULT_ = ? AND state_ = ? " ;
		List<Object> parameters = new ArrayList<>(8);
		parameters.add(result);
		parameters.add(className+"更新执行状态");
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(gameId);
		parameters.add(handicapId);
		parameters.add(period.toString());
		parameters.add(execResult);
		parameters.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameters);

	}

	/**
	 * 更新执行结果
	 * @param newType 新执行结果
	 * @param oldType 老执行结果
	 * @param planId     方案id
	 * @param handicapId 盘口id
	 * @param period     期数
	 */
	public void updateExecResult(IbmTypeEnum newType, IbmTypeEnum oldType, String planId, String handicapId, Object period,String className)
			throws SQLException {
		String sql = "UPDATE ibm_exec_result SET EXEC_RESULT_ = ?,DESC_=?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ " WHERE PLAN_ID_ = ? AND HANDICAP_ID_ = ? AND PERIOD_ = ? AND EXEC_RESULT_ = ? AND state_ = ? " ;
		List<Object> parameters = new ArrayList<>(8);
		parameters.add(newType.name());
		parameters.add(className+"更新执行结果");
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(planId);
		parameters.add(handicapId);
		parameters.add(period.toString());
		parameters.add(oldType.name());
		parameters.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameters);
	}
	/**
	 * 更新执行结果
	 * @param newType 新执行结果
	 * @param oldType 老执行结果
	 * @param handicapId 盘口id
	 * @param period     期数
	 */
	public void updateExecResult(IbmTypeEnum newType, IbmTypeEnum oldType, String handicapId, Object period,String className)
			throws SQLException {
		String sql = "UPDATE ibm_exec_result SET EXEC_RESULT_ = ?, DESC_=?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ " WHERE HANDICAP_ID_ = ? AND PERIOD_ = ? AND EXEC_RESULT_ = ? AND state_ = ? " ;
		List<Object> parameters = new ArrayList<>(8);
		parameters.add(newType.name());
		parameters.add(className+"更新执行结果");
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(handicapId);
		parameters.add(period.toString());
		parameters.add(oldType.name());
		parameters.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameters);
	}

	/**
	 * 获取所有方案的执行结果，存在不同的返回空，相同的返回值
	 *
	 * @param gameId     游戏id
	 * @param handicapId 盘口id
	 * @param period     期数
	 * @return 返回相同的值
	 */
	public List<String> listExecResult(String gameId, String handicapId, Object period) throws Exception {
		super.doTransactionPost();
		String sql = "SELECT EXEC_RESULT_ FROM ibm_exec_result WHERE GAME_ID_ = ? and HANDICAP_ID_ = ? "
				+ " and PERIOD_ = ? and  state_ = ? GROUP BY EXEC_RESULT_" ;
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(gameId);
		parameters.add(handicapId);
		parameters.add(period.toString());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findStringList("EXEC_RESULT_", sql, parameters);
	}
	/**
	 * 清理冗余数据
	 * 清理五分钟前创建的数据
	 *
	 * @param nowTime  清理时间
	 * @param ruleTime 时间规则
	 */
	public void clearRedundancy(Date nowTime, String ruleTime) throws Exception {
		String sql = "DELETE FROM `ibm_exec_result` WHERE CREATE_TIME_LONG_< ? " ;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(DateTool.getAfterRule(nowTime, ruleTime).getTime());
		super.dao.execute(sql, parameterList);
	}
}
