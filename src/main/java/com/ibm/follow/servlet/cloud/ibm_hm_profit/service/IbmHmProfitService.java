package com.ibm.follow.servlet.cloud.ibm_hm_profit.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.entity.IbmHmProfit;
import com.ibm.follow.servlet.cloud.ibm_hm_set.entity.IbmHmSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHmProfitService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHmProfit对象数据
	 */
	public String save(IbmHmProfit entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_hm_profit的 IBM_HM_PROFIT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_hm_profit set state_='DEL' where IBM_HM_PROFIT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HM_PROFIT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_profit的 IBM_HM_PROFIT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_hm_profit set state_='DEL' where IBM_HM_PROFIT_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_hm_profit的 IBM_HM_PROFIT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_hm_profit where IBM_HM_PROFIT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HM_PROFIT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_profit的 IBM_HM_PROFIT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_hm_profit where IBM_HM_PROFIT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHmProfit实体信息
	 *
	 * @param entity IbmHmProfit实体
	 */
	public void update(IbmHmProfit entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_hm_profit表主键查找IbmHmProfit实体
	 *
	 * @param id ibm_hm_profit 主键
	 * @return IbmHmProfit实体
	 */
	public IbmHmProfit find(String id) throws Exception {
		return (IbmHmProfit) this.dao.find(IbmHmProfit.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_hm_profit where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_profit  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_profit  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHmProfit数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHmProfit数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_profit where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_profit  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_profit  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHmProfit.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_profit  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHmProfit数据信息
	 *
	 * @return 可用<IbmHmProfit>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_profit  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHmProfit.class, sql);
	}

	/**
	 * 获取盘口会员的盈亏信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 盈亏信息
	 */
	public Map<String, Object> findInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT PROFIT_T_,BET_FUNDS_T_,BET_LEN_ FROM `ibm_hm_profit` "
				+ "where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 通过盘口会员id获取主键信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 盈亏主键
	 */
	public String findByHmId(String handicapMemberId) throws SQLException {
		String sql = "select IBM_HM_PROFIT_ID_ from ibm_hm_profit where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_HM_PROFIT_ID_", sql, parameterList);
	}
	/**
	 * 通过主键删除盈亏信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param nowTime          当前时间
	 */
	public void updateLogout(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "update ibm_hm_profit set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 修改止盈止损
	 *
	 * @param hmSet     盘口会员设置
	 * @param profitId  盈亏信息主键
	 * @param className 执行类名
	 */
	public void update(IbmHmSet hmSet, String profitId, String className) throws SQLException {
		String sql = "update ibm_hm_profit set PROFIT_LIMIT_MAX_=?,PROFIT_LIMIT_MIN_=?,LOSS_LIMIT_MIN_=?, "
				+ " UPDATE_TIME_LONG_=?" + ",DESC_=? where IBM_HM_PROFIT_ID_=?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(hmSet.getProfitLimitMax());
		parameterList.add(hmSet.getProfitLimitMin());
		parameterList.add(hmSet.getLossLimitMin());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(className.concat("修改止盈止损"));
		parameterList.add(profitId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 盈利限制
	 *
	 * @param type    盈利类型
	 * @param nowTime 限制时间
	 * @return 停止信息
	 */
	public Map<String, Object> profitLimit(IbmTypeEnum type, Date nowTime) throws SQLException {
		String tableName = "ibm_hm_profit";
		String profit="HANDICAP_PROFIT_T_";
		if (IbmTypeEnum.VIRTUAL.equals(type)) {
			tableName = "ibm_hm_profit_vr";
			profit="PROFIT_T_";
		}
		String sql = "SELECT ihgs.HANDICAP_MEMBER_ID_ as key_,ihgs.GAME_ID_ as value_ FROM ibm_hm_game_set ihgs "
				+ " LEFT JOIN ibm_hm_info ihi ON ihgs.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ AND ihi.STATE_ = ? "
				+ " WHERE BET_STATE_ = ? AND BET_MODE_ = ? AND ihgs.STATE_ = ? AND ihgs.HANDICAP_MEMBER_ID_ IN ("
				+ " SELECT HANDICAP_MEMBER_ID_ FROM " + tableName + " WHERE STATE_ = ? AND "
				+ " (PROFIT_LIMIT_MAX_ != 0 AND "+profit+" > PROFIT_LIMIT_MAX_*1000) OR IF "
				+ "(PROFIT_LIMIT_MIN_ != 0,"+profit+" < PROFIT_LIMIT_MIN_*1000,LOSS_LIMIT_MIN_ != 0 AND "+profit+" < LOSS_LIMIT_MIN_*1000)) ";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmTypeEnum.TRUE.name());
		parameterList.add(type.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());

		//查出达到限制的盘口会员
		Map<String, Object> stopInfo = super.findKVMap(sql, parameterList);

		if (ContainerTool.isEmpty(stopInfo)) {
			return null;
		}
		String handicapMemberIdsStr = String.join("','", stopInfo.keySet());

		//停止盘口会员的投注状态
		parameterList.clear();
		sql = "update ibm_hm_game_set set BET_STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
				+ " BET_STATE_ = ? and BET_MODE_ = ? and STATE_ = ? and HANDICAP_MEMBER_ID_ in ('"
				+ handicapMemberIdsStr + "')";

		parameterList.add(IbmTypeEnum.FALSE.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("自动管理达到限制的盘口会员,停止投注状态");
		parameterList.add(IbmTypeEnum.TRUE.name());
		parameterList.add(type.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);

		//更新盈利表状态
		parameterList.clear();
		sql = "UPDATE " + tableName + " SET STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE "
				+ " STATE_ = ? AND HANDICAP_MEMBER_ID_ IN ('" + handicapMemberIdsStr + "') ";
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("自动管理达到限制的盘口会员,关闭盈利表状态");
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);

		return stopInfo;
	}

	/**
	 * 获取盘口会员信息
	 * @param handicapMemberIds
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>> listHmInfo(String handicapMemberIds) throws SQLException {
		String sql = "SELECT ihp.HANDICAP_ID_,ihp.HANDICAP_PROFIT_T_,ihi.APP_USER_ID_ " +
				" FROM `ibm_hm_profit` ihp LEFT JOIN ibm_hm_info ihi on ihp.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ " +
				" WHERE ihp.HANDICAP_MEMBER_ID_ IN ('"+handicapMemberIds+"')";
		return dao.findMapList(sql,null);
	}

	/**
	 * 查找盈利id
	 *
	 * @param handicapMemberId 盘口会员ID
	 * @return 盈利id
	 */
	public String findIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "SELECT IBM_HM_PROFIT_ID_ as key_ FROM `ibm_hm_profit` where HANDICAP_MEMBER_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 添加盘口盈亏信息
	 *
	 * @param limitInfo        限制信息
	 * @param handicapMemberId 盘口会员id
	 * @param profitTh         盈亏金额
	 * @param fundTh           投注金额
	 * @param betTotal         投注数
	 * @param nowTime          创建时间
	 */
	public void save(Map<String, Object> limitInfo, String handicapMemberId, long profitTh,
			long fundTh, int betTotal, Date nowTime) throws Exception {
		IbmHmProfit profit = new IbmHmProfit();
		profit.setHandicapMemberId(handicapMemberId);
		profit.setProfitT(profitTh);
		profit.setHandicapProfitT(0);
		profit.setBetFundsT(fundTh);
		profit.setBetLen(betTotal);
		profit.setProfitLimitMax(limitInfo.get("PROFIT_LIMIT_MAX_"));
		profit.setProfitLimitMin(limitInfo.get("PROFIT_LIMIT_MIN_"));
		profit.setLossLimitMin(limitInfo.get("LOSS_LIMIT_MIN_"));
		profit.setCreateTime(nowTime);
		profit.setCreateTimeLong(System.currentTimeMillis());
		profit.setUpdateTimeLong(System.currentTimeMillis());
		profit.setState(IbmStateEnum.OPEN.name());
		save(profit);
	}

	/**
	 * 结算盈亏信息
	 *
	 * @param profitId 盘口会员总盈亏主键
	 * @param profit   盈亏金额
	 * @param fund     投注金额
	 * @param betTotal 投注数
	 * @param nowTime  结算时间
	 */
	public void update(String profitId, long profit, long fund, int betTotal, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_hm_profit set PROFIT_T_ = PROFIT_T_ + ?,BET_FUNDS_T_ = BET_FUNDS_T_ + ?, "
				+ " BET_LEN_ = BET_LEN_ + ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBM_HM_PROFIT_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(profit);
		parameterList.add(fund);
		parameterList.add(betTotal);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("结算盈亏信息");
		parameterList.add(profitId);
		super.execute(sql, parameterList);
	}
	/**
	 * 修改盘口盈亏信息
	 * @param handicapMemberId	盘口会员id
	 * @param profitAmount		盘口盈亏
	 */
	public void updateHandicapProfitInfo(String handicapMemberId, long profitAmount) throws SQLException {
		String sql="update ibm_hm_profit set HANDICAP_PROFIT_T_=? where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(profitAmount);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql,parameterList);
	}

    /**
     * 获取盘口会员盈亏设置信息
     * @param handicapMemberId      盘口会员id
     * @return
     */
    public Map<String, Object> findProfitLimit(String handicapMemberId) throws SQLException {
        String sql="select PROFIT_LIMIT_MAX_,PROFIT_LIMIT_MIN_,LOSS_LIMIT_MIN_ from ibm_hm_profit where HANDICAP_MEMBER_ID_=?"
                +" and STATE_=?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapMemberId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql,parameterList);
    }

    /**
     * 获取投注信息
     * @param handicapMemberId      盘口会员id
     * @param betMode               投注模式
     * @param number                获取条数
     * @return
     */
    public List<Map<String, Object>> findBetInfo(String handicapMemberId,String gameId, Object betMode, int number) throws SQLException {
        String profitId="IBM_HM_PROFIT_PERIOD_ID_";
        String tableName="ibm_hm_profit_period";
        if(IbmTypeEnum.VIRTUAL.name().equals(betMode)){
			profitId="IBM_HM_PROFIT_PERIOD_VR_ID_";
            tableName="ibm_hm_profit_period_vr";
        }
        String sql="select %s as PROFIT_PERIOD_ID_,PERIOD_,BET_LEN_,PROFIT_BET_LEN_,LOSS_BET_LEN_,BET_FUNDS_T_"
                +",PROFIT_T_ from %s WHERE HANDICAP_MEMBER_ID_=? and GAME_ID_=? and STATE_=? ORDER BY CREATE_TIME_LONG_ DESC limit ?";

        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(handicapMemberId);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(number);
        return super.dao.findMapList(String.format(sql,profitId,tableName),parameterList);
    }

	/**
	 * 结算
	 * @param hmProfitInfos		会员盈亏信息
	 * @param nowTime				当前时间
	 * @param betMode				投注模式
	 */
	public void updateSettlement(Map<String, Map<String, Object>> hmProfitInfos, Date nowTime, IbmTypeEnum betMode) throws SQLException {
		String tableName = " ibm_hm_profit ";
		if (IbmTypeEnum.VIRTUAL.equals(betMode)) {
			tableName = " ibm_hm_profit_vr ";
		}
		StringBuilder sql=new StringBuilder();
		sql.append("update ").append(tableName).append(" set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,PROFIT_T_=PROFIT_T_+ CASE HANDICAP_MEMBER_ID_ ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		StringBuilder sqlPlus = new StringBuilder();
		for(Map.Entry<String,Map<String,Object>> entry:hmProfitInfos.entrySet()){
			sql.append(" WHEN ? THEN ? ");
			parameters.add(entry.getKey());
			parameters.add(entry.getValue().get("profitTh"));
			sqlPlus.append("?,");
		}
		sql.append(" end,BET_FUNDS_T_ = BET_FUNDS_T_ + CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String,Map<String,Object>> entry:hmProfitInfos.entrySet()){
			sql.append(" WHEN ? THEN ? ");
			parameters.add(entry.getKey());
			parameters.add(entry.getValue().get("betFundsTh"));
		}
		sql.append(" end,BET_LEN_ = BET_LEN_ + CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String,Map<String,Object>> entry:hmProfitInfos.entrySet()){
			sql.append(" WHEN ? THEN ? ");
			parameters.add(entry.getKey());
			parameters.add(entry.getValue().get("betLen"));
		}
		sql.append(" end where STATE_ = ? and HANDICAP_MEMBER_ID_ in (");
		parameters.add(IbmStateEnum.OPEN.name());
		sql.append(sqlPlus);
		sql.replace(sql.length() - 1, sql.length(), ")");
		parameters.addAll(hmProfitInfos.keySet());
		super.dao.execute(sql.toString(), parameters);
	}
}
