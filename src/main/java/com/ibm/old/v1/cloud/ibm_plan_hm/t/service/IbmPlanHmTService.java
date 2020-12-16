package com.ibm.old.v1.cloud.ibm_plan_hm.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.entity.IbmPlanHmT;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmPlanHmTService extends BaseServicePlus {

	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmPlanHmT对象数据
	 */
	public String save(IbmPlanHmT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_plan_hm的 IBM_PLAN_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_plan_hm set state_='DEL' where IBM_PLAN_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_PLAN_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_plan_hm的 IBM_PLAN_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_plan_hm set state_='DEL' where IBM_PLAN_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_plan_hm的 IBM_PLAN_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_plan_hm where IBM_PLAN_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_PLAN_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_plan_hm的 IBM_PLAN_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_plan_hm where IBM_PLAN_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmPlanHmT实体信息
	 *
	 * @param entity IbmPlanHmT实体
	 */
	public void update(IbmPlanHmT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_plan_hm表主键查找IbmPlanHmT实体
	 *
	 * @param id ibm_plan_hm 主键
	 * @return IbmPlanHmT实体
	 */
	public IbmPlanHmT find(String id) throws Exception {
		return (IbmPlanHmT) this.dao.find(IbmPlanHmT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_plan_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_plan_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_plan_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmPlanHmT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmPlanHmT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_plan_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_plan_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_plan_hm  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmPlanHmT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_plan_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmPlanHmT数据信息
	 *
	 * @return 可用<IbmPlanHmT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_plan_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmPlanHmT.class, sql);
	}
	/**
	 * 获取状态
	 *
	 * @param planId           方案id
	 * @param handicapMemberId 盘口会员id
	 * @return 状态
	 */
	public String findState(String planId, String handicapMemberId) throws SQLException {
		String sql = "select STATE_ from ibm_plan_hm where PLAN_ID_ =? and HANDICAP_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(planId);
		parameterList.add(handicapMemberId);
		return super.dao.findString("STATE_", sql, parameterList);
	}

	/**
	 * 初始化盘口会员方案
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param plans            方案集合
	 */
	public void initPlanHm(String handicapMemberId, List<Map<String, Object>> plans) throws SQLException {
		Date nowTime = new Date();
		//插入不存在的盘口会员方案信息
		String sql =
				"INSERT INTO `ibm_plan_hm` (IBM_PLAN_HM_ID_,PLAN_USER_ID_,GAME_ID_,PLAN_ITEM_TABLE_ID_,PLAN_ID_,HANDICAP_MEMBER_ID_,CREATE_TIME_, "
						+ " CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES ";
		List<Object> parameterList = new ArrayList<>();
		StringBuilder sqlBuilder = new StringBuilder();
		//通过盘口会员id获取方案

		for (Map<String, Object> plan : plans) {
			sqlBuilder.append("(?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(plan.get("IBM_PLAN_USER_ID_"));
			parameterList.add(plan.get("GAME_ID_"));
			parameterList.add(plan.get("PLAN_ITEM_TABLE_ID_"));
			parameterList.add(plan.get("PLAN_ID_"));
			parameterList.add(handicapMemberId);
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(plan.get("STATE_"));
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(","));
		sql += sqlBuilder.toString();
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 同步盘口会员方案状态
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 */
	public void updateState(String handicapMemberId, String gameId,String className) throws SQLException {
		String sql = "SELECT pu.IBM_PLAN_USER_ID_ as key_,pu.STATE_ as value_ "
				+ " FROM ibm_plan_user pu LEFT JOIN ibm_handicap_member hm "
				+ " ON hm.APP_USER_ID_ = pu.APP_USER_ID_ WHERE hm.IBM_HANDICAP_MEMBER_ID_ = ?" + " AND hm.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, String> planUserMap = super.findKVsMap(sql, parameterList);

		sql = "UPDATE ibm_plan_hm SET DESC_=?,STATE_ = ?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? WHERE HANDICAP_MEMBER_ID_ = ? AND PLAN_USER_ID_ = ? AND GAME_ID_ = ? ";
		for (Map.Entry<String, String> entry : planUserMap.entrySet()) {
			parameterList.clear();
			parameterList.add(className+"同步盘口会员方案状态");
			parameterList.add(entry.getValue());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(handicapMemberId);
			parameterList.add(entry.getKey());
			parameterList.add(gameId);
			super.dao.execute(sql, parameterList);
		}
	}

	/**
	 * 同步盘口会员方案状态
	 *
	 * @param userId 用户id
	 * @param state  状态
	 * @param planId 方案id
	 */
	public void updateStateByUser(String userId, String state, String planId,String className) throws SQLException {
		List<Object> parameterList = new ArrayList<>(4);
		String sql = "UPDATE ibm_plan_hm SET DESC_=?,STATE_ = ?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? WHERE PLAN_USER_ID_ = "
				+ "(SELECT IBM_PLAN_USER_ID_ FROM ibm_plan_user WHERE PLAN_ID_ = ? AND APP_USER_ID_ = ? AND STATE_ != ? ); ";
		parameterList.add(className+"同步盘口会员方案状态");
		parameterList.add(state);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(planId);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 同步盘口会员方案状态
	 *
	 * @param userId     用户id
	 * @param state      状态
	 * @param planIdList 方案id集合
	 */
	public void updateStateByPlanUser(String userId, String state, List<String> planIdList,String className) throws SQLException {
		List<Object> parameterList = new ArrayList<>(4);
		StringBuilder sql = new StringBuilder(
				"UPDATE ibm_plan_hm SET DESC_=?,STATE_ = ?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? WHERE PLAN_USER_ID_ in "
						+ "(SELECT IBM_PLAN_USER_ID_ FROM ibm_plan_user WHERE PLAN_ID_ in ( ");
		parameterList.add(className+"同步盘口会员方案状态");
		parameterList.add(state);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for (String planId : planIdList) {
			sql.append("?,");
			parameterList.add(planId);
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") AND APP_USER_ID_ = ? AND STATE_ != ? ) ");
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql.toString(), parameterList);
	}
	/**
	 * 根据盘口会员id开启盘口会员方案
	 *
	 * @param handicapMemberId 盘口会员id
	 */
	public void openPlanHm(String handicapMemberId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameterList = new ArrayList<>();
		sql.append("SELECT pu.IBM_PLAN_USER_ID_ as key_,pu.STATE_ as value_ "
				+ " FROM ibm_plan_user pu LEFT JOIN ibm_handicap_member hm "
				+ " ON hm.APP_USER_ID_ = pu.APP_USER_ID_ WHERE hm.IBM_HANDICAP_MEMBER_ID_ = ?"
				+ " AND hm.STATE_ != ? ");
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, String> planUserMap = super.findKVsMap(sql.toString(), parameterList);
		if (ContainerTool.isEmpty(planUserMap)) {
			return;
		}
		sql.delete(0, sql.length());
		sql.append("UPDATE ibm_plan_hm SET STATE_ = ? WHERE HANDICAP_MEMBER_ID_ = ? AND PLAN_USER_ID_ = ? ");
		for (Map.Entry<String, String> entry : planUserMap.entrySet()) {
			parameterList.clear();
			parameterList.add(entry.getValue());
			parameterList.add(handicapMemberId);
			parameterList.add(entry.getKey());
			super.dao.execute(sql.toString(), parameterList);
		}
	}

	/**
	 * 获取盘口会员已开启的方案信息
	 *
	 * @param handicapMemberId 盘口会员ID
	 * @param gameId           游戏ID
	 * @return 方案信息
	 */
	public List<Map<String, Object>> listHmOpenPlanInfo(String handicapMemberId, String gameId) throws SQLException {
		String sql = "SELECT iph.PLAN_ID_, iph.PLAN_ITEM_TABLE_ID_,ipu.PLAN_ITEM_TABLE_NAME_ FROM "
				+ " ibm_plan_hm iph LEFT JOIN ibm_plan_user ipu ON iph.PLAN_USER_ID_ = ipu.IBM_PLAN_USER_ID_ "
				+ " WHERE HANDICAP_MEMBER_ID_ = ? AND iph.GAME_ID_ = ? AND iph.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 重置会员的所有方案
	 *
	 * @param clazzName        执行class名称
	 * @param handicapMemberId 盘口会员id
	 * @param appUserId        用户id
	 * @return 开启的方案ids
	 */
	public List<String> replayAll(String clazzName, String handicapMemberId, String appUserId) throws SQLException {
		//预先删除所有开启的信息
		String sql = "UPDATE `ibm_plan_hm` set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(10);
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName + "-预先删除所有开启的信息");
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);

		//获取用户开启的方案信息
		parameterList.clear();
		sql = "SELECT PLAN_ID_ FROM `ibm_plan_user` where APP_USER_ID_ = ? and STATE_ = ?";
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<String> planIds = super.dao.findStringList("PLAN_ID_", sql, parameterList);

		//开启用户开启的方案信息
		parameterList.clear();
		sql = "UPDATE `ibm_plan_hm` set  STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where HANDICAP_MEMBER_ID_ = ? and STATE_ != ? and PLAN_ID_ in (";
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName + "-开启用户开启的方案信息");
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		for (String planId : planIds) {
			sql = sql.concat("?,");
			parameterList.add(planId);
		}
		sql = sql.substring(0, sql.lastIndexOf(",")).concat(")");
		super.dao.execute(sql, parameterList);
		return planIds;
	}
	/**
	 * 重置会员的游戏的方案
	 *  @param clazzName        执行class名称
	 * @param handicapMemberId 盘口会员id
	 * @param appUserId        用户id
	 * @param gameId           游戏id
	 * @return plans
	 */
	public List<String> replayGame(String clazzName, String handicapMemberId, String appUserId, String gameId)
			throws SQLException {
		//预先删除所有开启的信息
		String sql = "UPDATE `ibm_plan_hm` set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(10);
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName + "-预先删除游戏开启的信息");
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);

		//获取用户开启的方案信息
		parameterList.clear();
		sql = "SELECT PLAN_ID_ FROM `ibm_plan_user` where APP_USER_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
		parameterList.add(appUserId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<String> planIds = super.dao.findStringList("PLAN_ID_", sql, parameterList);

		//开启用户开启的方案信息
		parameterList.clear();
		sql = "UPDATE `ibm_plan_hm` set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ != ? and PLAN_ID_ in (";
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName + "-开启用户开启游戏的方案信息");
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		for (String planId : planIds) {
			sql = sql.concat("?,");
			parameterList.add(planId);
		}
		sql = sql.substring(0, sql.lastIndexOf(",")).concat(")");
		super.dao.execute(sql, parameterList);
		return planIds;
	}

	public void syncUserPlanState(String appUserId, String itemId,String className) throws SQLException {
		String sql = "UPDATE ibm_plan_hm SET DESC_=?, STATE_ = (SELECT STATE_ FROM ibm_plan_user WHERE PLAN_ITEM_TABLE_ID_ = ? "
				+ " AND APP_USER_ID_ = ?) WHERE PLAN_ITEM_TABLE_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(className+"同步用户的方案状态");
		parameterList.add(itemId);
		parameterList.add(appUserId);
		parameterList.add(itemId);
		super.dao.execute(sql, parameterList);

	}
	/**
	 * 查询盘口会员是否存在方案盘口会员信息
	 * @param handicapMemberId			盘口会员id
	 * @return
	 */
	public boolean findByHmId(String handicapMemberId) throws SQLException {
		String sql="select IBM_PLAN_HM_ID_ from ibm_plan_hm where HANDICAP_MEMBER_ID_=? and STATE_!='DEL'";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(handicapMemberId);
		List<String> list=super.dao.findStringList("IBM_PLAN_HM_ID_",sql,parameterList);
		return ContainerTool.isEmpty(list);
	}
}
