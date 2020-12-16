package com.ibm.old.v1.cloud.ibm_plan.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.cloud.ibm_plan.t.entity.IbmPlanT;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmPlanTService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmPlanT对象数据
	 */
	public String save(IbmPlanT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_plan的 IBM_PLAN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_plan set state_='DEL' where IBM_PLAN_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_plan的 IBM_PLAN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_plan set state_='DEL' where IBM_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_plan的 IBM_PLAN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_plan where IBM_PLAN_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_plan的 IBM_PLAN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_plan where IBM_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmPlanT实体信息
	 *
	 * @param entity IbmPlanT实体
	 */
	public void update(IbmPlanT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_plan表主键查找IbmPlanT实体
	 *
	 * @param id ibm_plan 主键
	 * @return IbmPlanT实体
	 */
	public IbmPlanT find(String id) throws Exception {
		return (IbmPlanT) this.dao.find(IbmPlanT.class, id);

	}

	/**
	 * 获取分页Map数据
	 * @param planName  方案名称
	 * @param pageIndex 页面索引
	 * @param pageSize  页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String planName, Integer pageIndex, Integer pageSize) throws Exception {
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append(
				"SELECT COUNT(ip.IBM_PLAN_ID_) FROM ibm_plan ip LEFT JOIN ibm_game ig ON ip.GAME_ID_ = ig.IBM_GAME_ID_ where ip.state_ ='OPEN' ");
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT ip.IBM_PLAN_ID_,ip.PLAN_NAME_,ig.GAME_NAME_,ip.PLAN_CODE_,ip.PLAN_TYPE_,ip.PLAN_EXPLAIN_,ip.PLAN_WORTH_T_ FROM ibm_plan ip LEFT JOIN ibm_game ig ON ip.GAME_ID_ = ig.IBM_GAME_ID_ where ip.state_ = 'OPEN' ");
		ArrayList<Object> parameters = null;
		ArrayList<Object> parametersCount = null;
		if (StringTool.notEmpty(planName)) {
			parameters = new ArrayList<>();
			parametersCount = new ArrayList<>();
			parameters.add("%" + planName + "%");
			parametersCount.add("%" + planName + "%");
			sql.append("and ip.PLAN_NAME_ like ? ");
			sqlCount.append("and ip.PLAN_NAME_ like ? ");
		}
		sql.append("order by ip.UPDATE_TIME_ desc");

		return dao.page(sql.toString(), parameters, pageIndex, pageSize, sqlCount.toString(), parametersCount);
	}

	/**
	 * 获取分页IbmPlanT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmPlanT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_plan where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_plan  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_plan  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmPlanT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_plan  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmPlanT数据信息
	 *
	 * @return 可用<IbmPlanT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_plan  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmPlanT.class, sql);
	}

	/**
	 * 根据方案coed获取方案信息
	 *
	 * @param planCode 方案coed
	 * @return <IbmPlanT>数据信息
	 */
	public IbmPlanT findByCode(String planCode) throws Exception {
		String sql = "SELECT * FROM ibm_plan  where PLAN_CODE_ = ? and state_!='DEL' order by UPDATE_TIME_ desc";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(planCode);
		return (IbmPlanT) super.dao.findObject(IbmPlanT.class, sql, parameterList);
	}
	/**
	 * 通过游戏id和用户id获取所有方案
	 *
	 * @param gameId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> listPlan(String gameId, String userId) throws Exception {
		String sql = "select ip.SN_,ipu.* from ibm_plan_user ipu LEFT JOIN ibm_plan ip ON "
				+ "ipu.PLAN_ID_=ip.IBM_PLAN_ID_ where ipu.GAME_ID_=? and ipu.APP_USER_ID_=? and ipu.STATE_!=? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(gameId);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findMapList(sql, parameterList);
	}

	/**
	 * @param ibmPlanId
	 * @param appUserId 返回类型 void
	 * @throws SQLException
	 * @Title: delIbmPlanById
	 * @Description: 通过方案id逻辑删除玩家方案
	 * <p>
	 * 参数说明
	 */
	private void delIbmPlan(String ibmPlanId, String appUserId, String className) throws SQLException {
		String sql = "update ibm_plan_user set DESC_=?,STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? WHERE PLAN_ID_=? and APP_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>(5);
		Date nowTime = new Date();
		parameterList.add(className + "通过方案id逻辑删除玩家方案");
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(ibmPlanId);
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}

	/**
	 * 根据类型获取方案
	 *
	 * @param type 用户类型
	 * @return 方案列表
	 */
	public List<IbmPlanT> listByType(String type) throws Exception {

		String sql = "SELECT * FROM ibm_plan  where 1 = 1 ";
		List<Object> parameterList = new ArrayList<>(1);
		if (type.equals(IbmTypeEnum.FREE.name())) {
			sql += " and PLAN_TYPE_ = ? ";
			parameterList.add(IbmTypeEnum.FREE.name());
		}
		sql += " order by UPDATE_TIME_ desc ";
		return (List<IbmPlanT>) super.dao.findObjectList(IbmPlanT.class, sql, parameterList);
	}

	/**
	 * 根据游戏id获取方案信息列表
	 *
	 * @param gameId 游戏id
	 * @return IBM_PLAN_ID_, PLAN_ITEM_TABLE_NAME_
	 */
	public List<Map<String, Object>> listInfoByGameId(String gameId) throws SQLException {
		String sql = "SELECT IBM_PLAN_ID_,PLAN_CODE_ FROM ibm_plan  where GAME_ID_ = ? and state_ = ? order by UPDATE_TIME_ desc";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 根据游戏code，方案code获取方案id
	 *
	 * @param planCode 方案code
	 * @param gameCode 游戏code
	 * @return 方案id
	 */
	public String findIdByCode(String planCode, String gameCode) throws SQLException {
		String sql = "SELECT ip.IBM_PLAN_ID_ FROM ibm_plan ip LEFT JOIN ibm_game ig ON ip.GAME_ID_ = ig.IBM_GAME_ID_ "
				+ " WHERE ip.PLAN_CODE_ = ? AND ig.GAME_CODE_ = ? AND ip.state_ != ? AND ig.state_ != ? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(planCode);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("IBM_PLAN_ID_", sql, parameterList);
	}

	/**
	 * 根据游戏code，方案code获取方案id集合
	 *
	 * @param planCodes 方案code
	 * @param gameCode  游戏code
	 * @return 方案id
	 */
	public List<String> findIdByCodes(String[] planCodes, String gameCode) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ip.IBM_PLAN_ID_ FROM ibm_plan ip LEFT JOIN ibm_game ig ON ip.GAME_ID_ = ig.IBM_GAME_ID_ "
				+ "WHERE ig.GAME_CODE_ = ? AND ip.state_ != ? AND ig.state_ != ? AND ip.PLAN_CODE_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		for (String planCode : planCodes) {
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		return super.dao.findStringList("IBM_PLAN_ID_", sql.toString(), parameterList);
	}

	/**
	 * @param planCode  方案Code
	 * @param ibmGameId 游戏ID
	 * @return 方案信息
	 * @Description: 通过方案Code和游戏ID查找方案
	 * <p>
	 * 参数说明
	 */
	public IbmPlanT findByCodeId(String planCode, String ibmGameId) throws Exception {
		String sql = "SELECT * FROM ibm_plan  where PLAN_CODE_ = ? and GAME_ID_ = ? and state_!='DEL' order by UPDATE_TIME_ desc";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(planCode);
		parameterList.add(ibmGameId);
		return (IbmPlanT) super.dao.findObject(IbmPlanT.class, sql, parameterList);
	}

	/**
	 * @param planIds 方案ID集合
	 * @return 方案集合
	 * @Description: 通过方案ID集合查询方案
	 * <p>
	 * 参数说明
	 */
	public List<Map<String, Object>> findPlan(String planIds) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT IBM_PLAN_ID_,PLAN_ITEM_TABLE_NAME_,GAME_ID_,PLAN_NAME_ from ibm_plan where STATE_ = ? and IBM_PLAN_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String planId : planIds.split(",")) {
			sql.append("?,");
			parameterList.add(planId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}
	/**
	 *
	 * @param planIds
	 * @return
	 * @throws SQLException
	 */
	public Map<Object, Object> findExcPlan(List<String> planIds) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT IBM_PLAN_ID_ as key_,PLAN_ITEM_TABLE_NAME_ as value_ from ibm_plan where STATE_ = ? and IBM_PLAN_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String planId : planIds) {
			sql.append("?,");
			parameterList.add(planId);
		}

		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.findKVMap(sql.toString(),parameterList);
	}

	/**
	 * @return 方案ID集合
	 * @Description: 获取免费方案ID
	 */
	public List<String> findFreePayPlan() throws SQLException {
		String sql = "select IBM_PLAN_ID_ from ibm_plan where STATE_!=? AND PLAN_WORTH_T_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmTypeEnum.FREE.name());
		return super.dao.findStringList("IBM_PLAN_ID_", sql, parameterList);
	}

	/**
	 * 按照更新顺序查询所有方案名称
	 *
	 * @return 所有方案名称
	 */
	public List<Map<String, Object>> findAllPlan() throws Exception {
		String sql = "SELECT ip.IBM_PLAN_ID_,ip.PLAN_NAME_,ig.GAME_NAME_ FROM ibm_plan ip LEFT JOIN ibm_game ig ON ip.GAME_ID_ = ig.IBM_GAME_ID_ where ip.state_=? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.OPEN.name());
		return this.dao.findMapList(sql, parameterList);
	}

	/**
	 * 根据方案code获取方案id集合
	 *
	 * @param planCodes 方案code
	 * @return 方案id
	 */
	public List<String> findId(String[] planCodes, String gameId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IBM_PLAN_ID_ FROM ibm_plan WHERE state_ != ? AND GAME_ID_ = ? AND PLAN_CODE_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(gameId);
		for (String planCode : planCodes) {
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		return super.dao.findStringList("IBM_PLAN_ID_", sql.toString(), parameterList);
	}
	/**
	 * 根据方案code和游戏id获取方案id
	 *
	 * @param planCode 方案code
	 * @param gameId   游戏id
	 * @return
	 * @throws SQLException
	 */
	public String findId(String planCode, String gameId) throws SQLException {
		String sql = "SELECT IBM_PLAN_ID_ FROM ibm_plan WHERE state_ != ? AND GAME_ID_ = ? AND PLAN_CODE_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(gameId);
		parameterList.add(planCode);
		return super.dao.findString("IBM_PLAN_ID_", sql, parameterList);
	}

	/**
	 * @return 默认方案
	 * 返回类型 List<Map<String,Object>>
	 * @throws Exception
	 * @Description: 获取默认方案
	 * <p>
	 * 参数说明
	 */
	public List<IbmPlanT> findDefPayPlan() throws Exception {
		String sql = "SELECT * FROM ibm_plan WHERE STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEF.name());
		return super.dao.findObjectList(IbmPlanT.class, sql, parameterList);
	}

	/**
	 * @return 返回类型 Map<String,Object>
	 * @throws SQLException
	 * @Description: 通过游戏id查询方案
	 * <p>
	 * 参数说明
	 */
	public List<Map<String, Object>> findByGameId(String gameId) throws SQLException {
		String sql = "SELECT * FROM ibm_plan WHERE STATE_ != ? AND GAME_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(gameId);
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 根据游戏id获取方案id列表
	 *
	 * @param gameId 游戏id
	 * @return IBM_PLAN_ID_
	 */
	public List<String> listIdByGameId(String gameId) throws SQLException {
		String sql = "SELECT IBM_PLAN_ID_ FROM ibm_plan  where GAME_ID_ = ? and state_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findStringList("IBM_PLAN_ID_", sql, parameterList);
	}

	public Map<Object, Object> findIdAndTableName(String[] planCodes, String gameId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT IBM_PLAN_ID_ AS key_,PLAN_ITEM_TABLE_NAME_ AS value_ FROM ibm_plan WHERE GAME_ID_ = ? AND PLAN_CODE_ IN (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(gameId);
		for (String planCode : planCodes) {
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.deleteCharAt(sql.length() - 1).append(")");

		return super.findKVMap(sql.toString(), parameterList);
	}
	/**
	 * 根据方案code获取方案id集合
	 *
	 * @param planIds 方案code
	 * @return 方案id
	 */
	public Map<Object, Object> findTableById(List<String> planIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT IBM_PLAN_ID_ AS key_,PLAN_ITEM_TABLE_NAME_ AS value_ FROM ibm_plan WHERE state_ != ? AND IBM_PLAN_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.DEL.name());
		for (String planId : planIds) {
			sql.append("?,");
			parameterList.add(planId);
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		return super.findKVMap(sql.toString(), parameterList);
	}

	/**
	 * 更新信息
	 *
	 * @param plainId           方案id
	 * @param planName          方案名称
	 * @param planItemTableName 方案详情表名称
	 * @param planExplain       方案说明
	 * @param planType          方案类型
	 * @param planWorth         方案价值
	 */
	public void update(String plainId, String planName, String planItemTableName, String planExplain, String planType,
			String planWorth, String className) throws SQLException {
		String sql =
				"UPDATE `ibm_plan` set DESC_=?,PLAN_NAME_ = ?,PLAN_ITEM_TABLE_NAME_ = ?,PLAN_EXPLAIN_ = ?,PLAN_TYPE_ = ?,"
						+ " PLAN_WORTH_T_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE IBM_PLAN_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(className + "更新信息");
		parameterList.add(planName);
		parameterList.add(planItemTableName);
		parameterList.add(planExplain);
		parameterList.add(planType);
		parameterList.add(NumberTool.longValueT(planWorth));
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(plainId);
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 更新信息
	 *
	 * @param gameId            游戏id
	 * @param planId           方案id1
	 * @param planName          方案名称
	 * @param planItemTableName 方案详情表名称
	 * @param planExplain       方案说明
	 * @param planType          方案类型
	 * @param planWorth         方案价值
	 */
	public void update(String planId, String planName, String gameId, String planItemTableName, String planExplain,
			String planType, String planWorth, String className) throws SQLException {
		String sql =
				"UPDATE `ibm_plan` set DESC_=?,GAME_ID_ = ?,PLAN_NAME_ = ?,PLAN_ITEM_TABLE_NAME_ = ?,PLAN_EXPLAIN_ = ?,"
						+ " PLAN_TYPE_ = ?,PLAN_WORTH_T_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE IBM_PLAN_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(className + "更新信息");
		parameterList.add(gameId);
		parameterList.add(planName);
		parameterList.add(planItemTableName);
		parameterList.add(planExplain);
		parameterList.add(planType);
		parameterList.add(NumberTool.longValueT(planWorth));
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(planId);
		super.dao.execute(sql, parameterList);
	}

}
