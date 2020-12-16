package com.ibm.follow.servlet.cloud.ibm_card.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_card.entity.IbmCard;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBM_充值卡 服务类
 *
 * @author Robot
 */
public class IbmCardService extends BaseServicePlus {

    /**
     * 保存IBM_充值卡 对象数据
     *
     * @param entity IbmCard对象数据
     */
    public String save(IbmCard entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_card 的 IBM_CARD_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_card set state_='DEL' where IBM_CARD_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBM_CARD_ID_主键id数组的数据
     *
     * @param idArray 要删除 ibm_card 的 IBM_CARD_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "update ibm_card set state_='DEL' where IBM_CARD_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除 ibm_card  的 IBM_CARD_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_card where IBM_CARD_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBM_CARD_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_card 的 IBM_CARD_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "delete from ibm_card where IBM_CARD_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmCard实体信息
     *
     * @param entity IBM_充值卡 实体
     */
    public void update(IbmCard entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_card表主键查找 IBM_充值卡 实体
     *
     * @param id ibm_card 主键
     * @return IBM_充值卡 实体
     */
    public IbmCard find(String id) throws Exception {
        return (IbmCard) this.dao.find(IbmCard.class, id);

    }

    /**
     * 查询充值卡列表
     *
     * @param cardId    卡主键
     * @param userId    用户Id
     * @param cardName  卡种名称
     * @param cardState 卡状态
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param pageIndex 起始页
     * @param pageSize  页大小
     * @return page
     */
    public PageCoreBean<Map<String, Object>> listCard(String cardId, String userId, String cardName, String cardState,
                                                      Long startTime, Long endTime, int pageIndex, int pageSize) throws SQLException {
        String sqlCount = "SELECT count(*) FROM( ";
        StringBuilder sql = new StringBuilder("SELECT IBM_CARD_ID_,CARD_TREE_ID_,CARD_PASSWORD_,CARD_TREE_NAME_,CARD_TREE_POINT_,USE_USER_ID_,USER_NAME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,CREATER_NAME_,OWNER_NAME_,DESC_,STATE_ " +
                "FROM `ibm_card` where STATE_!=? AND (CREATE_USER_ID_ =? OR OWNE_USER_ID_ = ? )");
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(userId);
        parameters.add(userId);
        if (StringTool.notEmpty(cardId)) {
            sql.append(" AND IBM_CARD_ID_ = ?");
            parameters.add(cardId);
        }
        if (StringTool.notEmpty(cardName)) {
            sql.append(" AND CARD_TREE_NAME_ = ?");
            parameters.add(cardName);
        }
        if (StringTool.notEmpty(cardState)) {
            sql.append(" AND STATE_ = ?");
            parameters.add(cardState);
        }
        if (startTime != null) {
            sql.append(" AND CREATE_TIME_LONG_ BETWEEN ? AND ? ");
            parameters.add(startTime);
            parameters.add(endTime);
        }
        sql.append(" ORDER BY CREATE_TIME_LONG_ DESC ");
        sqlCount = sqlCount + sql + ") AS t  ";
        return dao.page(sql.toString(), parameters, pageIndex, pageSize, sqlCount, parameters);
    }


    /**
     * 查询特殊开卡的报表
     *
     * @param adminUserId 管理员ID
     * @param cardTreeId  分类Id
     * @param agentId     代理Id
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param pageIndex   起始页
     * @param pageSize    页大小
     * @return 报表
     */
    public Map<String, Object> listSpecialCardReport(String adminUserId, String cardTreeId, String agentId, Long startTime, Long endTime, Integer pageIndex, Integer pageSize) throws SQLException {

        String sql = "SELECT OWNE_USER_ID_,OWNER_NAME_,CARD_TREE_ID_,CARD_TREE_NAME_,0 totalPoint,0 totalPrice FROM `ibm_card` WHERE  (CREATE_USER_ID_=? OR OWNE_USER_ID_ =?) AND  CREATE_USER_ID_!= OWNE_USER_ID_ ";
        String sqlCount = "SELECT count(*) from (";
        String sqlUse = "SELECT OWNE_USER_ID_,OWNER_NAME_,CARD_TREE_ID_,CARD_TREE_NAME_,SUM(CARD_TREE_POINT_) totalPoint,sum(CARD_PRICE_T_) totalPrice " +
                "FROM `ibm_card` WHERE  STATE_='ACTIVATE' AND (CREATE_USER_ID_=? OR OWNE_USER_ID_ =?) AND  CREATE_USER_ID_!= OWNE_USER_ID_ ";
        List<Object> parameters = new ArrayList<>();
        parameters.add(adminUserId);
        parameters.add(adminUserId);
        StringBuilder sbSql = new StringBuilder();
        if (StringTool.notEmpty(cardTreeId)) {
            sbSql.append(" AND CARD_TREE_ID_=? ");
            parameters.add(cardTreeId);
        }

        if (StringTool.notEmpty(agentId)) {
            sbSql.append(" AND OWNE_USER_ID_=? ");
            parameters.add(agentId);
        }

        if (startTime != null) {
            sbSql.append(" AND CREATE_TIME_LONG_ BETWEEN ? AND ? ");
            parameters.add(startTime);
            parameters.add(endTime);
        }
        Map<String, Object> sunMap = dao.findMap(sqlUse + sbSql.toString(), parameters);

        sbSql.append(" GROUP BY OWNE_USER_ID_,CARD_TREE_ID_ ORDER BY OWNER_NAME_,CREATE_TIME_LONG_");
        sql += sbSql.toString();
        sqlUse += sbSql.toString();
        sqlCount = sqlCount + sql + ") AS t  ";
        PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
        List<Map<String, Object>> useList = dao.findMapList(sqlUse, parameters);
        for (Map<String, Object> baseMap : basePage.getList()) {
            for (Map<String, Object> useMap : useList) {
                if (baseMap.get("OWNE_USER_ID_").equals(useMap.get("OWNE_USER_ID_")) && baseMap.get("CARD_TREE_ID_").equals(useMap.get("CARD_TREE_ID_"))) {
                    baseMap.put("totalPoint", useMap.get("totalPoint"));
                    baseMap.put("totalPrice", NumberTool.doubleT(useMap.get("totalPrice")));
                }
            }
        }
        Map<String, Object> data = new HashMap<>(3);
        data.put("rows", basePage.getList());
        data.put("index", pageIndex);
        data.put("total", basePage.getTotalCount());
        data.put("totalPrice", NumberTool.doubleT(sunMap.get("totalPrice")));
        data.put("totalPoint", NumberTool.getInteger(sunMap.get("totalPoint"), 0));

        return data;
    }

    /**
     * 根据密码查询卡类实体
     *
     * @param cardPwd 卡密码
     * @return 实体
     */
    public IbmCard findEntityByCardPwd(String cardPwd) throws Exception {
        String sql = "SELECT * FROM `ibm_card` WHERE CARD_PASSWORD_ =? AND STATE_!=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(cardPwd);
        parameters.add(IbmStateEnum.DEL.name());
        return (IbmCard) dao.findObject(IbmCard.class, sql, parameters);

    }

    /**
     *  激活充值卡
     * @param pk 主键
     * @param userId 使用用户
     * @param userName 使用人
     * @param nowTime 时间
     */
    public int useCard(String pk, String userId, String userName, Date nowTime) throws SQLException {
        String sql = "UPDATE ibm_card SET  USE_USER_ID_=?, USER_NAME_= ?, UPDATE_TIME_=?, UPDATE_TIME_LONG_=?,STATE_=? WHERE IBM_CARD_ID_=? ";
        List<Object> parameters = new ArrayList<>();
        parameters.add(userId);
        parameters.add(userName);
        parameters.add(nowTime);
        parameters.add(System.currentTimeMillis());
        parameters.add(IbmStateEnum.ACTIVATE.name());
        parameters.add(pk);
        return super.execute(sql,parameters);
    }

}
