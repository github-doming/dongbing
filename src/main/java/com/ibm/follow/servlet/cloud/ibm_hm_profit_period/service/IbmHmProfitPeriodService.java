package com.ibm.follow.servlet.cloud.ibm_hm_profit_period.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period.entity.IbmHmProfitPeriod;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHmProfitPeriodService extends BaseServicePlus {

    /**
     * 保存{ay_table_class}对象数据
     *
     * @param entity IbmHmProfitPeriod对象数据
     */
    public String save(IbmHmProfitPeriod entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_hm_profit_period的 IBM_HM_PROFIT_PERIOD_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_hm_profit_period set state_='DEL' where IBM_HM_PROFIT_PERIOD_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBM_HM_PROFIT_PERIOD_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_hm_profit_period的 IBM_HM_PROFIT_PERIOD_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql =
                    "update ibm_hm_profit_period set state_='DEL' where IBM_HM_PROFIT_PERIOD_ID_ in(" + stringBuilder
                            .toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除ibm_hm_profit_period的 IBM_HM_PROFIT_PERIOD_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_hm_profit_period where IBM_HM_PROFIT_PERIOD_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBM_HM_PROFIT_PERIOD_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_hm_profit_period的 IBM_HM_PROFIT_PERIOD_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql =
                    "delete from ibm_hm_profit_period where IBM_HM_PROFIT_PERIOD_ID_ in(" + stringBuilder.toString()
                            + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmHmProfitPeriod实体信息
     *
     * @param entity IbmHmProfitPeriod实体
     */
    public void update(IbmHmProfitPeriod entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_hm_profit_period表主键查找IbmHmProfitPeriod实体
     *
     * @param id ibm_hm_profit_period 主键
     * @return IbmHmProfitPeriod实体
     */
    public IbmHmProfitPeriod find(String id) throws Exception {
        return (IbmHmProfitPeriod) this.dao.find(IbmHmProfitPeriod.class, id);

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
        String sqlCount = "SELECT count(*) FROM ibm_hm_profit_period where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_hm_profit_period  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_hm_profit_period  where state_!='DEL' order by " + sortFieldName + " "
                    + sortOrderName;
        }
        return dao.page(sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 获取分页IbmHmProfitPeriod数据
     *
     * @param sortFieldName 排序字段名
     * @param sortOrderName 排序顺序
     * @param pageIndex     页面索引
     * @param pageSize      页面大小
     * @return 分页IbmHmProfitPeriod数据
     */
    public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
            throws Exception {
        String sqlCount = "SELECT count(*) FROM ibm_hm_profit_period where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_hm_profit_period  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_hm_profit_period  where state_!='DEL' order by " + sortFieldName + " "
                    + sortOrderName;
        }
        return dao.page(IbmHmProfitPeriod.class, sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 按照更新顺序查询所有可用Map信息
     *
     * @return 可用Map信息
     */
    public List<Map<String, Object>> findAll() throws Exception {
        String sql = "SELECT * FROM ibm_hm_profit_period  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findMapList(sql, null);
    }

    /**
     * 按照更新顺序查询所有可用IbmHmProfitPeriod数据信息
     *
     * @return 可用<IbmHmProfitPeriod>数据信息
     */
    public List findObjectAll() throws Exception {
        String sql = "SELECT * FROM ibm_hm_profit_period  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findObjectList(IbmHmProfitPeriod.class, sql);
    }

    /**
     * 保存盘口会员当期盈亏
     *
     * @param handicapMemberId 盘口会员id
     * @param period           期数
     * @param profit           盈亏金额
     * @param funds            投注金额
     * @param betTotal         投注数
     * @param nowTime          创建时间
     */
    public void save(String handicapMemberId, Object period, long profit, long funds, int betTotal,
                     Date nowTime, int profitBetLen, int lossBetLen) throws Exception {
        IbmHmProfitPeriod profitPeriod = new IbmHmProfitPeriod();
        profitPeriod.setHandicapMemberId(handicapMemberId);
        profitPeriod.setPeriod(period);
        profitPeriod.setProfitT(profit);
        profitPeriod.setBetFundsT(funds);
        profitPeriod.setBetLen(betTotal);
        profitPeriod.setProfitBetLen(profitBetLen);
        profitPeriod.setLossBetLen(lossBetLen);
        profitPeriod.setCreateTime(nowTime);
        profitPeriod.setCreateTimeLong(System.currentTimeMillis());
        profitPeriod.setUpdateTimeLong(System.currentTimeMillis());
        profitPeriod.setState(IbmStateEnum.OPEN.name());
        save(profitPeriod);
    }

    /**
     * 通过盘口会员id和期数获取实体对象
     *
     * @param handicapMemberId 盘口会员
     * @param period           期数
     * @return
     */
    public IbmHmProfitPeriod findByHmIdAndPeriod(String handicapMemberId, String period) throws Exception {
        String sql = "select * from ibm_hm_profit_period where HANDICAP_MEMBER_ID_=? and PERIOD_=? and STATE_=?";
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(handicapMemberId);
        parameters.add(period);
        parameters.add(IbmStateEnum.OPEN.name());
        return (IbmHmProfitPeriod) super.dao.findObject(IbmHmProfitPeriod.class, sql, parameters);
    }

    /**
     * 获取会员信息
     *
     * @param appUserId 用户id
     * @return
     */
    public List<Map<String, Object>> findMemberInfo(String appUserId) throws SQLException {
        String sql = "select ihpp.HANDICAP_MEMBER_ID_,ihm.HANDICAP_CODE_,ihm.MEMBER_ACCOUNT_ FROM ibm_hm_profit_period ihpp"
                + " LEFT JOIN ibm_handicap_member ihm ON ihpp.HANDICAP_MEMBER_ID_=ihm.IBM_HANDICAP_MEMBER_ID_"
                + " WHERE ihm.APP_USER_ID_=? AND ihpp.STATE_=? AND ihm.STATE_=? GROUP BY ihpp.HANDICAP_MEMBER_ID_";
        List<Object> parameters = new ArrayList<>();
        parameters.add(appUserId);
        parameters.add(IbmStateEnum.OPEN.name());
        parameters.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql, parameters);
    }

    /**
     * 获取分页信息
     *
     * @param handicapMemberId 盘口会员id
     * @param gameCode         游戏code
     * @return
     */
    public PageCoreBean findProfitInfo(String handicapMemberId, String gameCode, int pageIndex, int pageSize) throws SQLException {
        String sqlCount = "select count(ihpp.IBM_HM_PROFIT_PERIOD_ID_)"
                + " from ibm_hm_profit_period ihpp LEFT JOIN ibm_game ig ON ihpp.GAME_ID_=ig.IBM_GAME_ID_ "
                + " where ihpp.HANDICAP_MEMBER_ID_=? and ig.GAME_CODE_=? and ihpp.STATE_=? ORDER BY ihpp.CREATE_TIME_LONG_ DESC ";
        String sql = "select ihpp.PERIOD_,ihpp.PROFIT_T_,ihpp.BET_FUNDS_T_,ihpp.BET_LEN_,ihpp.PROFIT_BET_LEN_,ihpp.LOSS_BET_LEN_"
                + " from ibm_hm_profit_period ihpp LEFT JOIN ibm_game ig ON ihpp.GAME_ID_=ig.IBM_GAME_ID_ "
                + " where ihpp.HANDICAP_MEMBER_ID_=? and ig.GAME_CODE_=? and ihpp.STATE_=? ORDER BY ihpp.CREATE_TIME_LONG_ DESC ";
        List<Object> parameters = new ArrayList<>();
        parameters.add(handicapMemberId);
        parameters.add(gameCode);
        parameters.add(IbmStateEnum.OPEN.name());
        return dao.page(sql, parameters, pageIndex, pageSize, sqlCount);
    }

	/**
	 * 保存当期盈亏信息
	 * @param hmProfitInfos		会员盈亏信息
	 * @param gameId				游戏id
	 * @param period				期数
	 * @param nowTime				当前时间
	 * @param betMode				投注模式
	 */
	public void saveSettlement(Map<String, Map<String, Object>> hmProfitInfos, String gameId, Object period,
							   Date nowTime, IbmTypeEnum betMode) throws SQLException {
		StringBuilder sql=new StringBuilder();
		String tableName="ibm_hm_profit_period";
		String profitId="IBM_HM_PROFIT_PERIOD_ID_";
		if(IbmTypeEnum.VIRTUAL.equals(betMode)){
			tableName="ibm_hm_profit_period_vr";
			profitId="IBM_HM_PROFIT_PERIOD_VR_ID_";
		}
		sql.append("insert into ").append(tableName).append("(").append(profitId).append(",HANDICAP_MEMBER_ID_,GAME_ID_,PERIOD_,")
				.append("PROFIT_T_,BET_FUNDS_T_,BET_LEN_,PROFIT_BET_LEN_,LOSS_BET_LEN_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,")
				.append("UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters = new ArrayList<>();
		for(Map.Entry<String,Map<String,Object>> entry:hmProfitInfos.entrySet()){
			Map<String,Object> profitInfo=entry.getValue();
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(entry.getKey());
			parameters.add(gameId);
			parameters.add(period);
			parameters.add(profitInfo.get("profitTh"));
			parameters.add(profitInfo.get("betFundsTh"));
			parameters.add(profitInfo.get("betLen"));
			parameters.add(profitInfo.get("profitBetLen"));
			parameters.add(profitInfo.get("lossBetLen"));
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbmStateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameters);
	}
}
