package com.ibm.old.v1.cloud.ibm_profit_vr.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_pi_follow_open_bet.t.service.IbmPiFollowOpenBetTService;
import com.ibm.old.v1.cloud.ibm_pi_follow_two_side.t.service.IbmPiFollowTwoSideTService;
import com.ibm.old.v1.cloud.ibm_pi_location_bet_number.t.service.IbmPiLocationBetNumberTService;
import com.ibm.old.v1.cloud.ibm_pi_number_to_track.t.service.IbmPiNumberToTrackTService;
import com.ibm.old.v1.cloud.ibm_pi_rank_hot_and_cold.t.service.IbmPiRankHotAndColdTService;
import com.ibm.old.v1.cloud.ibm_profit_vr.t.entity.IbmProfitVrT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmProfitVrTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmProfitVrT对象数据
	 */
	public String save(IbmProfitVrT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_profit_vr的 IBM_PROFIT_VR_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_profit_vr set state_='DEL' where IBM_PROFIT_VR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_PROFIT_VR_ID_主键id数组的数据
	 * @param idArray 要删除ibm_profit_vr的 IBM_PROFIT_VR_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_profit_vr set state_='DEL' where IBM_PROFIT_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_profit_vr的 IBM_PROFIT_VR_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_profit_vr where IBM_PROFIT_VR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_PROFIT_VR_ID_主键id数组的数据
	 * @param idArray 要删除ibm_profit_vr的 IBM_PROFIT_VR_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_profit_vr where IBM_PROFIT_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmProfitVrT实体信息
	 * @param entity IbmProfitVrT实体
	 */
	public void update(IbmProfitVrT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_profit_vr表主键查找IbmProfitVrT实体
	 * @param id ibm_profit_vr 主键
	 * @return IbmProfitVrT实体
	 */
	public IbmProfitVrT find(String id) throws Exception {
		return (IbmProfitVrT) this.dao.find(IbmProfitVrT. class,id);

	}

	/**
	 * 获取分页Map数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_profit_vr where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_profit_vr  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmProfitVrT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmProfitVrT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_profit_vr where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_profit_vr  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmProfitVrT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmProfitVrT数据信息
	 * @return 可用<IbmProfitVrT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmProfitVrT. class,sql);
	}
	

	/**
	 * 
	 * @Description: 根据盘口会员ID集合查询模拟投注盈亏信息
	 *
	 * 参数说明 
	 * @param handicapMemberIdList 盘口会员ID集合
	 * @return 模拟投注盈亏信息 
	 */
	public Map<String, Map<String, Object>> listProfitVr(List<String> handicapMemberIdList) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT HANDICAP_MEMBER_ID_,PROFIT_T_,BET_FUNDS_T_,BET_LEN_ from ibm_profit_vr where STATE_ = ? AND HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String handicapMemberId : handicapMemberIdList) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length()-1, sql.length(), ")");
		Map<String,Map<String, Object>> data = new HashMap<>();
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameterList);
		for (Map<String, Object> map : list) {
			map.put("PROFIT_T_",NumberTool.doubleT(map.get("PROFIT_T_")));
			map.put("BET_FUNDS_T_",NumberTool.doubleT(map.get("BET_FUNDS_T_")));
			data.put(map.get("HANDICAP_MEMBER_ID_").toString(), map);
			map.remove("ROW_NUM");
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
		String sql = "SELECT IBM_PROFIT_VR_ID_ FROM `ibm_profit_vr` where HANDICAP_MEMBER_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_PROFIT_VR_ID_", sql, parameterList);
	}

	/**
	 * 更新盈利信息
	 *
	 * @param profitVrId  盈利id
	 * @param profitT   投注盈亏
	 * @param betFundsT 投注积分
	 * @param betLen    投注项长度
	 * @param nowTime   更新时间
	 */
	public void updateProfit(String profitVrId, long profitT, long betFundsT, int betLen, Date nowTime,String className)
			throws SQLException {
		String sql = "UPDATE ibm_profit_vr SET DESC_=?, PROFIT_T_ = PROFIT_T_ + ?, BET_FUNDS_T_ = BET_FUNDS_T_ + ?, "
				+ " BET_LEN_ = BET_LEN_ + ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? WHERE IBM_PROFIT_VR_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(className+"更新盈利信息");
		parameterList.add(profitT);
		parameterList.add(betFundsT);
		parameterList.add(betLen);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(profitVrId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * @param handicapMemberId 盘口会员ID
	 * @return 模拟投注盈亏信息
	 * @Description: 根据盘口会员ID查询模拟投注盈亏信息
	 * <p>
	 * 参数说明
	 */
	public Map<String, Object> findProfitVr(String handicapMemberId) throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_,PROFIT_T_,BET_FUNDS_T_,BET_LEN_ from ibm_profit_vr where STATE_ = ? AND HANDICAP_MEMBER_ID_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(handicapMemberId);
		Map<String, Object> map = super.dao.findMap(sql, parameterList);
		if(ContainerTool.notEmpty(map)){
			map.put("PROFIT_T_",NumberTool.doubleT(map.get("PROFIT_T_")));
			map.put("BET_FUNDS_T_",NumberTool.doubleT(map.get("BET_FUNDS_T_")));
			map.remove("ROW_NUM");
		}
		return map;
	}

	/**
	 * 根据盘口会员id重置模拟投注盈亏信息
	 * @param handicapMemberId 盘口会员id
	 *
	 * @param nowTime 当前时间
	 */
	public void resetProfitVr(String handicapMemberId, Date nowTime,String className) throws SQLException {
		String sql = "UPDATE ibm_profit_vr SET DESC_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? WHERE HANDICAP_MEMBER_ID_ = ? AND STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(className+"根据盘口会员id重置模拟投注盈亏信息");
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql,parameterList);
	}

	/**
	 * 修改方案盈亏限额
	 * @param profitLimitMaxT 盈利上限
	 * @param lossLimitMinT 亏损下限
	 * @param appUserId 用户id
	 * @param itemId 方案详情表id
	 * @param name 方案详情表名
	 * @return 是否修改成功
	 */
	public boolean updateLimit(long profitLimitMaxT, long lossLimitMinT, String appUserId, String itemId, String name,String className) throws SQLException {
		String planId;
		switch (name) {
			case "IBM_PI_LOCATION_BET_NUMBER":
				IbmPiLocationBetNumberTService piLocationBetNumberTService = new IbmPiLocationBetNumberTService();
				planId = piLocationBetNumberTService.findPlanId(itemId);
				break;
			case "IBM_PI_FOLLOW_TWO_SIDE":
				IbmPiFollowTwoSideTService piFollowTwoSideTService = new IbmPiFollowTwoSideTService();
				planId = piFollowTwoSideTService.findPlanId(itemId);
				break;
			case "IBM_PI_FOLLOW_OPEN_BET":
				IbmPiFollowOpenBetTService piFollowOpenBetTService=new IbmPiFollowOpenBetTService();
				planId = piFollowOpenBetTService.findPlanId(itemId);
				break;
			case "IBM_PI_NUMBER_TO_TRACK":
				IbmPiNumberToTrackTService piNumberToTrackTService=new IbmPiNumberToTrackTService();
				planId = piNumberToTrackTService.findPlanId(itemId);
				break;
			case "IBM_PI_RANK_HOT_AND_COLD":
				IbmPiRankHotAndColdTService piRankHotAndColdTService=new IbmPiRankHotAndColdTService();
				planId = piRankHotAndColdTService.findPlanId(itemId);
				break;
			default:
				throw new RuntimeException("不存在的盘口详情表名称" + name);
		}
		if(StringTool.isEmpty(planId)){
			return false;
		}
		String sql = "UPDATE ibm_profit_plan_vr SET DESC_=?,PROFIT_LIMIT_MAX_T_ = ?,LOSS_LIMIT_MIN_T_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? " +
				"WHERE HANDICAP_MEMBER_ID_ in (SELECT IBM_HANDICAP_MEMBER_ID_ FROM ibm_handicap_member WHERE APP_USER_ID_ = ? AND STATE_ != ? )" +
				" AND PLAN_ID_ = ? and STATE_=? ";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(className+"修改方案盈亏限额");
		parameterList.add(profitLimitMaxT);
		parameterList.add(lossLimitMinT);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(planId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql,parameterList);
		return true;
	}
	/**
	 * 添加模拟投注信息
	 * @param hmSetT		盘口会员设置
	 * @param handicapMemberId	盘口会员id
	 * @return
	 * @throws Exception
	 */
	public String save(IbmHmSetT hmSetT, String handicapMemberId) throws Exception {
		Date nowTime = new Date();
		IbmProfitVrT profitVrT = new IbmProfitVrT();
		profitVrT.setHandicapId(hmSetT.getHandicapId());
		profitVrT.setAppUserId(hmSetT.getAppUserId());
		profitVrT.setHandicapMemberId(handicapMemberId);
		profitVrT.setProfitT(0);
		profitVrT.setBetFundsT(0);
		profitVrT.setBetLen(0);
		profitVrT.setProfitLimitMaxT(hmSetT.getProfitLimitMaxT());
		profitVrT.setProfitLimitMinT(hmSetT.getProfitLimitMinT());
		profitVrT.setLossLimitMinT(hmSetT.getLossLimitMinT());
		profitVrT.setCreateTime(nowTime);
		profitVrT.setCreateTimeLong(nowTime.getTime());
		profitVrT.setState(IbmStateEnum.OPEN.name());
		return save(profitVrT);
	}
	/**
	 * 清除模拟投注盈亏信息
	 * @param profitVrId		盘口会员总模拟盈亏主键
	 * @throws SQLException
	 */
	public void clearById(String profitVrId,String className) throws SQLException {
		String sql="update ibm_profit_vr set DESC_=?,STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_PROFIT_VR_ID_=?";
		List<Object> parameterList=new ArrayList<>(4);
		parameterList.add(className+"清除模拟投注盈亏信息");
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(profitVrId);
		super.dao.execute(sql,parameterList);
	}
	/**
	 * 盘口会员总模拟盈亏
	 * @param profitVrId		总模拟盈亏主键
	 * @param hmSetT			盘口会员设置
	 */
	public void update(String profitVrId, IbmHmSetT hmSetT,String className) throws SQLException {
		String sql="update ibm_profit_vr set DESC_=?,PROFIT_LIMIT_MAX_T_=?,PROFIT_LIMIT_MIN_T_=?,LOSS_LIMIT_MIN_T_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?"
				+ " where IBM_PROFIT_VR_ID_=?";
		List<Object> parameterList=new ArrayList<>(6);
		parameterList.add(className+"盘口会员总模拟盈亏");
		parameterList.add(hmSetT.getProfitLimitMaxT());
		parameterList.add(hmSetT.getProfitLimitMinT());
		parameterList.add(hmSetT.getLossLimitMinT());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(profitVrId);
		super.dao.execute(sql,parameterList);
	}
}
