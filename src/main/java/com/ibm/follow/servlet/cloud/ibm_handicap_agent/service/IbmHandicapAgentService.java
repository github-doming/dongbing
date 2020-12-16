package com.ibm.follow.servlet.cloud.ibm_handicap_agent.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.connector.pc.customer.login.MemberLoginAction;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.entity.IbmHandicapAgent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmHandicapAgentService extends BaseServicePlus {

    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 保存{ay_table_class}对象数据
     *
     * @param entity IbmHandicapAgent对象数据
     */
    public String save(IbmHandicapAgent entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_handicap_agent的 IBM_HANDICAP_AGENT_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_handicap_agent set state_='DEL' where IBM_HANDICAP_AGENT_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBM_HANDICAP_AGENT_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_handicap_agent的 IBM_HANDICAP_AGENT_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "update ibm_handicap_agent set state_='DEL' where IBM_HANDICAP_AGENT_ID_ in(" + stringBuilder
                    .toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除ibm_handicap_agent的 IBM_HANDICAP_AGENT_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_handicap_agent where IBM_HANDICAP_AGENT_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBM_HANDICAP_AGENT_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_handicap_agent的 IBM_HANDICAP_AGENT_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql =
                    "delete from ibm_handicap_agent where IBM_HANDICAP_AGENT_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmHandicapAgent实体信息
     *
     * @param entity IbmHandicapAgent实体
     */
    public void update(IbmHandicapAgent entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_handicap_agent表主键查找IbmHandicapAgent实体
     *
     * @param id ibm_handicap_agent 主键
     * @return IbmHandicapAgent实体
     */
    public IbmHandicapAgent find(String id) throws Exception {
        return (IbmHandicapAgent) this.dao.find(IbmHandicapAgent.class, id);

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
        String sqlCount = "SELECT count(*) FROM ibm_handicap_agent where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_handicap_agent  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_handicap_agent  where state_!='DEL' order by " + sortFieldName + " "
                    + sortOrderName;
        }
        return dao.page(sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 获取分页IbmHandicapAgent数据
     *
     * @param sortFieldName 排序字段名
     * @param sortOrderName 排序顺序
     * @param pageIndex     页面索引
     * @param pageSize      页面大小
     * @return 分页IbmHandicapAgent数据
     */
    public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
            throws Exception {
        String sqlCount = "SELECT count(*) FROM ibm_handicap_agent where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_handicap_agent  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_handicap_agent  where state_!='DEL' order by " + sortFieldName + " "
                    + sortOrderName;
        }
        return dao.page(IbmHandicapAgent.class, sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 按照更新顺序查询所有可用Map信息
     *
     * @return 可用Map信息
     */
    public List<Map<String, Object>> findAll() throws Exception {
        String sql = "SELECT * FROM ibm_handicap_agent  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findMapList(sql, null);
    }

    /**
     * 按照更新顺序查询所有可用IbmHandicapAgent数据信息
     *
     * @return 可用<IbmHandicapAgent>数据信息
     */
    public List findObjectAll() throws Exception {
        String sql = "SELECT * FROM ibm_handicap_agent  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findObjectList(IbmHandicapAgent.class, sql);
    }

    /**
     * 查询该用户所有账号信息
     *
     * @param userId     用户id
     * @param handicapId 盘口id
     * @return 账号信息
     */
    public List<Map<String, Object>> listAllAccount(String userId, String handicapId) throws SQLException {
        String sql = "SELECT iha.IBM_HANDICAP_AGENT_ID_ as HANDICAP_AGENT_ID_, iha.AGENT_ACCOUNT_ FROM "
                + " ibm_handicap_agent iha LEFT JOIN ibm_ha_info ihi ON iha.IBM_HANDICAP_AGENT_ID_ = ihi.HANDICAP_AGENT_ID_ "
                + " WHERE iha.APP_USER_ID_ = ? AND iha.HANDICAP_ID_ = ? AND iha.STATE_ = ? AND ihi.STATE_ != ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(userId);
        parameterList.add(handicapId);
        parameterList.add(IbmStateEnum.OPEN.name());
        parameterList.add(IbmStateEnum.LOGIN.name());
        return super.dao.findMapList(sql, parameterList);
    }

    /**
     * 代理ID登录 修改
     *
     * @param handicapAgentId 代理会员主键
     * @param landedTimeLong  定时登录事件
     * @param agentAccount    代理账户
     * @param agentPassword   代理密码
     * @param handicapItemId  盘口详情主键
     */
    public void update(String handicapAgentId, Long landedTimeLong, String agentAccount, String agentPassword,
                       String handicapItemId) throws SQLException {
        String sql = "UPDATE ibm_handicap_agent set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? ";
        List<Object> parameterList = new ArrayList<>(9);
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());

        if (StringTool.notEmpty(landedTimeLong)) {
            sql = sql.concat(",LANDED_TIME_LONG_ = ? ");
            parameterList.add(landedTimeLong + 5000L);
        }else{
            sql = sql.concat(",OPERATING_ = ? ");
            parameterList.add(IbmStateEnum.LOGIN.name());
        }
        if (StringTool.notEmpty(agentAccount)) {
            sql = sql.concat(",AGENT_ACCOUNT_ = ? ");
            parameterList.add(agentAccount);
        }
        if (StringTool.notEmpty(agentPassword)) {
            sql = sql.concat(",AGENT_PASSWORD_ = ? ");
            parameterList.add(agentPassword);
        }
        if (StringTool.notEmpty(handicapItemId)) {
            sql = sql.concat(",HANDICAP_ITEM_ID_ = ? ");
            parameterList.add(handicapItemId);
        }
        sql = sql.concat(",DESC_ = ? where IBM_HANDICAP_AGENT_ID_ = ? and STATE_ = ?");
        parameterList.add(MemberLoginAction.class.getName().concat("，代理ID登录"));
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.OPEN.name());
        super.dao.execute(sql, parameterList);
    }

    /**
     * 获取代理信息
     *
     * @param handicapAgentId 盘口代理主键
     * @param userId          用户主键
     * @return 会员信息
     */
    public Map<String, Object> findInfo(String handicapAgentId, String userId) throws SQLException {
        String sql = "SELECT AGENT_ACCOUNT_,AGENT_PASSWORD_,HANDICAP_ITEM_ID_,OPERATING_ FROM `ibm_handicap_agent` "
                + " where IBM_HANDICAP_AGENT_ID_ = ? and APP_USER_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapAgentId);
        parameterList.add(userId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql, parameterList);
    }

    /**
     * 获取 盘口代理主键
     *
     * @param userId       用户主键
     * @param agentAccount 代理账户
     * @param handicapId   盘口详情主键
     * @return 盘口会员主键
     */
    public Map<String,Object> findAgentInfo(String userId, String agentAccount, String handicapId) throws SQLException {
        String sql = "SELECT IBM_HANDICAP_AGENT_ID_,OPERATING_,STATE_ FROM `ibm_handicap_agent` where APP_USER_ID_ = ? and "
                + " AGENT_ACCOUNT_ = ? and HANDICAP_ID_ = ? and STATE_ != ? LIMIT 1";
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(userId);
        parameterList.add(agentAccount);
        parameterList.add(handicapId);
        parameterList.add(IbmStateEnum.DEL.name());
        return super.dao.findMap(sql,parameterList);
    }

    /**
     * 代理账号密码登录 修改
     *
     * @param handicapAgentId 盘口代理主键
     * @param agentPassword    代理密码
     * @param nowTime          更新时间
     */
    public void update(String handicapAgentId, String agentPassword,String handicapItemId, Date nowTime) throws SQLException {
        String sql = "UPDATE ibm_handicap_agent SET AGENT_PASSWORD_ = ?,HANDICAP_ITEM_ID_=?,OPERATING_=?,UPDATE_TIME_ = ?, "
                + " UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBM_HANDICAP_AGENT_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(8);
        parameterList.add(agentPassword);
        parameterList.add(handicapItemId);
        parameterList.add(IbmStateEnum.LOGIN.name());
        parameterList.add(nowTime);
        parameterList.add(System.currentTimeMillis());
        parameterList.add(MemberLoginAction.class.getName().concat("，代理账号密码登录"));
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.OPEN.name());
        super.dao.execute(sql, parameterList);
    }

    /**
     * 获取盘口主键
     *
     * @param handicapAgentId 盘口代理主键
     * @param userId          用户主键
     * @return 盘口主键
     */
    public String findHandicapId(String handicapAgentId, String userId) throws SQLException {
        String sql = "SELECT HANDICAP_ID_ FROM `ibm_handicap_agent` "
                + " where IBM_HANDICAP_AGENT_ID_ = ? and APP_USER_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapAgentId);
        parameterList.add(userId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findString("HANDICAP_ID_", sql, parameterList);
    }

    /**
     * 获取登录信息
     *
     * @param handicapAgentId 盘口代理主键
     * @return 登录信息
     */
    public Map<String, Object> findLoginInfo(String handicapAgentId) throws SQLException {
        String sql = "SELECT iha.HANDICAP_CODE_,ihi.HANDICAP_URL_, ihi.HANDICAP_CAPTCHA_, iha.HANDICAP_ID_, iha.AGENT_ACCOUNT_, "
                + " iha.AGENT_PASSWORD_ FROM `ibm_handicap_agent` iha LEFT JOIN ibm_handicap_item ihi ON "
                + " ihi.IBM_HANDICAP_ITEM_ID_ = iha.HANDICAP_ITEM_ID_ WHERE iha.IBM_HANDICAP_AGENT_ID_ = ? AND iha.STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.FIRST.name());
        return super.dao.findMap(sql, parameterList);
    }

    /**
     * 批量获取多代理登录信息
     *
     * @param handicapAgentIds 盘口代理ids
     * @return
     */
    public Map<String, Map<String, Object>> findLoginInfo(Set<String> handicapAgentIds) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(
                "select ihm.IBM_HANDICAP_AGENT_ID_ as HANDICAP_AGENT_ID_,ihm.HANDICAP_CODE_,ihi.HANDICAP_URL_,ihi.HANDICAP_CAPTCHA_,"
                        + "ihm.AGENT_ACCOUNT_,ihm.AGENT_PASSWORD_ from ibm_handicap_agent ihm LEFT JOIN ibm_handicap_item ihi ON ihi.IBM_HANDICAP_ITEM_ID_ = ihm.HANDICAP_ITEM_ID_"
                        + " WHERE ihm.STATE_ = ? AND ihm.IBM_HANDICAP_AGENT_ID_ in (");
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.OPEN.name());
        for (String handicapAgentId : handicapAgentIds) {
            sql.append("?,");
            parameterList.add(handicapAgentId);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameterList);
        if (ContainerTool.isEmpty(list)) {
            return new HashMap<>(1);
        }
        Map<String, Map<String, Object>> handicapMemberInfo = new HashMap<>(list.size());
        for (Map<String, Object> map : list) {
            handicapMemberInfo.put(map.get("HANDICAP_AGENT_ID_").toString(), map);
        }
        return handicapMemberInfo;
    }

    /**
     * 获取一分钟以内的定时登录的盘口代理
     *
     * @return 盘口代理信息列表
     */
    public List<Map<String, Object>> listTimeLanded() throws SQLException {
        String sql =
                "SELECT HANDICAP_URL_,HANDICAP_CAPTCHA_,iha.HANDICAP_ID_,ihi.HANDICAP_AGENT_ID_,iha.AGENT_ACCOUNT_, "
                        + " AGENT_PASSWORD_,iha.APP_USER_ID_ FROM `ibm_handicap_agent` iha "
                        + " LEFT JOIN ibm_ha_info ihi ON IBM_HANDICAP_AGENT_ID_ = ihi.HANDICAP_AGENT_ID_ AND ihi.STATE_ != ? "
                        + " LEFT JOIN ibm_handicap_item ii ON iha.HANDICAP_ITEM_ID_ = IBM_HANDICAP_ITEM_ID_ AND ii.STATE_ != ? "
                        + " WHERE LANDED_TIME_LONG_ BETWEEN ? AND ? AND iha.STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(5);
        parameterList.add(IbmStateEnum.LOGIN.name());
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(System.currentTimeMillis() + 60000L);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.findMapList(sql, parameterList);
    }

    /**
     * 不使用
     *
     * @param appUserId
     * @throws SQLException
     */
    public void delByAppUserId(String appUserId) throws SQLException {
        String sql = "delete from ibm_handicap_agent where APP_USER_ID_= ? ";
        List<Object> parameterList = new ArrayList<>(1);
        parameterList.add(appUserId);
        dao.execute(sql, parameterList);
    }

    /**
     * 逻辑删除数据
     *
     * @param appUserId 用户id
     * @throws SQLException
     */
    public void updateStateByAppUserId(String appUserId) throws SQLException {
        String sql = "update ibm_handicap_agent set STATE_ = ? where APP_USER_ID_= ? ";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(appUserId);
        dao.execute(sql, parameterList);
    }


    /**
     * 根据盘口ID查询主键
     *
     * @param handicapId 盘口ID
     * @return 盘口代理主键
     */
    public List<Map<String, Object>> findIdByHandicapId(String handicapId) throws SQLException {
        String sql = "select IBM_HANDICAP_AGENT_ID_ from ibm_handicap_agent where HANDICAP_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return dao.findMapList(sql, parameterList);
    }

    /**
     * 获取操作为登陆和登出的盘口代理
     * @return
     * @param existHaIds
     */
    public List<String> listOperatingHaId(Set<Object> existHaIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("select ich.EXIST_HA_ID_ from ibm_client_ha ich LEFT JOIN ibm_handicap_agent iha on ich.HANDICAP_AGENT_ID_=iha.IBM_HANDICAP_AGENT_ID_");
        sql.append(" where iha.OPERATING_ IN(?,?) AND ich.EXIST_HA_ID_ in (");
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(IbmStateEnum.LOGIN.name());
        parameterList.add(IbmStateEnum.LOGOUT.name());
        for(Object existHaId:existHaIds){
            sql.append("?,");
            parameterList.add(existHaId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.dao.findStringList("EXIST_HA_ID_",sql.toString(),parameterList);
    }

    /**
     * 修改操作
     * @param handicapAgentId   盘口代理id
     */
    public void updateOperating(String handicapAgentId) throws SQLException {
        String sql="update ibm_handicap_agent set OPERATING_=?,UPDATE_TIME_LONG_=? where IBM_HANDICAP_AGENT_ID_=?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(IbmStateEnum.NONE.name());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(handicapAgentId);
        super.dao.execute(sql,parameterList);
    }
    /**
     * 批量修改操作
     * @param handicapAgentIds   盘口代理ids
     */
    public void updateOperating(List<String> handicapAgentIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("update ibm_handicap_agent set OPERATING_=?,UPDATE_TIME_LONG_=? where IBM_HANDICAP_AGENT_ID_ in(");
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(IbmStateEnum.NONE.name());
        parameterList.add(System.currentTimeMillis());
        for(String handicapAgentId:handicapAgentIds){
            sql.append("?,");
            parameterList.add(handicapAgentId);
        }
        super.dao.execute(sql.toString(),parameterList);
    }

    /**
     * 修改十分钟前操作为登陆或登出的代理信息
     */
    public void updateOperating() throws SQLException {
        StringBuilder sql=new StringBuilder();
        sql.append("select IBM_HANDICAP_AGENT_ID_ from ibm_handicap_agent where (OPERATING_=? or OPERATING_=?) and STATE_!=?")
                .append(" and UPDATE_TIME_LONG_ < ?");
        List<Object> parameterList = new ArrayList<>(4);
        parameterList.add(IbmStateEnum.LOGIN.name());
        parameterList.add(IbmStateEnum.LOGOUT.name());
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(System.currentTimeMillis()-90*1000L);
        List<String> handicapAgentIds=super.dao.findStringList("IBM_HANDICAP_AGENT_ID_",sql.toString(),parameterList);
        if(ContainerTool.isEmpty(handicapAgentIds)){
            return ;
        }
        sql.delete(0,sql.length());
        parameterList.clear();
        sql.append("update ibm_handicap_agent set OPERATING_=?,UPDATE_TIME_LONG_=?,DESC_=? where IBM_HANDICAP_AGENT_ID_ in(");
        parameterList.add(IbmStateEnum.NONE.name());
        parameterList.add(System.currentTimeMillis());
        parameterList.add("自动管理修改操作状态");
        for(String handicapAgentId:handicapAgentIds){
            sql.append("?,");
            parameterList.add(handicapAgentId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        super.dao.execute(sql.toString(),parameterList);
    }
    /**
     * 获取盘口代理信息
     * @param handicapAgentIds 盘口代理ids
     * @return
     */
    public List<Map<String, Object>> findAgentInfos(List<String> handicapAgentIds) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameterList = new ArrayList<>();
        sql.append("select IBM_HANDICAP_AGENT_ID_ as HANDICAP_AGENT_ID_,AGENT_ACCOUNT_,AGENT_PASSWORD_,HANDICAP_URL_,HANDICAP_CAPTCHA_,HANDICAP_CODE_");
        sql.append(" from ibm_handicap_agent LEFT JOIN ibm_handicap_item on HANDICAP_ITEM_ID_=IBM_HANDICAP_ITEM_ID_ where IBM_HANDICAP_AGENT_ID_ in(");
        for(String handicapAgentId:handicapAgentIds){
            sql.append("?,");
            parameterList.add(handicapAgentId);
        }
        sql.replace(sql.length()-1,sql.length(),")");
        return super.dao.findMapList(sql.toString(),parameterList);
    }

    /**
     * 获取代理信息
     * @param appUserId     用户id
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> findAgentByUserId(String appUserId) throws SQLException {
        String sql="SELECT iha.IBM_HANDICAP_AGENT_ID_,iha.HANDICAP_ID_,ihi.STATE_ FROM"
                + " ibm_handicap_agent iha LEFT JOIN ibm_ha_info ihi ON iha.IBM_HANDICAP_AGENT_ID_ = ihi.HANDICAP_AGENT_ID_"
                + " WHERE iha.APP_USER_ID_ =? AND iha.STATE_ =?";
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(appUserId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMapList(sql,parameterList);
    }
    /**
     * 逻辑删除盘口代理相关信息
     * @param handicapAgentId      盘口代理id
     */
    public void delHaInfo(String handicapAgentId, String desc) throws SQLException {
        String sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
                + " HANDICAP_AGENT_ID_ =? and STATE_ != ?";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(IbmStateEnum.DEL.name());
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(desc);
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.DEL.name());

        super.dao.execute(String.format(sqlFormat, "ibm_ha_follow_period"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_ha_notice"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_ha_set"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_ha_game_set"), parameterList);
        super.dao.execute(String.format(sqlFormat, "ibm_ha_info"), parameterList);

        sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
                + " IBM_HANDICAP_AGENT_ID_ =? and STATE_ != ?";
        super.dao.execute(String.format(sqlFormat, "ibm_handicap_agent"), parameterList);

    }

    /**
     * 获取详情信息
     * @param handicapAgentId   盘口代理id
     * @return 详情信息
     */
    public Map<String, Object> findItemInfo(String handicapAgentId) throws SQLException {
        String sql="SELECT iha.AGENT_ACCOUNT_,ihi.HANDICAP_URL_,ihi.HANDICAP_CAPTCHA_,iha.LANDED_TIME_LONG_,iha.HANDICAP_ID_"
                + " FROM ibm_handicap_agent iha LEFT JOIN ibm_handicap_item ihi ON iha.HANDICAP_ITEM_ID_=ihi.IBM_HANDICAP_ITEM_ID_"
                + " WHERE iha.IBM_HANDICAP_AGENT_ID_=? and iha.STATE_=?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql,parameterList);
    }

    /**
     * 获取主键信息
     * @param appUserId 游戏主键
     * @param handicapCode 盘口编码
     * @return 主键信息列表
     */
    public List<String> listId(String appUserId, String handicapCode) throws SQLException {
        String sql = "SELECT IBM_HANDICAP_AGENT_ID_ as key_ FROM `ibm_handicap_agent` "
                + " where HANDICAP_CODE_ = ? and APP_USER_ID_ = ? AND STATE_ != ?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(handicapCode);
        parameterList.add(appUserId);
        parameterList.add(IbmStateEnum.DEL.name());
        return super.findStringList(sql,parameterList);
    }
}
