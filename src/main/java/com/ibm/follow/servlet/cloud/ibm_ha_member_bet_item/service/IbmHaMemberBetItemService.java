package com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.entity.IbmHaMemberBetItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Robot
 */
public class IbmHaMemberBetItemService extends BaseService {

    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 保存{ay_table_class}对象数据
     *
     * @param entity IbmHaMemberBetItem对象数据
     */
    public String save(IbmHaMemberBetItem entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_ha_member_bet_item的 IBM_HA_MEMBER_BET_ITEM_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_ha_member_bet_item set state_='DEL' where IBM_HA_MEMBER_BET_ITEM_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBM_HA_MEMBER_BET_ITEM_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_ha_member_bet_item的 IBM_HA_MEMBER_BET_ITEM_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "update ibm_ha_member_bet_item set state_='DEL' where IBM_HA_MEMBER_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除ibm_ha_member_bet_item的 IBM_HA_MEMBER_BET_ITEM_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_ha_member_bet_item where IBM_HA_MEMBER_BET_ITEM_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBM_HA_MEMBER_BET_ITEM_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_ha_member_bet_item的 IBM_HA_MEMBER_BET_ITEM_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "delete from ibm_ha_member_bet_item where IBM_HA_MEMBER_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmHaMemberBetItem实体信息
     *
     * @param entity IbmHaMemberBetItem实体
     */
    public void update(IbmHaMemberBetItem entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_ha_member_bet_item表主键查找IbmHaMemberBetItem实体
     *
     * @param id ibm_ha_member_bet_item 主键
     * @return IbmHaMemberBetItem实体
     */
    public IbmHaMemberBetItem find(String id) throws Exception {
        return (IbmHaMemberBetItem) this.dao.find(IbmHaMemberBetItem.class, id);

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
    public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
                             Integer pageSize) throws Exception {
        String sqlCount = "SELECT count(*) FROM ibm_ha_member_bet_item where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_ha_member_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_ha_member_bet_item  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
        }
        return dao.page(sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 获取分页IbmHaMemberBetItem数据
     *
     * @param sortFieldName 排序字段名
     * @param sortOrderName 排序顺序
     * @param pageIndex     页面索引
     * @param pageSize      页面大小
     * @return 分页IbmHaMemberBetItem数据
     */
    public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
                                   Integer pageSize) throws Exception {
        String sqlCount = "SELECT count(*) FROM ibm_ha_member_bet_item where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_ha_member_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_ha_member_bet_item  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
        }
        return dao.page(IbmHaMemberBetItem.class, sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 按照更新顺序查询所有可用Map信息
     *
     * @return 可用Map信息
     */
    public List<Map<String, Object>> findAll() throws Exception {
        String sql = "SELECT * FROM ibm_ha_member_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findMapList(sql, null);
    }

    /**
     * 按照更新顺序查询所有可用IbmHaMemberBetItem数据信息
     *
     * @return 可用<IbmHaMemberBetItem>数据信息
     */
    public List findObjectAll() throws Exception {
        String sql = "SELECT * FROM ibm_ha_member_bet_item  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findObjectList(IbmHaMemberBetItem.class, sql);
    }

    /**
     * 开奖期更新数据
     *
     * @param handicapAgentId 盘口代理会员id
     * @param checkTime       检查时间
     * @param gameId          游戏ID
     * @return 开奖期更新数据
     */
    public List<Map<String, Object>> listDrawInfo(String gameId, String handicapAgentId, long checkTime) throws SQLException {
        String sql = "SELECT IBM_HA_MEMBER_BET_ITEM_ID_,EXEC_STATE_ FROM ibm_ha_member_bet_item WHERE HANDICAP_AGENT_ID_ = ? AND STATE_ != ? "
                + " AND GAME_ID_ = ? AND CREATE_TIME_LONG_ <= ? AND UPDATE_TIME_LONG_ >= ?  order by UPDATE_TIME_LONG_ desc";
        List<Object> parameterList = new ArrayList<>(5);
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(gameId);
        parameterList.add(checkTime);
        parameterList.add(checkTime);
        return this.dao.findMapList(sql, parameterList);
    }

    /**
     * 修改执行状态
     *
     * @param hmBetItems  盘口会员投注信息
     * @param requestType 执行状态
     */
    public List<String> updateProcessInfo(Map<String, Object> hmBetItems, IbmStateEnum requestType) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("select CLIENT_HA_FOLLOW_BET_ID_ from ibm_hm_bet_item where IBM_HM_BET_ITEM_ID_ in(");
        List<Object> parameters = new ArrayList<>();
        for (String hmBetItemId : hmBetItems.keySet()) {
            sql.append("?,");
            parameters.add(hmBetItemId);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        List<String> haFollowBetIds = super.dao.findStringList("CLIENT_HA_FOLLOW_BET_ID_", sql.toString(), parameters);
        if (ContainerTool.isEmpty(haFollowBetIds)) {
            return haFollowBetIds;
        }
        sql.delete(0, sql.length());
        parameters.clear();
        sql.append("update ibm_ha_member_bet_item set UPDATE_TIME_LONG_=?,EXEC_STATE_=? where CLIENT_HA_FOLLOW_BET_ID_ in(");
        parameters.add(System.currentTimeMillis());
        parameters.add(requestType.name());
        for (String haFollowBetId : haFollowBetIds) {
            String[] followBetIds=haFollowBetId.split(",");
            for(String followBetId:followBetIds){
                sql.append("?,");
                parameters.add(followBetId);
            }
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        super.dao.execute(sql.toString(), parameters);
        return haFollowBetIds;
    }

    /**
     * 查询新的投注记录数
     *
     * @param handicapAgentId 盘口代理会员id
     * @param checkTime       检查时间
     * @param gameId          游戏ID
     * @return 投注记录数
     */
    public List<Map<String, Object>> listNewBetInfo(String gameId, String handicapAgentId, long checkTime, int number) throws SQLException {
        String sql = "SELECT IBM_HA_MEMBER_BET_ITEM_ID_,FOLLOW_MEMBER_ACCOUNT_,EXEC_STATE_,PERIOD_,BET_CONTENT_,BET_FUND_T_," +
                "FOLLOW_CONTENT_,FOLLOW_FUND_T_,CREATE_TIME_LONG_ FROM ibm_ha_member_bet_item WHERE GAME_ID_ = ? AND HANDICAP_AGENT_ID_ = ? " +
                "AND STATE_ = ? ";
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(gameId);
        parameterList.add(handicapAgentId);
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
     * 查询投注记录
     * @param gameId 游戏ID
     * @param handicapAgentId 盘口代理会员id
     * @param period 期数
     * @return 投注记录
     */
    public List<Map<String, Object>> listBetInfo(String gameId, String handicapAgentId, Object period)
            throws SQLException {
        String sql = "SELECT FOLLOW_MEMBER_ACCOUNT_,EXEC_STATE_,BET_CONTENT_,BET_FUND_T_,FOLLOW_CONTENT_,"
                + " FOLLOW_FUND_T_ FROM ibm_ha_member_bet_item WHERE GAME_ID_ = ? AND HANDICAP_AGENT_ID_ = ? " +
                "  and PERIOD_ = ? AND STATE_ != ? ORDER BY UPDATE_TIME_LONG_ desc";
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(gameId);
        parameterList.add(handicapAgentId);
        parameterList.add(period);
        parameterList.add(IbmStateEnum.DEL.name());
        return this.dao.findMapList(sql, parameterList);
    }

    /**
     * 获取盘口代理ids
     *
     * @param haFollowBetIds 客户端代理跟投ids
     * @return
     */
    public List<String> findHaIds(List<String> haFollowBetIds) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<Object> parameterList = new ArrayList<>(haFollowBetIds.size());
        sql.append("select HANDICAP_AGENT_ID_ from ibm_ha_member_bet_item where CLIENT_HA_FOLLOW_BET_ID_ in(");
        for (String haFollowBetId : haFollowBetIds) {
            String[] followBetIds=haFollowBetId.split(",");
            for(String followBetId:followBetIds){
                sql.append("?,");
                parameterList.add(followBetId);
            }
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        return super.dao.findStringList("HANDICAP_AGENT_ID_", sql.toString(), parameterList);
    }

    /**
     * 清空表格
     *
     * @param gameId          游戏id
     * @param handicapAgentId 盘口代理id
     * @param time            已开奖期数时间
     */
    public void clearForm(String gameId, String handicapAgentId, long time) throws SQLException {
        String sql = "update ibm_ha_member_bet_item set STATE_=?,UPDATE_TIME_LONG_=? where HANDICAP_AGENT_ID_=? and "
                + " GAME_ID_=? and STATE_=? and CREATE_TIME_LONG_<?";
        List<Object> parameters = new ArrayList<>(6);
        parameters.add(IbmStateEnum.CLOSE.name());
        parameters.add(System.currentTimeMillis());
        parameters.add(handicapAgentId);
        parameters.add(gameId);
        parameters.add(IbmStateEnum.OPEN.name());
        parameters.add(time);
        super.dao.execute(sql, parameters);
    }

    /**
     * 修改跟投信息
     * @param haFollowBetId      跟投信息
     */
    public void updateProcessInfo(String haFollowBetId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        sql.append("update ibm_ha_member_bet_item set UPDATE_TIME_LONG_=?,EXEC_STATE_=? where CLIENT_HA_FOLLOW_BET_ID_ in(");
        parameters.add(System.currentTimeMillis());
        parameters.add(IbmStateEnum.SUCCESS.name());
        String[] followBetIds = haFollowBetId.split(",");
        for (String followBetId : followBetIds) {
            sql.append("?,");
            parameters.add(followBetId);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        super.dao.execute(sql.toString(), parameters);
    }
}
