package com.cloud.recharge.card_recharge_daily.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.cloud.recharge.card_recharge_daily.entity.CardRechargeDaily;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 充值卡报表 服务类
 *
 * @author Robot
 */
public class CardRechargeDailyService extends BaseServiceProxy {

	/**
	 * 保存充值卡报表 对象数据
	 *
	 * @param entity CardRechargeDaily对象数据
	 */
	public String save(CardRechargeDaily entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除card_recharge_daily 的 CARD_RECHARGE_DAILY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update card_recharge_daily set state_='DEL' where CARD_RECHARGE_DAILY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除CARD_RECHARGE_DAILY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 card_recharge_daily 的 CARD_RECHARGE_DAILY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update card_recharge_daily set state_='DEL' where CARD_RECHARGE_DAILY_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 card_recharge_daily  的 CARD_RECHARGE_DAILY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from card_recharge_daily where CARD_RECHARGE_DAILY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除CARD_RECHARGE_DAILY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除card_recharge_daily 的 CARD_RECHARGE_DAILY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from card_recharge_daily where CARD_RECHARGE_DAILY_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新CardRechargeDaily实体信息
	 *
	 * @param entity 充值卡报表 实体
	 */
	public void update(CardRechargeDaily entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据card_recharge_daily表主键查找 充值卡报表 实体
	 *
	 * @param id card_recharge_daily 主键
	 * @return 充值卡报表 实体
	 */
	public CardRechargeDaily find(String id) throws Exception {
		return dao.find(CardRechargeDaily.class, id);
	}

	/**
	 * 记录 充值卡报表 - 创建
	 *
	 * @param cardTreeId 卡种主键
	 * @param userId     用户主键
	 * @param dailyTime  报表时间
	 * @param cardNum    卡数量
	 * @param nowTime    记录时间
	 */
	public void recordDaily(String cardTreeId, String cardTreeName, String userId, String userName, String dailyTime,
			int cardNum, Date nowTime,String tableName) throws SQLException {
		String dailyId = findId(userId,cardTreeId, dailyTime,tableName);
		String sql;
		List<Object> parameters = new ArrayList<>(10);
		if (StringTool.isEmpty(dailyId)) {
			sql = "INSERT INTO "+tableName+" (CARD_RECHARGE_DAILY_ID_,CARD_TREE_ID_,USER_ID_,USER_NAME_,CARD_TREE_NAME_,CREATE_TOTAL_, "
					+ " ACTIVATE_TOTAL_,POINT_TOTAL_,PRICE_TOTAL_T_,PRICE_TOTAL_DOWN_T_,DAILY_TIME_,DAILY_TIME_LONG_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			parameters.add(RandomTool.onlyCode("RECHARGE_DAILY"));
			parameters.add(cardTreeId);
			parameters.add(userId);
			parameters.add(userName);
			parameters.add(cardTreeName);
			parameters.add(cardNum);
			// TODO
			parameters.add(0);
			parameters.add(0);
			parameters.add(0);
			parameters.add(0);

			parameters.add(dailyTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(System.currentTimeMillis());
			parameters.add(StateEnum.OPEN.name());
		} else {
			sql = "UPDATE "+tableName+" set CREATE_TOTAL_ = CREATE_TOTAL_ + ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
					+ " where CARD_RECHARGE_DAILY_ID_ = ?";
			parameters.add(cardNum);
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(dailyId);
		}
		super.execute(sql, parameters);
	}

	/**
	 * 记录 充值卡报表 携带使用数据
	 */
	public void recordDaily(String cardTreeId, String cardTreeName, String userId, String userName, String dailyTime,
			int createTotal,int activateTotal,	int point, Long priceT, long downPriceT, Date nowTime,String tableName) throws SQLException {
		String dailyId = findId(userId,cardTreeId, dailyTime,tableName);
		String sql;
		List<Object> parameters = new ArrayList<>(10);
		if (StringTool.isEmpty(dailyId)) {
			sql = "INSERT INTO "+tableName+" (CARD_RECHARGE_DAILY_ID_,CARD_TREE_ID_,USER_ID_,USER_NAME_,CARD_TREE_NAME_,CREATE_TOTAL_, "
					+ " ACTIVATE_TOTAL_,POINT_TOTAL_,PRICE_TOTAL_T_,PRICE_TOTAL_DOWN_T_,DAILY_TIME_,DAILY_TIME_LONG_,CREATE_TIME_, "
					+ " CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			parameters.add(RandomTool.onlyCode("RECHARGE_DAILY"));
			parameters.add(cardTreeId);
			parameters.add(userId);
			parameters.add(userName);
			parameters.add(cardTreeName);
			parameters.add(createTotal);
			parameters.add(activateTotal);
			parameters.add(point);
			parameters.add(priceT);
			parameters.add(downPriceT);
			parameters.add(dailyTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(System.currentTimeMillis());
			parameters.add(StateEnum.OPEN.name());
		} else {
			sql = "UPDATE "+tableName+" set CREATE_TOTAL_ = CREATE_TOTAL_ + ?,ACTIVATE_TOTAL_ = ACTIVATE_TOTAL_ + ?,"
					+ "POINT_TOTAL_ = POINT_TOTAL_ + ?,PRICE_TOTAL_T_ = PRICE_TOTAL_T_ + ?, PRICE_TOTAL_DOWN_T_=PRICE_TOTAL_DOWN_T_+?,"
					+ "UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? " + " where CARD_RECHARGE_DAILY_ID_ = ?";
			parameters.add(createTotal);
			parameters.add(activateTotal);
			parameters.add(point);
			parameters.add(priceT);
			parameters.add(downPriceT);
			parameters.add(nowTime);
			parameters.add(System.currentTimeMillis());
			parameters.add(dailyId);
		}
		super.execute(sql, parameters);

	}
	/**
	 * 查找充值卡报表主键
	 *
	 * @param userId    用户主键
	 * @param dailyTime 充值卡报表天
	 * @return 充值卡报表主键
	 */
	private String findId(String userId,String cardTreeId, String dailyTime,String tableName) throws SQLException {
		String sql = "SELECT CARD_RECHARGE_DAILY_ID_ as key_ FROM "+tableName
				+ " where USER_ID_ = ? and CARD_TREE_ID_=? and DAILY_TIME_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(userId);
		parameters.add(cardTreeId);
		parameters.add(dailyTime);
		parameters.add(StateEnum.OPEN.name());
		return super.findString(sql, parameters);

	}
	/**
	 * 获取报表详情
	 *
	 * @param cardTreeId 卡种主键
	 * @param subUserIds 子用户主键数组
	 * @param startTime  开始时间
	 * @param endTime    结束时间
	 * @return 以代理为组的某个代理的报表
	 */
	public PageCoreBean<Map<String, Object>> listReportItem4Agent(String cardTreeId, List<String> subUserIds, Long startTime,
			Long endTime, int pageIndex, int pageSize,String tableName) throws SQLException {
		String sqlCount = "SELECT count(*) from (";
		String sql = "SELECT CARD_TREE_NAME_,USER_NAME_,USER_ID_,SUM(CREATE_TOTAL_) CREATE_TOTAL_,SUM(ACTIVATE_TOTAL_) "
				+ " ACTIVATE_TOTAL_,SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_,SUM(POINT_TOTAL_) POINT_TOTAL_,SUM(PRICE_TOTAL_DOWN_T_) "
				+ " PRICE_TOTAL_DOWN_T_ FROM "+tableName+" WHERE STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(StateEnum.OPEN.name());
		if (StringTool.notEmpty(cardTreeId)) {
			sql += " AND CARD_TREE_ID_ = ? ";
			parameters.add(cardTreeId);
		}
		if (startTime != null && endTime != null) {
			sql += " AND DAILY_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		StringBuilder sqlPlus = new StringBuilder(" AND USER_ID_ in(");
		subUserIds.forEach(subUserId -> {
			sqlPlus.append("?,");
			parameters.add(subUserId);
		});
		sql += sqlPlus.deleteCharAt(sqlPlus.lastIndexOf(",")).append(")")
				.append(" GROUP BY USER_ID_ ORDER BY CREATE_TIME_LONG_");
		sqlCount = sqlCount + sql + ") AS t  ";
		return dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}

	/**
	 * 获取报表合计
	 *
	 * @param cardTreeId 卡种主键
	 * @param userId 代理主键数组
	 * @param startTime  开始时间
	 * @param endTime    结束时间
	 * @return 以代理为组的某个代理的报表
	 */
	public Map<String, Object> listReportTotal4Agent(String cardTreeId, String userId, Long startTime,
														  Long endTime) throws SQLException {
		String sql = "SELECT SUM(ACTIVATE_TOTAL_) ACTIVATE_TOTAL_,SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_," +
				"SUM(POINT_TOTAL_) POINT_TOTAL_,SUM(PRICE_TOTAL_DOWN_T_) PRICE_TOTAL_DOWN_T_ " +
				"FROM card_recharge_daily_sum WHERE STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(StateEnum.OPEN.name());
		if (StringTool.notEmpty(cardTreeId)) {
			sql+= " AND CARD_TREE_ID_ = ? ";
			parameters.add(cardTreeId);
		}
		if (startTime != null && endTime != null) {
			sql+=  " AND DAILY_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		if(StringTool.notEmpty(userId)){
			sql+= " AND USER_ID_ = ? ";
			parameters.add(userId);
		}
		return super.findMap(sql, parameters);
	}
	/**
	 * 获取报表详情
	 *
	 * @param cardTreeIds 卡种主键数组
	 * @param subUserIds  子用户主键数组
	 * @param startTime   开始时间
	 * @param endTime     结束时间
	 * @return 以卡种为组的某个代理的报表
	 */
	public List<Map<String, Object>> listReportItem4Card(List<String> cardTreeIds, List<String> subUserIds,
			Long startTime, Long endTime) throws SQLException {
		String sql = "SELECT CARD_TREE_NAME_,CARD_TREE_ID_,SUM(CREATE_TOTAL_) CREATE_TOTAL_,SUM(ACTIVATE_TOTAL_) "
				+ " ACTIVATE_TOTAL_,SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_,SUM(POINT_TOTAL_) POINT_TOTAL_, "
				+ " SUM(PRICE_TOTAL_DOWN_T_) PRICE_TOTAL_DOWN_T_ FROM card_recharge_daily_sum WHERE STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(StateEnum.OPEN.name());
		if (startTime != null && endTime != null) {
			sql += " AND DAILY_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		StringBuilder sqlPlus = new StringBuilder();
		if (ContainerTool.notEmpty(subUserIds)) {
			sqlPlus.append(" AND USER_ID_ in(");
			subUserIds.forEach(subUserId -> {
				sqlPlus.append("?,");
				parameters.add(subUserId);
			});
			sqlPlus.deleteCharAt(sqlPlus.lastIndexOf(",")).append(")");
		}

		if (ContainerTool.notEmpty(cardTreeIds)) {
			sqlPlus.append(" AND CARD_TREE_ID_ in(");
			cardTreeIds.forEach(cardTreeId -> {
				sqlPlus.append("?,");
				parameters.add(cardTreeId);
			});
			sqlPlus.deleteCharAt(sqlPlus.lastIndexOf(",")).append(")")
					.append(" GROUP BY CARD_TREE_ID_ ORDER BY CREATE_TIME_LONG_");
		}
		sql += sqlPlus;
		return super.findMapList(sql, parameters);

	}

	/**
	 * 获取报表合计
	 *
	 * @param cardTreeIds 卡种主键数组
	 * @param userId  用户主键
	 * @param startTime   开始时间
	 * @param endTime     结束时间
	 * @return 以卡种为组的某个代理的报表
	 */
	public Map<String, Object> listReportTotal4Card(List<String> cardTreeIds, String userId,
														 Long startTime, Long endTime) throws SQLException {
		String sql = "SELECT CARD_TREE_NAME_,CARD_TREE_ID_,SUM(CREATE_TOTAL_) CREATE_TOTAL_,SUM(ACTIVATE_TOTAL_) "
				+ " ACTIVATE_TOTAL_,SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_,SUM(POINT_TOTAL_) POINT_TOTAL_, "
				+ " SUM(PRICE_TOTAL_DOWN_T_) PRICE_TOTAL_DOWN_T_ FROM card_recharge_daily WHERE STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(StateEnum.OPEN.name());
		if (startTime != null && endTime != null) {
			sql += " AND DAILY_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		StringBuilder sqlPlus = new StringBuilder();
		if (StringTool.notEmpty(userId)) {
			sqlPlus.append(" AND USER_ID_ = ?");
			parameters.add(userId);
		}

		if (ContainerTool.notEmpty(cardTreeIds)) {
			sqlPlus.append(" AND CARD_TREE_ID_ in(");
			cardTreeIds.forEach(cardTreeId -> {
				sqlPlus.append("?,");
				parameters.add(cardTreeId);
			});
			sqlPlus.deleteCharAt(sqlPlus.lastIndexOf(",")).append(")");
		}
		sql += sqlPlus.append(" ORDER BY CREATE_TIME_LONG_");
		return super.findMap(sql, parameters);

	}

	/**
	 * 获取报表详情
	 *
	 * @return 以卡种为组的某个代理的报表
	 */
	public List<Map<String, Object>> listItem4Agent(String cardTreeId, String userId, Long startTime, Long endTime,String tableName)
			throws SQLException {
		String sql = "SELECT CARD_TREE_NAME_,SUM(CREATE_TOTAL_) CREATE_TOTAL_,SUM(ACTIVATE_TOTAL_) "
				+ " ACTIVATE_TOTAL_,SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_,SUM(POINT_TOTAL_) POINT_TOTAL_ "
				+ " FROM "+tableName+" WHERE USER_ID_ = ? AND STATE_ = ? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(userId);
		parameters.add(StateEnum.OPEN.name());
		if (StringTool.notEmpty(cardTreeId)) {
			sql += " AND CARD_TREE_ID_ = ? ";
			parameters.add(cardTreeId);
		}
		if (startTime != null && endTime != null) {
			sql += " AND DAILY_TIME_LONG_ BETWEEN ? AND ? ";
			parameters.add(startTime);
			parameters.add(endTime);
		}
		sql += " GROUP BY CARD_TREE_ID_ ORDER BY CREATE_TIME_LONG_";
		return super.findMapList(sql, parameters);
	}
	/**
	 * 获取报表详情
	 *
	 * @return 以代理为组的某个卡种的报表
	 */
	public PageCoreBean<Map<String, Object>> listItem4Card(String cardTreeId, List<String> subUserIds, Long startTime,
			Long endTime, int pageIndex, int pageSize,String tableName) throws SQLException {
		String sqlCount = "SELECT count(*) from (";
		StringBuilder sql = new StringBuilder("SELECT USER_NAME_,SUM(CREATE_TOTAL_) CREATE_TOTAL_,SUM(ACTIVATE_TOTAL_) "
				+ " ACTIVATE_TOTAL_,SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_,SUM(POINT_TOTAL_) POINT_TOTAL_ "
				+ " FROM " + tableName + " WHERE CARD_TREE_ID_ = ? AND STATE_ = ? AND USER_ID_ in(");
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(cardTreeId);
		parameters.add(StateEnum.OPEN.name());
		for(String userId:subUserIds){
			sql.append("?,");
			parameters.add(userId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		if (startTime != null && endTime != null) {
			sql.append(" AND DAILY_TIME_LONG_ BETWEEN ? AND ? ");
			parameters.add(startTime);
			parameters.add(endTime);
		}
		sql.append(" GROUP BY USER_ID_ ORDER BY CREATE_TIME_LONG_");
		sqlCount = sqlCount + sql + ") AS t  ";
		return dao.page(sql.toString(), parameters, pageIndex, pageSize, sqlCount, parameters);
	}
}
