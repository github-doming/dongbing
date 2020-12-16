package com.ibm.old.v1.cloud.ibm_plan_user.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_plan.t.entity.IbmPlanT;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemT;
import com.ibm.old.v1.cloud.ibm_plan_user.t.entity.IbmPlanUserT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmPlanUserTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmPlanUserT对象数据
	 */
	public String save(IbmPlanUserT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_plan_user的 IBM_PLAN_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_plan_user set state_='DEL' where IBM_PLAN_USER_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_PLAN_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_plan_user的 IBM_PLAN_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_plan_user set state_='DEL' where IBM_PLAN_USER_ID_ in(" + stringBuilder.toString()
					+ ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_plan_user的 IBM_PLAN_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_plan_user where IBM_PLAN_USER_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_PLAN_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_plan_user的 IBM_PLAN_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_plan_user where IBM_PLAN_USER_ID_ in(" + stringBuilder.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmPlanUserT实体信息
	 *
	 * @param entity IbmPlanUserT实体
	 */
	public void update(IbmPlanUserT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_plan_user表主键查找IbmPlanUserT实体
	 *
	 * @param id ibm_plan_user 主键
	 * @return IbmPlanUserT实体
	 */
	public IbmPlanUserT find(String id) throws Exception {
		return (IbmPlanUserT) this.dao.find(IbmPlanUserT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_plan_user where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_plan_user  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_plan_user  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmPlanUserT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmPlanUserT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_plan_user where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_plan_user  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_plan_user  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmPlanUserT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_plan_user  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmPlanUserT数据信息
	 *
	 * @return 可用<IbmPlanUserT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_plan_user  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findObjectList(IbmPlanUserT.class, sql);
	}

	/**
	 * 根据用户id和方案详情表名称获取用户方案信息
	 *
	 * @param userId            用户id
	 * @param planItemTableName 方案详情表名称
	 * @return 用户方案信息
	 */
	public IbmPlanUserT findByUserIdAndTableName(String userId, String planItemTableName) throws Exception {
		String sql = "select * from ibm_plan_user where APP_USER_ID_= ? and PLAN_ITEM_TABLE_NAME_ = ? and STATE_ =? " ;
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(planItemTableName);
		parameterList.add(IbmStateEnum.OPEN.name());
		return (IbmPlanUserT) super.dao.findObject(IbmPlanUserT.class, sql, parameterList);
	}

	/**
	 * 根据方案详情表id查询游戏主键GAME_ID
	 * @param planItemid 方案详情id
	 * @return
	 */
	public String findTablePlanId(String planItemid) throws SQLException {
		String sql="select GAME_ID_ from ibm_plan_user where PLAN_ITEM_TABLE_ID_=? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(planItemid);
		return 	super.dao.findString("GAME_ID_",sql,parameterList);

	}
	/**
	 * @param planIdList 方案ID集合
	 * @param appUserId  用户ID
	 * @param state      方案状态
	 * @Description: 修改选中方案状态
	 * <p>
	 * 参数说明
	 */
	public void updateAllState(List<String> planIdList, String appUserId, String state,String className) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"UPDATE `ibm_plan_user` set DESC_=?,STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where "
						+ " APP_USER_ID_ = ? and PLAN_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		Date nowTime = new Date();
		parameterList.add(className+"修改选中方案状态");
		parameterList.add(state);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(appUserId);
		for (String planId : planIdList) {
			sql.append(" ?,");
			parameterList.add(planId);
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * @param planId 方案ID
	 * @param userId 用户ID
	 * @return
	 * @Description: 通过方案ID和用户ID查询方案
	 * <p>
	 * 参数说明
	 */
	public IbmPlanUserT findByPlanId(String planId, String userId) throws Exception {
		String sql = "select * from ibm_plan_user where PLAN_ID_ = ? and APP_USER_ID_ = ? and STATE_ != ? " ;
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(planId);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return (IbmPlanUserT) super.dao.findObject(IbmPlanUserT.class, sql, parameterList);
	}

	/**
	 * @param userId 用户ID
	 * @return 方案ID集合
	 * @throws SQLException
	 * @Description: 通过用户ID查询方案ID集合
	 * <p>
	 * 参数说明
	 */
	public List<String> listPlanByUserId(String userId) throws SQLException {
		String sql = "SELECT PLAN_ID_ from ibm_plan_user where APP_USER_ID_ = ? and state_!=? " ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findStringList("PLAN_ID_", sql, parameterList);
	}

	/**
	 * @param excPlanIds 方案ID集合
	 * @throws SQLException
	 * @Description: 根据方案ID集合删除方案
	 * <p>
	 * 参数说明
	 */
	public void delByPlanIds(List<String> excPlanIds,String userId) throws SQLException {
		StringBuilder sql = new StringBuilder("update ibm_plan_user set DESC_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,state_=? where APP_USER_ID_=? and PLAN_ID_ in (");
		List<Object> parameters = new ArrayList<>();
		parameters.add("根据方案ID集合删除方案");
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(userId);
		for (String excPlanId : excPlanIds) {
			sql.append("?,");
			parameters.add(excPlanId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}


	/**
	 * 获取方案限制信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param planId           方案id
	 * @return 方案限制信息
	 */
	public Map<String, Object> getLimitInfo(String handicapMemberId, String planId) throws SQLException {
		String sql = "SELECT PROFIT_LIMIT_MAX_T_,LOSS_LIMIT_MIN_T_ FROM `ibm_plan_user` ipu "
				+ " LEFT JOIN ibm_handicap_member ihm ON ipu.APP_USER_ID_ = ihm.APP_USER_ID_ "
				+ " WHERE ihm.IBM_HANDICAP_MEMBER_ID_ = ? and PLAN_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(planId);
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 后台管理分页
	 * @param planName 方案名称
	 * @return 分页信息
	 */
	public PageCoreBean find(String planName, String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize) throws SQLException {
		StringBuilder sqlCount = new StringBuilder();
		sqlCount.append("SELECT count(IBM_PLAN_USER_ID_) FROM ibm_handicap_user where state_!='DEL' ");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IBM_PLAN_USER_ID_,PLAN_NAME_,(SELECT APP_USER_NAME_ FROM app_user WHERE APP_USER_ID_ = ipu.APP_USER_ID_) APP_USER_NAME," +
				"(SELECT GAME_NAME_ FROM ibm_game WHERE IBM_GAME_ID_ = ipu.GAME_ID_) GAME_NAME,PROFIT_LIMIT_MAX_T_,LOSS_LIMIT_MIN_T_,MONITOR_PERIOD_," +
				"BET_MODE_,OPERATE_FREQUENCY_ FROM ibm_plan_user ipu where state_!='DEL' ");
		if(!StringUtil.isBlank(planName)){
			ArrayList<Object> parameters = new ArrayList<>();
			ArrayList<Object> parametersCount = new ArrayList<>();
			parameters.add("%"+planName+"%");
			parametersCount.add("%"+planName+"%");
			sql.append("and PLAN_NAME_ like ? ");
			sqlCount.append("and PLAN_NAME_ like ? ");
			if (StringUtil.isBlank(sortFieldName)||StringUtil.isBlank(sortOrderName)) {
				sql.append("order by UPDATE_TIME_LONG_ desc");
			}else{
				sql.append("order by ").append(sortFieldName).append(" ").append(sortOrderName);
			}
			return dao.page(sql.toString(), parameters, pageIndex, pageSize, sqlCount.toString(),parametersCount );
		}else{
			if (StringUtil.isBlank(sortFieldName)||StringUtil.isBlank(sortOrderName)) {
				sql.append("order by UPDATE_TIME_LONG_ desc");
			}else{
				sql.append("order by ").append(sortFieldName).append(" ").append(sortOrderName);
			}
			return dao.page(sql.toString(), null, pageIndex, pageSize, sqlCount.toString() );
		}
	}

	/**
	 * 修改止盈止损监控期数及投注模式信息
	 * @param profitLimitMaxT 止盈
	 * @param lossLimitMinT 止损
	 * @param betMode 投注模式
	 * @param monitorPeriod 监控期数
	 * @param userId 用户id
	 * @param planItemId 盘口详情id
	 */
    public void update(long profitLimitMaxT, long lossLimitMinT, String betMode, String monitorPeriod, String userId, String planItemId,String className) throws SQLException {
    	String sql = "UPDATE ibm_plan_user SET DESC_=?,PROFIT_LIMIT_MAX_T_ = ?,LOSS_LIMIT_MIN_T_ = ?,MONITOR_PERIOD_=?" +
				",BET_MODE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? WHERE APP_USER_ID_ = ? AND PLAN_ITEM_TABLE_ID_ = ? AND STATE_ != ? ";
    	List<Object> parameterList = new ArrayList<>(9);
		parameterList.add(className+"修改止盈止损监控期数及投注模式信息");
    	parameterList.add(profitLimitMaxT);
    	parameterList.add(lossLimitMinT);
    	parameterList.add(monitorPeriod);
    	parameterList.add(betMode);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
    	parameterList.add(userId);
    	parameterList.add(planItemId);
    	parameterList.add(IbmStateEnum.DEL.name());
    	super.dao.execute(sql,parameterList);
    }

	/**
	 * 获取盘口会员所有方案
	 * @param handicapMemberId 盘口会员id
	 * @return 盘口会员所有方案
	 */
	public List<Map<String, Object>> findPlanIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "SELECT pu.STATE_,pu.IBM_PLAN_USER_ID_,pu.PLAN_ID_,pu.GAME_ID_,pu.PLAN_ITEM_TABLE_ID_ FROM ibm_plan_user pu LEFT JOIN ibm_handicap_member hm " +
				" ON hm.APP_USER_ID_ = pu.APP_USER_ID_ WHERE hm.IBM_HANDICAP_MEMBER_ID_ = ? AND pu.STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql,parameterList);
	}

	/**
	 * 通过用户id获取方案详情id
	 * @param userId 用户id
	 * @param planIds 方案id
	 * @return 方案详情id
	 */
	public List<String> listPlanItemIdByPlan(String userId,List<String> planIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT PLAN_ITEM_TABLE_ID_ FROM ibm_plan_user WHERE APP_USER_ID_ = ? AND STATE_ != ? AND PLAN_ID_ IN ( ");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		for (String planId : planIds){
			sql.append("?,");
			parameterList.add(planId);
		}
		sql.replace(sql.length()-1,sql.length(),")");

		return super.dao.findStringList("PLAN_ITEM_TABLE_ID_",sql.toString(),parameterList);

	}
	/**
	 * 保存用户方案
	 * @param planT 方案
	 * @param planItemT 方案详情
	 * @param userId 用户id
	 * @return  用户方案ID
	 */
	public String save(IbmPlanT planT, IbmPlanItemT planItemT, String userId) throws Exception {
		IbmPlanUserT planUserT = new IbmPlanUserT();
		planUserT.setPlanId(planT.getIbmPlanId());
		planUserT.setPlanItemTableName(planItemT.getName());
		planUserT.setPlanItemTableId(planItemT.getId());
		planUserT.setAppUserId(userId);
		planUserT.setGameId(planT.getGameId());
		planUserT.setPlanName(planT.getPlanName());
		planUserT.setPlanIcon(planT.getPlanIcon());
		planUserT.setProfitLimitMaxT(planItemT.getProfitLimitMaxT());
		planUserT.setLossLimitMinT(planItemT.getLossLimitMinT());
		planUserT.setMonitorPeriod(planItemT.getMonitorPeriod());
		planUserT.setBetMode(planItemT.getBetMode());
		planUserT.setOperateFrequency(0);
		planUserT.setCreateTime(new Date());
		planUserT.setCreateTimeLong(System.currentTimeMillis());
		planUserT.setUpdateTime(new Date());
		planUserT.setCreateTimeLong(System.currentTimeMillis());
		planUserT.setState(IbmStateEnum.CLOSE.name());
		return save(planUserT);
	}

	/**
	 * 根据方案详情表id查找方案主键PLAN_ID_
	 * @param planItemId 方案详情表id
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public String findPlanIdByTableId(String planItemId) throws SQLException {
		String sql = "SELECT PLAN_ID_ FROM  ibm_plan_user  WHERE PLAN_ITEM_TABLE_ID_ = ?  " ;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(planItemId);
		return super.dao.findString("PLAN_ID_", sql, parameterList);
	}

	/**
	 * 查找出用户方案信息
	 *
	 * @param planId 方案id
	 * @param userId 用户id
	 * @return 用户方案信息
	 */
	public String findUserPlanItemInfoById(String planId, String userId) throws SQLException {
		String sql = "SELECT PLAN_ITEM_TABLE_ID_ FROM `ibm_plan_user` where "
				+ " APP_USER_ID_ = ? and PLAN_ID_ = ? and STATE_ != ? " ;
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(planId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("PLAN_ITEM_TABLE_ID_",sql, parameterList);
	}

}
