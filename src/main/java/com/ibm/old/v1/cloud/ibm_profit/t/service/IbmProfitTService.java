package com.ibm.old.v1.cloud.ibm_profit.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_profit.t.entity.IbmProfitT;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmProfitTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmProfitT对象数据
	 */
	public String save(IbmProfitT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_profit的 IBM_PROFIT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_profit set state_='DEL' where IBM_PROFIT_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_PROFIT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_profit的 IBM_PROFIT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_profit set state_='DEL' where IBM_PROFIT_ID_ in(" + stringBuilder.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_profit的 IBM_PROFIT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_profit where IBM_PROFIT_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_PROFIT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_profit的 IBM_PROFIT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_profit where IBM_PROFIT_ID_ in(" + stringBuilder.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmProfitT实体信息
	 *
	 * @param entity IbmProfitT实体
	 */
	public void update(IbmProfitT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_profit表主键查找IbmProfitT实体
	 *
	 * @param id ibm_profit 主键
	 * @return IbmProfitT实体
	 */
	public IbmProfitT find(String id) throws Exception {
		return (IbmProfitT) this.dao.find(IbmProfitT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_profit where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_profit  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmProfitT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmProfitT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_profit where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_profit  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmProfitT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmProfitT数据信息
	 *
	 * @return 可用<IbmProfitT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findObjectList(IbmProfitT.class, sql);
	}
	/**
	 * 清理已经登出的用户
	 */
	public void clearLogout() throws SQLException {
		String sql = "SELECT IBM_HANDICAP_MEMBER_ID_ FROM `ibm_handicap_member` WHERE LOGIN_STATE_ = ? AND UPDATE_TIME_LONG_ > ?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.LOGOUT.name());
		parameters.add(System.currentTimeMillis() - IbmConfig.AUTO_JOB_TIME * 1.5);
		List<String> handicapMemberIds = super.dao.findStringList("IBM_HANDICAP_MEMBER_ID_", sql, parameters);
		if (ContainerTool.isEmpty(handicapMemberIds)) {
			return;
		}
		sql = "UPDATE %s SET STATE_ = ? WHERE STATE_ = ? AND HANDICAP_MEMBER_ID_ IN (" ;
		parameters.clear();
		parameters.add(IbmStateEnum.CLOSE.name());
		parameters.add(IbmStateEnum.OPEN.name());
		StringBuilder sqlBuilder = new StringBuilder();
		for (String handicapMemberId : handicapMemberIds) {
			sqlBuilder.append("?,");
			parameters.add(handicapMemberId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(")");
		sql += sqlBuilder.toString();

		//盘口会员盈亏
		super.dao.execute(String.format(sql, "ibm_profit"), parameters);
		//盘口会员模拟盈亏
		super.dao.execute(String.format(sql, "ibm_profit_vr"), parameters);
		//盘口会员方案盈亏
		super.dao.execute(String.format(sql, "ibm_profit_plan"), parameters);
		//盘口会员方案模拟盈亏
		super.dao.execute(String.format(sql, "ibm_profit_plan_vr"), parameters);
		//盘口会员盈亏信息
		super.dao.execute(String.format(sql, "ibm_profit_info"), parameters);
		//盘口会员模拟盈亏信息
		super.dao.execute(String.format(sql, "ibm_profit_info_vr"), parameters);
	}

	/**
	 * 盈利限制
	 *
	 * @param type 限制类型
	 */
	public List<String> profitLimit(IbmTypeEnum type,String className) throws SQLException {
		String tableName = "ibm_profit" ;
		String profit="HANDICAP_PROFIT_T_";
		if (IbmTypeEnum.VIRTUAL.equals(type)) {
			tableName = "ibm_profit_vr" ;
			profit="PROFIT_T_";
		}

		//查出达到限制的盘口会员
		String sql = "SELECT ihp.HANDICAP_MEMBER_ID_  FROM " + tableName + " ihp "
				+ " LEFT JOIN ibm_handicap_member ihm ON ihp.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_ "
				+ " LEFT JOIN ibm_hm_game_set ihgs ON ihp.HANDICAP_MEMBER_ID_=ihgs.HANDICAP_MEMBER_ID_"
				+ " WHERE ihm.LOGIN_STATE_ = ? AND ihp.STATE_ = ? and ihgs.BET_STATE_=? and ihgs.BET_MODE_=? AND ( (PROFIT_LIMIT_MAX_T_ != 0 AND "+profit+" > "
				+ " PROFIT_LIMIT_MAX_T_) OR IF (PROFIT_LIMIT_MIN_T_ IS NULL,(LOSS_LIMIT_MIN_T_ != 0 AND "+profit+" < "
				+ " LOSS_LIMIT_MIN_T_),( PROFIT_LIMIT_MIN_T_ != 0 AND "+profit+" < PROFIT_LIMIT_MIN_T_))) "
				+ " ORDER BY ihp.HANDICAP_ID_" ;
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmTypeEnum.TRUE.name());
		parameterList.add(type.name());
		List<String> handicapMemberIds = super.dao.findStringList("HANDICAP_MEMBER_ID_", sql, parameterList);
		if (ContainerTool.isEmpty(handicapMemberIds)) {
			return null;
		}
		String handicapMemberIdStr = String.join("','", handicapMemberIds);
		//停止盘口会员的投注状态
		Date nowTime = new Date();
		sql = "UPDATE `ibm_plan_hm` SET DESC_=?,STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE STATE_ = ? "
				+ " AND HANDICAP_MEMBER_ID_ IN ('" + handicapMemberIdStr + "') " ;
		parameterList.clear();
		parameterList.add(className+"停止盘口会员的投注状态");
		parameterList.add(IbmTypeEnum.CLOSE.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmTypeEnum.OPEN.name());
		super.dao.execute(sql, parameterList);

		sql="update ibm_hm_game_set set DESC_=?, BET_STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where STATE_=? and BET_MODE_=?  "
				+ "and HANDICAP_MEMBER_ID_ in ('"+handicapMemberIdStr+"')";
		parameterList.clear();
		parameterList.add(className+"停止盘口会员的投注状态");
		parameterList.add(IbmTypeEnum.FALSE.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmTypeEnum.OPEN.name());
		parameterList.add(type.name());
		super.dao.execute(sql, parameterList);

		//更新盈利表状态
		sql = "UPDATE " + tableName + " SET DESC_=?,STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE STATE_ = ? AND "
				+ " HANDICAP_MEMBER_ID_ IN ('" + handicapMemberIdStr + "') " ;
		parameterList.clear();
		parameterList.add(className+"更新盈利表状态");
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);

		return handicapMemberIds;
	}

	/**
	 * @param handicapMemberIdList 盘口会员ID集合
	 * @return 真实投注盈亏信息
	 * @Description: 根据盘口会员ID集合查询真实投注盈亏信息
	 * <p>
	 * 参数说明
	 */
	public Map<String, Map<String, Object>> listProfit(List<String> handicapMemberIdList) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT HANDICAP_MEMBER_ID_,PROFIT_T_,BET_FUNDS_T_,BET_LEN_,HANDICAP_PROFIT_T_ from ibm_profit where STATE_ = ? AND HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String handicapMemberId : handicapMemberIdList) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameterList);
		Map<String, Map<String, Object>> data = new HashMap<>(list.size());
		for (Map<String, Object> map : list) {
			map.put("PROFIT_T_", NumberTool.doubleT(map.get("PROFIT_T_")));
			map.put("BET_FUNDS_T_", NumberTool.doubleT(map.get("BET_FUNDS_T_")));
			map.put("HANDICAP_PROFIT_",NumberTool.doubleT(map.get("HANDICAP_PROFIT_T_")));
			map.remove("HANDICAP_PROFIT_T_");
			map.remove("ROW_NUM");
			data.put(map.get("HANDICAP_MEMBER_ID_").toString(), map);
		}
		return data;
	}

	/**
	 * 查找盈利id
	 *
	 * @param handicapMemberId 盘口会员ID
	 * @return 盈利id
	 */
	public String findIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "SELECT IBM_PROFIT_ID_ FROM `ibm_profit` where HANDICAP_MEMBER_ID_ = ? AND STATE_ = ?" ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_PROFIT_ID_", sql, parameterList);
	}

	/**
	 * 更新盈利信息
	 *
	 * @param profitId  盈利id
	 * @param profitT   投注盈亏
	 * @param betFundsT 投注积分
	 * @param betLen    投注项长度
	 * @param nowTime   更新时间
	 */
	public void updateProfit(String profitId, long profitT, long betFundsT, int betLen, Date nowTime,String className)
			throws SQLException {
		String sql = "UPDATE ibm_profit SET DESC_=?,PROFIT_T_ = PROFIT_T_ + ?, BET_FUNDS_T_ = BET_FUNDS_T_ + ?, "
				+ " BET_LEN_ = BET_LEN_ + ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? WHERE IBM_PROFIT_ID_ = ?" ;
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(className+"更新盈利信息");
		parameterList.add(profitT);
		parameterList.add(betFundsT);
		parameterList.add(betLen);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(profitId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 根据盘口会员id重置真实投注盈亏信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param nowTime          当前时间
	 */
	public void resetProfit(String handicapMemberId, Date nowTime,String kindName) throws SQLException {
		String sql = "UPDATE ibm_profit SET DESC_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? WHERE HANDICAP_MEMBER_ID_ = ? AND STATE_ = ? " ;
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(kindName+"根据盘口会员id重置真实投注盈亏信息");
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 清理冗余数据
	 *
	 * @param nowTime  清理时间
	 * @param type     清理类型
	 * @param ruleTime 时间规则
	 */
	public void clearRedundancy(Date nowTime, IbmTypeEnum type, String ruleTime) throws Exception {
		String tableName = "ibm_profit" ;
		if (IbmTypeEnum.VIRTUAL.equals(type)) {
			tableName = "ibm_profit_vr" ;
		}
		String sql = "DELETE FROM ".concat(tableName).concat(" WHERE CREATE_TIME_LONG_< ? ");
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(DateTool.getAfterRule(nowTime, ruleTime).getTime());
		super.dao.execute(sql, parameterList);

	}
	/**
	 * 更新盘口盈利信息
	 * @param profitId		盘口会员总盈亏id
	 * @param handicapProfit	盘口盈利信息
	 */
	public void updateHandicapProfit(String profitId, Long handicapProfit,String className) throws SQLException {
		String sql="update ibm_profit set DESC_=?, HANDICAP_PROFIT_T_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_PROFIT_ID_=? ";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(className+"更新盘口盈利信息");
		parameterList.add(handicapProfit);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(profitId);
		super.dao.execute(sql,parameterList);
	}
	/**
	 * 根据盈亏主键清除信息
	 * @param profitId	盘口会员盈亏主键
	 * @throws SQLException
	 */
	public void clearById(String profitId,String className) throws SQLException {
		String sql="update ibm_profit set DESC_=?,STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_PROFIT_ID_=?";
		List<Object> parameterList=new ArrayList<>(4);
		parameterList.add(className+"根据盈亏主键清除信息");
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(profitId);
		super.dao.execute(sql,parameterList);
	}
	/**
	 * 添加盘口盈亏信息
	 * @param hmSetT                    盘口会员设置
	 * @param handicapMemberId        盘口会员id
	 * @param handicapProfit            盘口盈亏信息
	 * @throws Exception
	 */
	public String save(IbmHmSetT hmSetT, String handicapMemberId, long handicapProfit) throws Exception {
		Date nowTime = new Date();
		IbmProfitT profitTEntity = new IbmProfitT();
		profitTEntity.setHandicapId(hmSetT.getHandicapId());
		profitTEntity.setAppUserId(hmSetT.getAppUserId());
		profitTEntity.setHandicapMemberId(handicapMemberId);
		profitTEntity.setHandicapProfitT(handicapProfit);
		profitTEntity.setProfitT(0);
		profitTEntity.setBetFundsT(0);
		profitTEntity.setBetLen(0);
		profitTEntity.setProfitLimitMaxT(hmSetT.getProfitLimitMaxT());
		profitTEntity.setProfitLimitMinT(hmSetT.getProfitLimitMinT());
		profitTEntity.setLossLimitMinT(hmSetT.getLossLimitMinT());
		profitTEntity.setCreateTime(nowTime);
		profitTEntity.setCreateTimeLong(nowTime.getTime());
		profitTEntity.setState(IbmStateEnum.OPEN.name());
		return save(profitTEntity);
	}
	/**
	 * 设置止盈止损
	 * @param profitId	盘口会员总盈亏
	 * @param hmSetT		盘口会员设置
	 */
	public void update(String profitId, IbmHmSetT hmSetT,String className) throws SQLException {
		String sql="update ibm_profit set DESC_=?,PROFIT_LIMIT_MAX_T_=?,PROFIT_LIMIT_MIN_T_=?,LOSS_LIMIT_MIN_T_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?"
				+ " where IBM_PROFIT_ID_=?";
		List<Object> parameterList=new ArrayList<>(6);
		parameterList.add(className+"设置止盈止损");
		parameterList.add(hmSetT.getProfitLimitMaxT());
		parameterList.add(hmSetT.getProfitLimitMinT());
		parameterList.add(hmSetT.getLossLimitMinT());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(profitId);
		super.dao.execute(sql,parameterList);
	}
}
