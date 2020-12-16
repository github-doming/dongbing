package com.ibm.follow.connector.admin.manage3.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.service.IbmSysBetOddsService;
import com.ibm.follow.servlet.server.core.thread.SettleBetItemThread;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

/**
 * @Description: 盘口业务代码
 * @Author: Dongming
 * @Date: 2019-09-03 15:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmSysService extends BaseServicePlus {

    /**
     * 查找过期的用户Id
     * @param longTime
     * @return
     * @throws SQLException
     */
    public List<String> listUidsByExpireDataTime(long longTime) throws SQLException {
        String sql = "SELECT APP_USER_ID_ FROM `app_user` WHERE LOGIN_TIME_LONG_ < ?";
        List<Object> parameters = new ArrayList<>(1);
        parameters.add(longTime);
        return dao.findStringList("APP_USER_ID_",sql,parameters);
    }

    /**
     * 根据时间清理数据
     * @param tableNames
     * @param longTime
     * @return
     * @throws SQLException
     */
    public int cleanDataByTime(List<String> tableNames,long longTime) throws SQLException {
        int changeNum = 0;
        String sql;
        List<Object> parameters = new ArrayList<>(1);
        parameters.add(longTime);
        for (String tableName : tableNames){
            sql = "DELETE FROM %s where CREATE_TIME_LONG_ < ?";
            changeNum += super.dao.execute(String.format(sql,tableName),parameters);
        }
        return changeNum;
    }

    /**
     * 清理代理过期数据
     * @param tableNames
     * @param haIds
     * @return
     * @throws SQLException
     */
    public int cleanDataByHaId(List<String> tableNames,List<String> haIds) throws SQLException {
        int changeNum = 0;
        String sql;
        List<Object> parameters = new ArrayList<>(haIds.size());
        String spliStr = sqlParameter(haIds, parameters);
        for (String tableName : tableNames){
            if("ibm_handicap_agent".equals(tableName)){
                sql = "DELETE FROM %s where IBM_HANDICAP_AGENT_ID_ in("+spliStr;
                changeNum += super.dao.execute(String.format(sql,tableName),parameters);
                continue;
            }
            sql = "DELETE FROM %s where HANDICAP_AGENT_ID_ in("+spliStr;
            changeNum += super.dao.execute(String.format(sql,tableName),parameters);
        }
        return changeNum;
    }

    /**
     * 清理会员过期数据
     * @param tableNames
     * @param hmIds
     * @return
     * @throws SQLException
     */
    public int cleanDataByHmId(List<String> tableNames,List<String> hmIds) throws SQLException {
        int changeNum = 0;
        String sql;
        List<Object> parameters = new ArrayList<>(hmIds.size());
        String spliStr = sqlParameter(hmIds, parameters);
        for (String tableName : tableNames){
            if("ibm_handicap_member".equals(tableName)){
                sql = "DELETE FROM %s where IBM_HANDICAP_MEMBER_ID_ in("+spliStr;
                changeNum += super.dao.execute(String.format(sql,tableName),parameters);
                continue;
            }
            sql = "DELETE FROM %s where HANDICAP_MEMBER_ID_ in("+spliStr;
            changeNum += super.dao.execute(String.format(sql,tableName),parameters);
        }
        return changeNum;
    }

    private String sqlParameter(List<String> hmIds, List<Object> parameters) {
        StringBuilder sbStr = new StringBuilder();
        for(String hmId : hmIds){
            sbStr.append("?,");
            parameters.add(hmId);
        }
        sbStr.deleteCharAt(sbStr.lastIndexOf(",")).append(")");
        return sbStr.toString();
    }


    /**
     * 查找投注表相关信息
     * @param tables
     * @param lastDate
     * @return
     * @throws Exception
     */
    public  List<Map<String,Object>> findBetInfo(List<String> tables,Long lastDate) throws Exception {
        List<Object> paramList = new ArrayList<>();
        paramList.add(lastDate);
        String bestStr = " WHERE  CREATE_TIME_LONG_ >= ?";
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (String tableName : tables){
            Map<String,Object> tempMap = new HashMap<>();
            String sql = "SELECT COUNT(*) count FROM %s";
            String lastSql = "SELECT CREATE_TIME_ FROM %s ORDER BY CREATE_TIME_LONG_ ASC LIMIT 1";
            String dateSql = "SELECT LEFT(CREATE_TIME_,10) date FROM %s GROUP BY date ORDER BY CREATE_TIME_LONG_ ASC";
            sql = String.format(sql,tableName);
            lastSql = String.format(lastSql,tableName);
            dateSql = String.format(dateSql,tableName);
            tempMap.put("tableName",tableName);
            tempMap.put("total",dao.findString("count", sql, null));
            tempMap.put("abortTotal",dao.findString("count", sql+bestStr, paramList));
            String createTime = dao.findString("CREATE_TIME_",lastSql,null);
            tempMap.put("createTime",StringTool.isEmpty(createTime) ? "无数据" : createTime);
            List<String> dates = dao.findStringList("date",dateSql,null);
            tempMap.put("date",dates.isEmpty() ? "无数据":dates);

            listMap.add(tempMap);
        }
        return listMap;
    }

    /**
     * 查找过期用户
     * @param date
     * @return
     * @throws SQLException
     */
    public List<Map<String,Object>> findExpireUser(Long date) throws SQLException {
        String sql = "SELECT APP_USER_NAME_,NICKNAME_,APP_USER_TYPE_,UPDATE_TIME_ FROM `app_user` WHERE UPDATE_TIME_LONG_ <= ? ORDER BY CREATE_TIME_LONG_ ASC";
        List<Object> paramList = new ArrayList<>();
        paramList.add(date);
        return dao.findMapList(sql,paramList);
    }







}
