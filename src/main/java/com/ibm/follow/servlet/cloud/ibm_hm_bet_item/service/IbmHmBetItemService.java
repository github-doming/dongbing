package com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.entity.IbmHmBetItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHmBetItemService extends BaseServicePlus {

    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 保存{ay_table_class}对象数据
     *
     * @param entity IbmHmBetItem对象数据
     */
    public String save(IbmHmBetItem entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_hm_bet_item的 IBM_HM_BET_ITEM_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_hm_bet_item set state_='DEL' where IBM_HM_BET_ITEM_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBM_HM_BET_ITEM_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_hm_bet_item的 IBM_HM_BET_ITEM_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql =
                    "update ibm_hm_bet_item set state_='DEL' where IBM_HM_BET_ITEM_ID_ in(" + stringBuilder.toString()
                            + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除ibm_hm_bet_item的 IBM_HM_BET_ITEM_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_hm_bet_item where IBM_HM_BET_ITEM_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBM_HM_BET_ITEM_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_hm_bet_item的 IBM_HM_BET_ITEM_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "delete from ibm_hm_bet_item where IBM_HM_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmHmBetItem实体信息
     *
     * @param entity IbmHmBetItem实体
     */
    public void update(IbmHmBetItem entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_hm_bet_item表主键查找IbmHmBetItem实体
     *
     * @param id ibm_hm_bet_item 主键
     * @return IbmHmBetItem实体
     */
    public IbmHmBetItem find(String id) throws Exception {
        return (IbmHmBetItem) this.dao.find(IbmHmBetItem.class, id);

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
        String sqlCount = "SELECT count(*) FROM ibm_hm_bet_item where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_hm_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_hm_bet_item  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
        }
        return dao.page(sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 获取分页IbmHmBetItem数据
     *
     * @param sortFieldName 排序字段名
     * @param sortOrderName 排序顺序
     * @param pageIndex     页面索引
     * @param pageSize      页面大小
     * @return 分页IbmHmBetItem数据
     */
    public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
            throws Exception {
        String sqlCount = "SELECT count(*) FROM ibm_hm_bet_item where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_hm_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_hm_bet_item  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
        }
        return dao.page(IbmHmBetItem.class, sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 按照更新顺序查询所有可用Map信息
     *
     * @return 可用Map信息
     */
    public List<Map<String, Object>> findAll() throws Exception {
        String sql = "SELECT * FROM ibm_hm_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findMapList(sql, null);
    }

    /**
     * 按照更新顺序查询所有可用IbmHmBetItem数据信息
     *
     * @return 可用<IbmHmBetItem>数据信息
     */
    public List findObjectAll() throws Exception {
        String sql = "SELECT * FROM ibm_hm_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findObjectList(IbmHmBetItem.class, sql);
    }

    /**
     * 获取投注信息
     *
     * @param hmBetItemId 投注信息主键
     * @return 投注信息
     */
    public Map<String, Object> findBetInfo(String hmBetItemId) throws SQLException {
        String sql = "SELECT GAME_ID_,PERIOD_,BET_CONTENT_,FUND_T_ as FUNDS_T_,BET_INFO_CODE_ FROM ibm_hm_bet_item "
                + " where IBM_HM_BET_ITEM_ID_ = ? and state_ = ? ";
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(hmBetItemId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql, parameterList);
    }


    /**
     * 获取投注信息
     *
     * @param handicapMemberId 盘口会员id
     * @param gameCode         游戏code
     * @param period           期数
     * @param betInfoCode      投注信息code
     * @param execState        执行状态
     * @return 投注信息
     */
    public Map<String, Object> findBetInfo(String handicapMemberId, String gameCode, String period, String betInfoCode,
                                           String execState) throws SQLException {
        String sql = "SELECT ihbi.IBM_HM_BET_ITEM_ID_ as key_,ihbi.BET_CONTENT_ as value_ FROM ibm_hm_bet_item ihbi"
                + " LEFT JOIN ibm_game ig ON ihbi.GAME_ID_ = ig.IBM_GAME_ID_"
                + " WHERE ihbi.HANDICAP_MEMBER_ID_ =? AND ig.GAME_CODE_=? AND ihbi.PERIOD_=? AND ihbi.EXEC_STATE_=? AND "
                + " ihbi.BET_INFO_CODE_ in('" + String.join("','", betInfoCode.split(",")) + "')";
        List<Object> parameters = new ArrayList<>();
		if(StringTool.isContains(period,"-")){
			period=period.substring(4);
		}
        parameters.add(handicapMemberId);
        parameters.add(gameCode);
        parameters.add(period);
        parameters.add(execState);
        return super.findKVMap(sql, parameters);
    }

    /**
     * 获取处理过的投注信息
     *
     * @param handicapMemberId 盘口会员id
     * @param gameCode         游戏code
     * @param period           期数
     * @param betInfoCode      投注信息code
     * @return 投注信息
     */
    public Map<String, Object> findBetInfo(String handicapMemberId, String gameCode, String period, String betInfoCode)
            throws SQLException {
        String sql = "SELECT ihbi.IBM_HM_BET_ITEM_ID_ as key_,ihbi.BET_CONTENT_ as value_ FROM ibm_hm_bet_item ihbi"
                + " LEFT JOIN ibm_game ig ON ihbi.GAME_ID_ = ig.IBM_GAME_ID_"
                + " WHERE ihbi.HANDICAP_MEMBER_ID_ =? AND ig.GAME_CODE_=? AND ihbi.PERIOD_=? AND (ihbi.EXEC_STATE_=? or ihbi.EXEC_STATE_=?) AND "
                + " ihbi.BET_INFO_CODE_ in('" + String.join("','", betInfoCode.split(",")) + "')";
        List<Object> parameters = new ArrayList<>();
		if(StringTool.isContains(period,"-")){
			period=period.substring(4);
		}
        parameters.add(handicapMemberId);
        parameters.add(gameCode);
        parameters.add(period);
        parameters.add(IbmStateEnum.SUCCESS.name());
        parameters.add(IbmStateEnum.FAIL.name());
        return super.findKVMap(sql, parameters);
    }

    /**
     * 修改执行状态
     *
     * @param hmBetItems  盘口会员投注信息
     * @param requestType 执行状态
     */
    public void updateProcessInfo(Map<String, Object> hmBetItems, IbmStateEnum requestType) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update ibm_hm_bet_item set EXEC_STATE_=?,UPDATE_TIME_LONG_=? where IBM_HM_BET_ITEM_ID_ in(");
        List<Object> parameters = new ArrayList<>();
        parameters.add(requestType.name());
        parameters.add(System.currentTimeMillis());
        for (String hmBetItemId : hmBetItems.keySet()) {
            sql.append("?,");
            parameters.add(hmBetItemId);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        super.dao.execute(sql.toString(), parameters);
    }

    /**
     * 保存错误信息
     *
     * @param errorMap 错误信息map
     */
    public void updateErrorInfo(Map<String, String> errorMap) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        sql.append("select IBM_HM_BET_ITEM_ID_ as key_,DESC_ as value_ from ibm_hm_bet_item where IBM_HM_BET_ITEM_ID_ in (");
        for (String key : errorMap.keySet()) {
            sql.append("?,");
            parameters.add(key);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        Map<String, String> map = findKVMap(sql.toString(), parameters);
        if (ContainerTool.isEmpty(map)) {
            return;
        }
        sql.delete(0, sql.length());
        parameters.clear();

        sql.append("update ibm_hm_bet_item set UPDATE_TIME_LONG_=?,DESC_= CASE IBM_HM_BET_ITEM_ID_");
        parameters.add(System.currentTimeMillis());
        for (Map.Entry<String, String> entry : errorMap.entrySet()) {
            sql.append(" WHEN ? THEN ?");
            parameters.add(entry.getKey());
            parameters.add(StringTool.notEmpty(map.get(entry.getKey())) ? map.get(entry.getKey()).concat(",").concat(entry.getValue()) : entry.getValue());
        }
        sql.append(" end where IBM_HM_BET_ITEM_ID_ in (");
        for (String key : errorMap.keySet()) {
            sql.append("?,");
            parameters.add(key);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        super.dao.execute(sql.toString(), parameters);
    }

    /**
     * 开奖期更新数据
     *
     * @param handicapMemberId 盘口会员id
     * @param checkTime        检查时间
     * @param gameId           游戏ID
     * @return 开奖期更新数据
     */
    public List<Map<String, Object>> listDrawInfo(String gameId, String handicapMemberId, long checkTime)
            throws SQLException {
        String sql = "SELECT IBM_HM_BET_ITEM_ID_,DRAW_NUMBER_,BET_MODE_,EXEC_STATE_,PROFIT_T_,DESC_,BET_TYPE_ FROM ibm_hm_bet_item "
                + "WHERE HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? AND GAME_ID_ = ? AND CREATE_TIME_LONG_ <= ? "
                + "AND UPDATE_TIME_LONG_ >= ? order by UPDATE_TIME_LONG_ desc";
        List<Object> parameterList = new ArrayList<>(5);
        parameterList.add(handicapMemberId);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(gameId);
        parameterList.add(checkTime);
        parameterList.add(checkTime);
        return this.dao.findMapList(sql, parameterList);
    }

    /**
     * 查询新的投注记录数
     *
     * @param handicapMemberId 盘口会员id
     * @param checkTime        检查时间
     * @param gameId           游戏ID
     * @param number           条数
     * @return 投注记录数
     */
    public List<Map<String, Object>> listNewBetInfo(String gameId, String handicapMemberId, long checkTime, int number)
            throws SQLException {
        String sql = "SELECT IBM_HM_BET_ITEM_ID_,PERIOD_,FOLLOW_MEMBER_ACCOUNT_,FUND_T_,"
                + "BET_CONTENT_,DRAW_NUMBER_,EXEC_STATE_,PROFIT_T_,ODDS_,BET_MODE_,BET_TYPE_,DESC_ FROM"
                + " ibm_hm_bet_item  WHERE GAME_ID_ = ? AND  HANDICAP_MEMBER_ID_ = ? AND STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(gameId);
        parameterList.add(handicapMemberId);
        parameterList.add(IbmStateEnum.OPEN.name());
        if (number == 0) {
            sql += " AND CREATE_TIME_LONG_ >= ? ORDER BY CREATE_TIME_LONG_ desc";
            parameterList.add(checkTime);
        } else {
            sql += " AND EXEC_STATE_!=? ORDER BY CREATE_TIME_LONG_ desc limit " + number;
            parameterList.add(IbmStateEnum.FAIL.name());
        }
        return this.dao.findMapList(sql, parameterList);
    }

    /**
     * 获取当期投注总额和当期投注总数
     *
     * @param gameId           游戏id
     * @param handicapMemberId 盘口会员id
     * @param period           期数
     * @param data             data
     */
    public void findSum(String gameId, String handicapMemberId, Object period, Map<String, Object> data)
            throws SQLException {
        String sql = "select SUM(FUND_T_) as amount,SUM(BET_CONTENT_LEN_) as number FROM ibm_hm_bet_item"
                + " WHERE HANDICAP_MEMBER_ID_ = ? and GAME_ID_=? and PERIOD_= ? and STATE_ != ?";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(handicapMemberId);
        parameterList.add(gameId);
        parameterList.add(period);
        parameterList.add(IbmStateEnum.DEL.name());
        Map<String, Object> map = super.dao.findMap(sql, parameterList);

        sql = "SELECT count(IBM_HM_BET_ITEM_ID_) as waiting FROM ibm_hm_bet_item WHERE HANDICAP_MEMBER_ID_ = ?"
                + " and GAME_ID_=? and PERIOD_= ? and STATE_ != ? and EXEC_STATE_=? ";
        parameterList.add(IbmStateEnum.PROCESS.name());
        //等待投注数
        data.put("waiting", Integer.parseInt(super.dao.findString("waiting", sql, parameterList)));
        //当期投注总额
        data.put("amount", 0);
        //当期投注总数
        data.put("number", 0);
        //期数
        data.put("period", period);
        if (StringTool.notEmpty(map.get("amount")) && StringTool.notEmpty(map.get("number"))) {
            data.put("amount", NumberTool.doubleT(map.get("amount")));
            data.put("number", Integer.parseInt(map.get("number").toString()));
        }
    }

    /**
     * 获取投注信息
     * @param gameId 游戏主键
     * @param handicapMemberId 盘口会员主键
     * @param period 期数
     * @return 投注信息
     */
    public List<Map<String, Object>> listBetInfo(String gameId, String handicapMemberId, Object period)
            throws SQLException {
        String sql = "SELECT PERIOD_,FOLLOW_MEMBER_ACCOUNT_,FUND_T_,"
                + "BET_CONTENT_,EXEC_STATE_,PROFIT_T_,BET_MODE_,BET_TYPE_,DESC_ FROM"
                + " ibm_hm_bet_item  WHERE HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and PERIOD_= ? AND STATE_ != ? "
                + " order by UPDATE_TIME_LONG_ desc ";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(handicapMemberId);
        parameterList.add(gameId);
        parameterList.add(period);
        parameterList.add(IbmStateEnum.DEL.name());
        return this.dao.findMapList(sql, parameterList);
    }

    /**
     * 获取投注项执行信息
     *
     * @param handicapId 盘口主键
     * @param period     期数
     * @param betMode    投注模式
     * @param tableName  表名
     * @return 执行信息
     */
    public Map<String, List<Map<String, Object>>> mapBetItemInfo(String handicapId, Object period, IbmTypeEnum betMode,
                                                                   String tableName) throws SQLException {
        String sql = "SELECT IBM_HM_BET_ITEM_ID_ AS BET_ITEM_ID_, HANDICAP_MEMBER_ID_, FOLLOW_MEMBER_ACCOUNT_, "
                + " FUND_T_, BET_CONTENT_, BET_CONTENT_LEN_ FROM " + tableName + " WHERE HANDICAP_ID_ = ? "
                + " AND PERIOD_ = ? AND EXEC_STATE_ = ? AND BET_MODE_ = ? AND STATE_ = ? ORDER BY HANDICAP_MEMBER_ID_";
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(handicapId);
        parameterList.add(period);
        parameterList.add(IbmStateEnum.SUCCESS.name());
        parameterList.add(betMode.name());
        parameterList.add(IbmStateEnum.OPEN.name());
        List<Map<String, Object>> betItemList = super.findMapList(sql, parameterList);

        Map<String, List<Map<String, Object>>> mapExecBetItem = new HashMap<>(betItemList.size() / 4);
        if (ContainerTool.isEmpty(betItemList)) {
            return mapExecBetItem;
        }
        for (Map<String, Object> betItem : betItemList) {
            String handicapMemberId = betItem.remove("HANDICAP_MEMBER_ID_").toString();
            if (mapExecBetItem.containsKey(handicapMemberId)) {
                mapExecBetItem.get(handicapMemberId).add(betItem);
            } else {
                List<Map<String, Object>> execBetItems = new ArrayList<>(4);
                execBetItems.add(betItem);
                mapExecBetItem.put(handicapMemberId, execBetItems);
            }
        }
        return mapExecBetItem;
    }
	/**
	 * 获取投注信息
	 *
	 * @param gameId      游戏id
	 * @param period      期数
	 * @param handicapIds 盘口列表
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> mapBetItemInfo(String gameId, Object period, List<String> handicapIds) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBM_HM_BET_ITEM_ID_ AS BET_ID_, HANDICAP_MEMBER_ID_, FOLLOW_MEMBER_ACCOUNT_, "
				+ " FUND_T_, BET_CONTENT_, BET_CONTENT_LEN_ FROM ibm_hm_bet_item WHERE GAME_ID_ = ?"
				+ " AND PERIOD_ = ? AND EXEC_STATE_ = ? AND STATE_ = ? and HANDICAP_ID_ in(");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(gameId);
		parameterList.add(period);
		parameterList.add(IbmStateEnum.SUCCESS.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String handicapId : handicapIds) {
			sql.append("?,");
			parameterList.add(handicapId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(") ORDER BY HANDICAP_MEMBER_ID_");
		return super.findKeyMaps(sql,parameterList,"HANDICAP_MEMBER_ID_");
	}

    /**
     * 更新结算信息
     *
     * @param betItemInfo 结果信息
     * @param drawNumber  开奖号码
     * @param tableName   表名
     * @param nowTime     更新时间
     */
    public void updateResult(Map<String, Object> betItemInfo, String drawNumber, String tableName, Date nowTime)
            throws SQLException {
        String sql = "UPDATE " + tableName + " SET PROFIT_T_ = ?, ODDS_ = ?, EXEC_STATE_ = ?, DRAW_NUMBER_ = ?, "
                + " UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? WHERE IBM_HM_BET_ITEM_ID_ = ?";
        List<Object> parameterList = new ArrayList<>(7);
        parameterList.add(betItemInfo.get("PROFIT_T_"));
        parameterList.add(betItemInfo.get("ODDS_"));
        parameterList.add(IbmStateEnum.FINISH.name());
        parameterList.add(drawNumber);
        parameterList.add(nowTime);
        parameterList.add(System.currentTimeMillis());
        parameterList.add("更新结算信息");
        parameterList.add(betItemInfo.get("BET_ITEM_ID_"));
        super.execute(sql, parameterList);
    }
	/**
	 * 更新结算信息
	 *
	 * @param betInfos	 结果信息
	 * @param drawNumber  开奖号码
	 * @param nowTime     更新时间
	 */
	public void updateResult(List<Map<String, Object>> betInfos, String drawNumber, Date nowTime) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"update ibm_hm_bet_item set EXEC_STATE_ = ?,DRAW_NUMBER_ =?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ =?,");
		sql.append("PROFIT_T_= CASE IBM_HM_BET_ITEM_ID_");
		List<Object> parameters = new ArrayList<>(7);
		parameters.add(IbmStateEnum.FINISH.name());
		parameters.add(drawNumber);
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());

		StringBuilder sqlPlus = new StringBuilder();
		List<Object> parametersPlus = new ArrayList<>();
		for (Map<String, Object> map : betInfos) {
			sql.append(" WHEN ? THEN ?");
			parameters.add(map.get("BET_ID_"));
			parameters.add(map.get("PROFIT_T_"));

			sqlPlus.append("?,");
			parametersPlus.add(map.get("BET_ID_"));
		}
		sql.append(" end where IBM_HM_BET_ITEM_ID_ in (");
		sql.append(sqlPlus);
		parameters.addAll(parametersPlus);
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

    /**
     * 获取待开奖盘口ids
     *
     * @param gameId 游戏id
     * @param period 期数
     * @return
     */
    public List<String> findHandicapId(String gameId, Object period) throws SQLException {
        String sql = "select HANDICAP_ID_ as key_ from ibm_hm_bet_item where PERIOD_=? AND GAME_ID_=?"
                + " AND EXEC_STATE_=? GROUP BY HANDICAP_ID_";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(period);
        parameterList.add(gameId);
        parameterList.add(IbmStateEnum.SUCCESS.name());
        return super.findStringList(sql, parameterList);
    }

    /**
     * 获取投注总和总数
     *
     * @param hmBetItems 投注详情
     * @return
     */
    public Map<String, Object> findSum(Map<String, Object> hmBetItems) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select SUM(FUND_T_) as amount,SUM(BET_CONTENT_LEN_) as number FROM ibm_hm_bet_item")
                .append(" WHERE IBM_HM_BET_ITEM_ID_ in(");
        List<Object> parameterList = new ArrayList<>(hmBetItems.size());
        for (Map.Entry<String, Object> entry : hmBetItems.entrySet()) {
            sql.append("?,");
            parameterList.add(entry.getKey());
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        return super.dao.findMap(sql.toString(), parameterList);
    }

    /**
     * 清除表格
     *
     * @param gameId           游戏id
     * @param handicapMemberId 盘口会员id
     * @param time             已开奖期数时间
     */
    public void clearForm(String gameId, String handicapMemberId, long time) throws SQLException {
        String sql="update ibm_hm_bet_item set STATE_=?,UPDATE_TIME_LONG_=? where HANDICAP_MEMBER_ID_=? and "
                + " GAME_ID_=? and STATE_=? and CREATE_TIME_LONG_<?";
        List<Object> parameters=new ArrayList<>(6);
        parameters.add(IbmStateEnum.CLOSE.name());
        parameters.add(System.currentTimeMillis());
        parameters.add(handicapMemberId);
        parameters.add(gameId);
        parameters.add(IbmStateEnum.OPEN.name());
        parameters.add(time);
        super.dao.execute(sql,parameters);
    }

    /**
     * 通过跟投ids获取投注信息
     * @param followBetIds
     * @param execState
     * @return
     * @throws SQLException
     */
    public Map<String, Object> findByFollowId(String followBetIds, String execState) throws SQLException {
        String sql = "SELECT IBM_HM_BET_ITEM_ID_ as key_,BET_CONTENT_ as value_ FROM ibm_hm_bet_item "
                + " WHERE EXEC_STATE_=? AND "
                + " CLIENT_HA_FOLLOW_BET_ID_ in('" + String.join("','", followBetIds.split(",")) + "')";
        List<Object> parameters = new ArrayList<>();
        parameters.add(execState);
        return super.findKVMap(sql, parameters);
    }
}
