package com.ibm.old.v1.cloud.ibm_hm_set.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.app.ibm_hm_set.action.IbmHandicapEditAction;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHmSetTService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHmSetT对象数据
	 */
	public String save(IbmHmSetT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_hm_set的 IBM_HM_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_hm_set set state_='DEL' where IBM_HM_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}
	/**
	 * 查找显示项
	 *
	 * @param hmSetId 盘口设置主键id
	 * @return 盘口设置显示项
	 */
	public Map<String, Object> findShow(String hmSetId) throws SQLException {
		String sql = "select * from ibm_hm_set where IBM_HM_SET_ID_=? andSTATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(hmSetId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 查找出用户盘口设置
	 *
	 * @param handicapMemberId 盘口会员主键id
	 * @param userId           用户id
	 * @return 用户方案信息
	 */
	public IbmHmSetT findHmSet(String handicapMemberId, String userId) throws Exception {
		String sql = "SELECT *  from `ibm_hm_set` where "
				+ " APP_USER_ID_ = ? and HANDICAP_MEMBER_ID_ = ? and STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return (IbmHmSetT)super.dao.findObject(IbmHmSetT.class, sql, parameterList);
	}

	/**
	 * 逻辑删除IBM_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_set的 IBM_HM_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_hm_set set state_='DEL' where IBM_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_hm_set的 IBM_HM_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_hm_set where IBM_HM_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HM_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_set的 IBM_HM_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_hm_set where IBM_HM_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHmSetT实体信息
	 *
	 * @param entity IbmHmSetT实体
	 */
	public boolean update(IbmHmSetT entity) throws Exception {
		Integer update = dao.update(entity);
		transactionEnd(jdbcTool);
		return update==1;
	}


	/**
	 * 根据ibm_hm_set表主键查找IbmHmSetT实体
	 *
	 * @param id ibm_hm_set 主键
	 * @return IbmHmSetT实体
	 */
	public IbmHmSetT find(String id) throws Exception {
		return (IbmHmSetT) this.dao.find(IbmHmSetT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_hm_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHmSetT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHmSetT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_set where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHmSetT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHmSetT数据信息
	 *
	 * @return 可用<IbmHmSetT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_set  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHmSetT.class, sql);
	}

	/**
	 * 根据盘口会员id获取盘口用户设置
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 盘口用户设置
	 */
	public IbmHmSetT findByHandicapMemberId(String handicapMemberId) throws Exception {
		String sql = "SELECT * FROM ibm_hm_set  where HANDICAP_MEMBER_ID_ = ? and STATE_!=? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(handicapMemberId);
		parameters.add(IbmStateEnum.DEL.name());
		return (IbmHmSetT) super.dao.findObject(IbmHmSetT.class, sql, parameters);
	}

	/**
	 * @return 盘口设置默认数据
	 * @Description: 查询盘口设置默认数据
	 */
	public IbmHmSetT findDefEntity() throws Exception {
		String sql = "SELECT * from ibm_hm_set where STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEF.name());
		return (IbmHmSetT) super.dao.findObject(IbmHmSetT.class, sql, parameterList);
	}

	/**
	 * 获取 盘口会员投注比例map
	 *
	 * @param handicapMemberIds 盘口会员id 集合
	 * @return 盘口会员投注比例map
	 */
	public Map<Object, Object> mapHmBetRate(Set<Object> handicapMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder("select HANDICAP_MEMBER_ID_ as key_, BET_RATE_T_ as value_ from ");
		sql.append(" ibm_hm_set where STATE_= ? and HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>(handicapMemberIds.size() + 1);
		parameterList.add(IbmStateEnum.OPEN.name());
		for (Object handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(") ");
		return super.findKVMap(sql.toString(), parameterList);
	}

	/**
	 * 获取投注模式
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 投注模式
	 */
	public String findBetMerger(String handicapMemberId) throws SQLException {
		String sql = "select BET_MERGER_ from ibm_hm_set where HANDICAP_MEMBER_ID_=? and STATE_= ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("BET_MERGER_", sql, parameterList);
	}

	/**
	 * 自定义重置方案
	 *
	 * @param type 限制类型
	 */
	public void customReset(String type,String className) throws Exception {
		String tableName = "ibm_profit_plan";
		if (IbmTypeEnum.VIRTUAL.name().equals(type)) {
			tableName = "ibm_profit_plan_vr";
		}
		String sql = "SELECT pp.PLAN_ID_ AS key_,pp.HANDICAP_MEMBER_ID_ AS value_ FROM ibm_hm_set hms"
				+ " LEFT JOIN ibm_handicap_member hm ON hms.HANDICAP_MEMBER_ID_ = hm.IBM_HANDICAP_MEMBER_ID_"
				+ " LEFT JOIN " + tableName + " pp on hms.HANDICAP_MEMBER_ID_=pp.HANDICAP_MEMBER_ID_"
				+ " WHERE hms.RESET_TYPE_ =? and hm.LOGIN_STATE_=? and pp.STATE_=? AND ((hms.RESET_LOSS_MIN_T_ != 0"
				+ " AND PROFIT_T_ < hms.RESET_LOSS_MIN_T_) OR (hms.RESET_PROFIT_MAX_T_ != 0 AND PROFIT_T_ > hms.RESET_PROFIT_MAX_T_))"
				+ " and hm.STATE_!=?";

		List<Object> parameterList = new ArrayList<>();
		parameterList.add("2");
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, List<String>> mapList = super.findKVsMapList(sql, parameterList);
		if (ContainerTool.isEmpty(mapList)) {
			return;
		}

		for (Map.Entry<String, List<String>> entry : mapList.entrySet()) {
			parameterList.clear();
			String planId = entry.getKey();
			List<String> handicapMemberIds = entry.getValue();

			sql = "select ig.GAME_CODE_ from ibm_plan ip LEFT JOIN ibm_game ig on ip.GAME_ID_=ig.IBM_GAME_ID_"
					+ " where ip.IBM_PLAN_ID_=? and ip.STATE_=? and ig.STATE_=?";
			parameterList.add(planId);
			parameterList.add(IbmStateEnum.OPEN.name());
			parameterList.add(IbmStateEnum.OPEN.name());
			String gameCode = super.dao.findString("GAME_CODE_", sql, parameterList);
			if (StringTool.isEmpty(gameCode)) {
				continue;
			}
			String period = PeriodTool.findLotteryPeriod(gameCode);
			//重置方案投注项状态
			parameterList.clear();
			sql = "update ibm_exec_plan_group SET DESC_=?,STATE_ =?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? WHERE STATE_=? AND PERIOD_ = ?  "
					+ " and PLAN_ID_=? and HANDICAP_MEMBER_ID_ in ('" + String.join("','", handicapMemberIds) + "')";
			parameterList.add(className+"重置方案投注项状态");
			parameterList.add(IbmStateEnum.REPLAY.name());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(IbmStateEnum.OPEN.name());
			parameterList.add(period);
			parameterList.add(planId);
			super.dao.execute(sql, parameterList);

			parameterList.clear();
			sql = "update " + tableName + " set DESC_=?,STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where PLAN_ID_=? and "
					+ " STATE_=? and HANDICAP_MEMBER_ID_ in ('" + String.join("','", handicapMemberIds) + "')";
			parameterList.add(className+"重置方案");
			parameterList.add(IbmStateEnum.REPLAY.name());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(planId);
			parameterList.add(IbmStateEnum.OPEN.name());
			super.dao.execute(sql, parameterList);
		}
	}
	/**
	 * 每天重置方案
	 * 1,获取所有每天重置方案的盘口会员
	 * 2,获取上一期期数，并修改上一期的转码情况状态为REPLAY
	 * 3,修改盘口会员的方案盈亏情况
	 *
	 * @param type  限制类型
	 * @param games 游戏
	 */
	public void everyDayReset(String type, IbmGameEnum[] games,String className) throws Exception {
		String tableName = "ibm_profit_plan";
		if (IbmTypeEnum.VIRTUAL.name().equals(type)) {
			tableName = "ibm_profit_plan_vr";
		}
		String sql = "select HANDICAP_MEMBER_ID_ from ibm_hm_set where RESET_TYPE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add("1");
		parameterList.add(IbmStateEnum.OPEN.name());
		List<String> handicapMemberIds = super.dao.findStringList("HANDICAP_MEMBER_ID_", sql, parameterList);
		if (ContainerTool.isEmpty(handicapMemberIds)) {
			return;
		}

		for (IbmGameEnum game : games) {
			parameterList.clear();
			Object period = PeriodTool.findLotteryPeriod(game);

			sql = "select IBM_PLAN_ID_ from ibm_plan ip left join ibm_game ig on ip.GAME_ID_=ig.IBM_GAME_ID_"
					+ " where ig.GAME_CODE_=? and ig.STATE_=? and ip.STATE_=?";
			parameterList.add(game.name());
			parameterList.add(IbmStateEnum.OPEN.name());
			parameterList.add(IbmStateEnum.OPEN.name());
			List<String> planIds = super.dao.findStringList("IBM_PLAN_ID_", sql, parameterList);

			for (String planId : planIds) {
				parameterList.clear();
				sql = "select HANDICAP_MEMBER_ID_ from ibm_plan_hm where PLAN_ID_=? and HANDICAP_MEMBER_ID_ in('"
						+ String.join("','", handicapMemberIds) + "')";
				parameterList.add(planId);
				List<String> planHmIds = super.dao.findStringList("HANDICAP_MEMBER_ID_", sql, parameterList);

				parameterList.clear();
				sql = "update ibm_exec_plan_group set DESC_=?, STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where PERIOD_=? and STATE_=?"
						+ " and PLAN_ID_=? and HANDICAP_MEMBER_ID_ in ('" + String.join("','", planHmIds) + "')";
				parameterList.add(className+"重置执行方案组");
				parameterList.add(IbmStateEnum.REPLAY.name());
				parameterList.add(new Date());
				parameterList.add(System.currentTimeMillis());
				parameterList.add(period.toString());
				parameterList.add(IbmStateEnum.OPEN.name());
				parameterList.add(planId);
				super.dao.execute(sql, parameterList);
				parameterList.clear();

				sql = "update " + tableName + " set DESC_=?,STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where PLAN_ID_=? and "
						+ " STATE_=? and HANDICAP_MEMBER_ID_ in ('" + String.join("','", planHmIds) + "')";
				parameterList.add(className+"重重置方案");
				parameterList.add(IbmStateEnum.REPLAY.name());
				parameterList.add(new Date());
				parameterList.add(System.currentTimeMillis());
				parameterList.add(planId);
				parameterList.add(IbmStateEnum.OPEN.name());
				super.dao.execute(sql, parameterList);
			}
		}
	}

	/**
	 * @return 获取定时登录盘口会员
	 */
	public List<String> findLoginTime() throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ FROM ibm_hm_set WHERE LANDED_TIME_ IS NOT NULL"
				+ " AND SAVE_ACCOUNT_ = ? AND LANDED_TIME_ BETWEEN DATE_SUB(curtime(), INTERVAL 1 MINUTE) "
				+ " AND curtime() AND STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmTypeEnum.TRUE.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findStringList("HANDICAP_MEMBER_ID_", sql, parameterList);
	}

	/**
	 * 根据id查找盘口会员设置信息
	 *
	 * @param handicapMemberId 盘口会员ID
	 * @param appUserId        用户ID
	 * @return 盘口会员设置信息
	 */
	public Map<String, Object> findInfoById(String handicapMemberId, String appUserId) throws SQLException {
		String sql = "SELECT BET_RATE_T_, PROFIT_LIMIT_MAX_T_,LOSS_LIMIT_MIN_T_,PROFIT_LIMIT_MIN_T_,RESET_TYPE_,"
				+ " RESET_PROFIT_MAX_T_,RESET_LOSS_MIN_T_,BLAST_STOP_ FROM `ibm_hm_set` where HANDICAP_MEMBER_ID_ = ? and "
				+ " APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 修改合并状态
	 *
	 * @param betMerger        合并状态
	 * @param handicapMemberId 盘口会员ID
	 * @return 修改结果
	 */
	public boolean updateBetMerger(String betMerger, String handicapMemberId) throws SQLException {
		String sql = "update ibm_hm_set set BET_MERGER_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_=?,DESC_ = ?"
				+ " where HANDICAP_MEMBER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(betMerger);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(IbmHandicapEditAction.class.getName() + "修改投注合并状态");
		parameterList.add(handicapMemberId);
		return super.dao.execute(sql, parameterList) == 1;
	}
	/**
	 * 根据盘口会员id删除盘口设置信息
	 * @param handicapMemberList			盘口会员id
	 */
	public void delByHmId(List<String> handicapMemberList) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibm_hm_set set STATE_='DEL',UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for(String handicapMemberId:handicapMemberList){
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(),parameterList);
	}
}
