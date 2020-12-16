package com.ibm.follow.servlet.cloud.ibm_card_report.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import com.ibm.follow.servlet.cloud.ibm_card_report.entity.IbmCardReport;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBM_充值卡报表 服务类
 *
 * @author Robot
 */
public class IbmCardReportService extends BaseServicePlus {

    /**
     * 保存IBM_充值卡报表 对象数据
     *
     * @param entity IbmCardReport对象数据
     */
    public String save(IbmCardReport entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_card_report 的 IBM_CARD_REPORT_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_card_report set state_='DEL' where IBM_CARD_REPORT_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBM_CARD_REPORT_ID_主键id数组的数据
     *
     * @param idArray 要删除 ibm_card_report 的 IBM_CARD_REPORT_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "update ibm_card_report set state_='DEL' where IBM_CARD_REPORT_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除 ibm_card_report  的 IBM_CARD_REPORT_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_card_report where IBM_CARD_REPORT_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBM_CARD_REPORT_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_card_report 的 IBM_CARD_REPORT_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "delete from ibm_card_report where IBM_CARD_REPORT_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmCardReport实体信息
     *
     * @param entity IBM_充值卡报表 实体
     */
    public void update(IbmCardReport entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_card_report表主键查找 IBM_充值卡报表 实体
     *
     * @param id ibm_card_report 主键
     * @return IBM_充值卡报表 实体
     */
    public IbmCardReport find(String id) throws Exception {
        return (IbmCardReport) this.dao.find(IbmCardReport.class, id);

    }

    /**
     * 根据分类ID查找用户当天报表实体
     *
     * @param cardTreeId 分类ID
     * @param userId     用户ID
     * @return 实体
     */
    public IbmCardReport findByCardTreeId(String cardTreeId, String userId, String target) throws Exception {
        String sql = "SELECT * FROM `ibm_card_report` WHERE CARD_TREE_ID_ =? AND USER_ID_=? AND TARGET_=? AND REPORT_TIME_LONG_>=? AND STATE_!=? ";
        List<Object> parameters = new ArrayList<>(4);
        parameters.add(cardTreeId);
        parameters.add(userId);
        parameters.add(target);
        parameters.add(DateTool.getDayStart().getTime());
        parameters.add(IbmStateEnum.DEL.name());
        return (IbmCardReport) dao.findObject(IbmCardReport.class, sql, parameters);
    }

    /**
     * 根据分类ID和记录时间查找用户报表实体
     *
     * @param cardTreeId     分类ID
     * @param userId         用户ID
     * @param cardCreateTime 充值卡记录时间
     * @return 实体
     */
    public IbmCardReport findByCardTreeIdAndDate(String cardTreeId, String userId, String targer, Date cardCreateTime) throws Exception {
        String sql = "SELECT * FROM `ibm_card_report` WHERE CARD_TREE_ID_ =? AND USER_ID_=? AND TARGET_=? AND REPORT_TIME_LONG_ BETWEEN ? AND ?  AND STATE_!=? ";
        List<Object> parameters = new ArrayList<>(6);
        parameters.add(cardTreeId);
        parameters.add(userId);
        parameters.add(targer);
        parameters.add(DateTool.getDayStart(cardCreateTime).getTime());
        parameters.add(DateTool.getDayEnd(cardCreateTime).getTime());
        parameters.add(IbmStateEnum.DEL.name());
        return (IbmCardReport) dao.findObject(IbmCardReport.class, sql, parameters);
    }

    /**
     * 更新报表记录  激活数、总积分、总价值
     *
     * @param pk    主键
     * @param point 积分
     * @param price 价值
     */
    public void updateReportUseState(String pk, int point, long price) throws Exception {
        String sql = "UPDATE  ibm_card_report SET CARD_ACTIVATE_TOTAL_= CARD_ACTIVATE_TOTAL_ + 1,PRICE_TOTAL_T_ = PRICE_TOTAL_T_+?," +
                "POINT_TOTAL_ = POINT_TOTAL_+?,UPDATE_TIME_LONG_=? WHERE  IBM_CARD_REPORT_ID_= ? ";
        List<Object> parameters = new ArrayList<>(5);
        parameters.add(price);
        parameters.add(point);
        parameters.add(System.currentTimeMillis());
        parameters.add(pk);
        dao.execute(sql, parameters);

    }

    /**
     * 更新报表 总数
     *
     * @param pk 主键
     */
    public void updateReportEdition(String pk) throws Exception {
        String sql = "UPDATE  ibm_card_report SET CARD_TOTAL_= CARD_TOTAL_ + 1,UPDATE_TIME_LONG_=? WHERE  IBM_CARD_REPORT_ID_= ? ";
        List<Object> parameters = new ArrayList<>(3);
        parameters.add(System.currentTimeMillis());
        parameters.add(pk);
        dao.execute(sql, parameters);
    }

    /**
     * 更新所有数据 创建卡并激活
     * @param pk
     * @param point
     * @param price
     * @throws SQLException
     */
    public void updateAllState(String pk, int point, long price) throws SQLException {
        String sql = "UPDATE  ibm_card_report SET CARD_TOTAL_= CARD_TOTAL_ + 1,CARD_ACTIVATE_TOTAL_= CARD_ACTIVATE_TOTAL_ + 1,PRICE_TOTAL_T_ = PRICE_TOTAL_T_+?," +
                "POINT_TOTAL_ = POINT_TOTAL_+?,UPDATE_TIME_LONG_=? WHERE  IBM_CARD_REPORT_ID_= ? ";
        List<Object> parameters = new ArrayList<>(5);
        parameters.add(price);
        parameters.add(point);
        parameters.add(System.currentTimeMillis());
        parameters.add(pk);
        dao.execute(sql, parameters);
    }


    /**
     * 查询报表按照代理分类
     *
     * @param subAgentIds 直属下级代理Id
     * @param agentId     代理Id
     * @param cardTreeId  分类Id
     * @param timeStart   开始时间
     * @param timeEnd     结束时间
     * @param pageIndex   起始页
     * @param pageSize    页大小
     * @return data
     */
    public Map<String, Object> listReportByAgent(List<String> subAgentIds, String adminUserId, String agentId, String cardTreeId,
                                                 Long timeStart, Long timeEnd, int pageIndex, int pageSize) throws SQLException {
        String sql = "SELECT USER_NAME_,USER_ID_,CARD_TREE_ID_,SUM(CARD_TOTAL_) CARD_TOTAL_,SUM(CARD_ACTIVATE_TOTAL_) CARD_ACTIVATE_TOTAL_," +
                "SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_,SUM(POINT_TOTAL_) POINT_TOTAL_ FROM `ibm_card_report` WHERE STATE_ !=? AND TARGET_= ? ";
        String sqlCount = "SELECT count(*) from (";
        String sumSql = "SELECT SUM(PRICE_TOTAL_T_) totalPrice,SUM(POINT_TOTAL_) totalPoint FROM `ibm_card_report` WHERE STATE_ !=?  AND TARGET_= ? ";

        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(IbmTypeEnum.HIGHER_UP.name());
        StringBuilder sbSql = new StringBuilder();
        if (StringTool.notEmpty(cardTreeId)) {
            sbSql.append(" AND CARD_TREE_ID_ = ? ");
            parameters.add(cardTreeId);
        }

        if (StringTool.notEmpty(agentId)) {
            sbSql.append(" AND USER_ID_ = ? ");
            parameters.add(agentId);
        } else {
            sbSql.append(" AND USER_ID_ IN('',");
            if (ContainerTool.notEmpty(subAgentIds)) {
                for (String id : subAgentIds) {
                    sbSql.append("?,");
                    parameters.add(id);
                }
            }
            sbSql.deleteCharAt(sbSql.length() - 1);
            sbSql.append(")");
        }

        if (timeStart != null) {
            sbSql.append(" AND REPORT_TIME_LONG_ BETWEEN ? AND ? ");
            parameters.add(timeStart);
            parameters.add(timeEnd);
        }

        sumSql += sbSql.toString();
        sbSql.append("GROUP BY USER_ID_ ORDER BY REPORT_TIME_LONG_");
        sql += sbSql.toString();
        sqlCount = sqlCount + sql + ") AS t  ";

        PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
        Map<String, Object> sunMap = dao.findMap(sumSql, parameters);
        List<Map<String, Object>> listMap = basePage.getList();

        // 查询针对自己的报表信息 （自己开的卡（包含特殊开卡））
        Map<String, Object> selfData = selfReport(adminUserId, cardTreeId, IbmTypeEnum.SELF.name(), timeStart, timeEnd);
        // 查询交付给上级的报表合计信息
        Map<String, Object> upData = selfReport(adminUserId, cardTreeId, IbmTypeEnum.HIGHER_UP.name(), timeStart, timeEnd);

        int activateCardTotal = 0;
        double turnInPrice = 0;
        double totalPrice = NumberTool.doubleT(sunMap.get("totalPrice"));
        if (ContainerTool.notEmpty(listMap) || ContainerTool.notEmpty(selfData)) {
            if (ContainerTool.notEmpty(selfData)) {
                listMap.add(0, selfData);
            }
            turnInPrice = NumberTool.doubleT(upData.get("PRICE_TOTAL_T_"));
            totalPrice += NumberTool.doubleT(selfData.get("PRICE_TOTAL_T_"));

            IbmAdminAppUserService userService = new IbmAdminAppUserService();
            for (Map<String, Object> baseMap : listMap) {
                baseMap.put("PRICE_TOTAL_T_", NumberTool.doubleT(baseMap.get("PRICE_TOTAL_T_")));
                activateCardTotal += NumberTool.getInteger(baseMap.get("CARD_ACTIVATE_TOTAL_"));
                String addUserId = baseMap.get("USER_ID_").toString();
                String nickName ;
                if(IbmTypeEnum.ADMIN.name().equals(addUserId)){
                    nickName = "ADMIN";
                }else{
                    nickName = userService.listShow(addUserId).get("NICKNAME_").toString();
                }
                baseMap.put("NICKNAME_",nickName);
            }
        }
        Map<String, Object> data = new HashMap<>(8);
        data.put("rows", listMap);
        data.put("index", pageIndex);
        data.put("total", basePage.getTotalCount());
        data.put("turnInPrice", turnInPrice);
        data.put("activateCardTotal", activateCardTotal);
        data.put("gainTotal", totalPrice - turnInPrice);
        data.put("totalPrice", totalPrice);
        data.put("totalPoint", NumberTool.getInteger(sunMap.get("totalPoint")) + NumberTool.getInteger(selfData.get("POINT_TOTAL_")));
        return data;
    }

    /**
     * 查询代理报表详情项
     *
     * @param agentId    代理Id
     * @param cardTreeId 分类Id
     * @param target     目标 - 查针对自己的 还是针对上级的
     * @param timeStart  开始时间
     * @param timeEnd    结束时间
     * @return data
     */
    public Map<String, Object> listAgentReportItem(String agentId, String cardTreeId, String target, Long timeStart, Long timeEnd) throws SQLException {
        String sql = "SELECT CARD_TREE_NAME_,SUM(CARD_TOTAL_) CARD_TOTAL_,SUM(CARD_ACTIVATE_TOTAL_) CARD_ACTIVATE_TOTAL_,SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_" +
                ",SUM(POINT_TOTAL_) POINT_TOTAL_ FROM `ibm_card_report` " +
                "WHERE STATE_ !=? AND  USER_ID_ = ? AND TARGET_= ? ";

        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(agentId);
        parameters.add(target);

        StringBuilder sbSql = new StringBuilder();
        if (StringTool.notEmpty(cardTreeId)) {
            sbSql.append(" AND CARD_TREE_ID_ = ? ");
            parameters.add(cardTreeId);
        }
        if (timeStart != null) {
            sbSql.append(" AND REPORT_TIME_LONG_ BETWEEN ? AND ? ");
            parameters.add(timeStart);
            parameters.add(timeEnd);
        }
        sbSql.append(" GROUP BY CARD_TREE_ID_ ORDER BY REPORT_TIME_LONG_");
        sql += sbSql.toString();
        List<Map<String, Object>> listMap = dao.findMapList(sql, parameters);

        int activateCardTotal = 0;
        int pointTotal = 0;
        double priceTotal = 0;
        for (Map<String, Object> baseMap : listMap) {
            baseMap.put("PRICE_TOTAL_T_", NumberTool.doubleT(baseMap.get("PRICE_TOTAL_T_")));
            activateCardTotal += NumberTool.getInteger(baseMap.get("CARD_ACTIVATE_TOTAL_"));
            pointTotal += NumberTool.getInteger(baseMap.get("POINT_TOTAL_"));
            priceTotal += NumberTool.getDouble(baseMap.get("PRICE_TOTAL_T_"));
        }
        Map<String, Object> data = new HashMap<>(8);
        data.put("rows", listMap);
        data.put("index", 1);
        data.put("total", listMap.size());
        data.put("turnInPrice", "-");
        data.put("activateCardTotal", activateCardTotal);
        data.put("gainTotal", "-");
        data.put("totalPrice", priceTotal);
        data.put("totalPoint", pointTotal);

        return data;
    }

    /**
     * 查询自己的报表信息合计
     *
     * @param userId     用户Id
     * @param cardTreeId 分类Id
     * @param target     目标数据
     * @param timeStart  开始时间
     * @param timeEnd    结束时间
     */
    public Map<String, Object> selfReport(String userId, String cardTreeId, String target, Long timeStart, Long timeEnd) throws SQLException {
        String sql = "SELECT CARD_TREE_NAME_,USER_NAME_,USER_ID_,CARD_TREE_ID_,SUM(CARD_TOTAL_) CARD_TOTAL_,SUM(CARD_ACTIVATE_TOTAL_) CARD_ACTIVATE_TOTAL_," +
                "SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_,SUM(POINT_TOTAL_) POINT_TOTAL_ FROM `ibm_card_report` WHERE STATE_ !=? AND TARGET_= ? AND USER_ID_ = ? ";
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(target);
        parameters.add(userId);
        if (StringTool.notEmpty(cardTreeId)) {
            sql += " AND CARD_TREE_ID_ = ? ";
            parameters.add(cardTreeId);
        }
        if (timeStart != null) {
            sql += " AND REPORT_TIME_LONG_ BETWEEN ? AND ? ";
            parameters.add(timeStart);
            parameters.add(timeEnd);
        }
        Map<String, Object> dataMap = dao.findMap(sql, parameters);
        if (dataMap.get("CARD_TREE_ID_") == null) {
            return new HashMap<>(1);
        }
        return dataMap;
    }

    /**
     * 查询自己的报表信息合计
     *
     * @param userId     用户Id
     * @param cardTreeId 分类Id
     * @param target     目标数据
     * @param timeStart  开始时间
     * @param timeEnd    结束时间
     */
    public List<Map<String, Object>> selfReportGroup(String userId, String cardTreeId, String target, Long timeStart, Long timeEnd) throws SQLException {
        String sql = "SELECT CARD_TREE_NAME_,USER_NAME_,USER_ID_,CARD_TREE_ID_,SUM(CARD_TOTAL_) CARD_TOTAL_,SUM(CARD_ACTIVATE_TOTAL_) CARD_ACTIVATE_TOTAL_," +
                "SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_,SUM(POINT_TOTAL_) POINT_TOTAL_ FROM `ibm_card_report` WHERE STATE_ !=? AND TARGET_= ? AND USER_ID_ = ? ";
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(target);
        parameters.add(userId);
        if (StringTool.notEmpty(cardTreeId)) {
            sql += " AND CARD_TREE_ID_ = ? ";
            parameters.add(cardTreeId);
        }
        if (timeStart != null) {
            sql += " AND REPORT_TIME_LONG_ BETWEEN ? AND ? ";
            parameters.add(timeStart);
            parameters.add(timeEnd);
        }
        sql += " GROUP BY CARD_TREE_ID_ ";
        List<Map<String, Object>> dataList = dao.findMapList(sql, parameters);
        if (ContainerTool.isEmpty(dataList) || dataList.size() == 0) {
            return new ArrayList<>(1);
        }
        return dataList;
    }


    /**
     * 查询报表按照卡种分类
     *
     * @param userId 用户Id
     * @return data
     */
    public Map<String, Object> listReportByCardTree(String userId, List<String> subAgentIds) throws SQLException {

        String sumSql = "SELECT SUM(CARD_TOTAL_) totalCard,SUM(CARD_ACTIVATE_TOTAL_) CARD_ACTIVATE_TOTAL_,SUM(PRICE_TOTAL_T_) totalPrice,SUM(POINT_TOTAL_) totalPoint FROM `ibm_card_report` " +
                "WHERE STATE_ !=? AND TARGET_= ?  ";
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(IbmTypeEnum.HIGHER_UP.name());

        StringBuilder sbSql = new StringBuilder();
        if (ContainerTool.notEmpty(subAgentIds)) {
            sbSql.append(" AND USER_ID_ IN('',");
            if (ContainerTool.notEmpty(subAgentIds)) {
                for (String id : subAgentIds) {
                    sbSql.append("?,");
                    parameters.add(id);
                }
            }
            sbSql.deleteCharAt(sbSql.length() - 1);
            sbSql.append(")");
        }

        sumSql += sbSql.toString();
        Map<String, Object> sunMap = dao.findMap(sumSql, parameters);

        // 查询针对自己的报表信息 （自己开的卡（包含特殊开卡））
        Map<String, Object> selfData = selfReport(userId, "", IbmTypeEnum.SELF.name(), null, null);
        // 查询交付给上级的报表合计信息
        List<Map<String, Object>> upData = selfReportGroup(userId, "", IbmTypeEnum.HIGHER_UP.name(), null, null);

        int activateCardTotal = 0;
        double turnInPrice = 0;
        double totalPrice = NumberTool.doubleT(sunMap.get("totalPrice")) + NumberTool.doubleT(selfData.get("PRICE_TOTAL_T_"));
        for (Map<String, Object> baseMap : upData) {
            baseMap.put("PRICE_TOTAL_T_", NumberTool.doubleT(baseMap.get("PRICE_TOTAL_T_")));
            activateCardTotal += NumberTool.getInteger(baseMap.get("CARD_ACTIVATE_TOTAL_"));

            turnInPrice += NumberTool.getDouble(baseMap.get("PRICE_TOTAL_T_"));
        }

        Map<String, Object> data = new HashMap<>(8);
        data.put("rows", upData);
        data.put("turnInPrice", turnInPrice);
        data.put("activateCardTotal", activateCardTotal);
        data.put("gainTotal", totalPrice - turnInPrice);
        data.put("totalPrice", totalPrice);
        data.put("totalPoint", NumberTool.getInteger(sunMap.get("totalPoint")) + NumberTool.getInteger(selfData.get("POINT_TOTAL_")));
        return data;

    }

    /**
     * 查询某一卡种的报表情况
     *
     * @param subAgentIds 下级代理Id 包含查询者本身
     * @param userId      代理Id
     * @param cardTreeId  分类Id
     * @return data
     */
    public Map<String, Object> listCardTreeReportItem(List<String> subAgentIds, String userId, String cardTreeId, int pageIndex, int pageSize) throws SQLException {
        String sql = "SELECT USER_NAME_,USER_ID_,CARD_TREE_NAME_,SUM(CARD_TOTAL_) CARD_TOTAL_,SUM(CARD_ACTIVATE_TOTAL_) CARD_ACTIVATE_TOTAL_," +
                "SUM(POINT_TOTAL_) POINT_TOTAL_,SUM(PRICE_TOTAL_T_) PRICE_TOTAL_T_ FROM `ibm_card_report` where STATE_ !=? AND TARGET_= ?  AND CARD_TREE_ID_ = ? ";
        String sumSql = "SELECT SUM(PRICE_TOTAL_T_) totalPrice,SUM(POINT_TOTAL_) totalPoint FROM `ibm_card_report` WHERE STATE_ !=? AND TARGET_= ?  AND CARD_TREE_ID_ = ? ";
        String sqlCount = "SELECT count(*) from (";
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(IbmTypeEnum.HIGHER_UP.name());
        parameters.add(cardTreeId);
        StringBuilder sbSql = new StringBuilder();

        if (ContainerTool.notEmpty(subAgentIds)) {
            sbSql.append(" AND USER_ID_ IN(");
            for (String id : subAgentIds) {
                sbSql.append("?,");
                parameters.add(id);
            }
            sbSql.replace(sbSql.lastIndexOf(","), sbSql.length(), ")");
        }

        sumSql += sbSql.toString();

        sbSql.append(" GROUP BY USER_ID_ ORDER BY REPORT_TIME_LONG_");
        sql += sbSql.toString();
        sqlCount = sqlCount + sql + ") AS t  ";
        PageCoreBean<Map<String, Object>> basePage = dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
        Map<String, Object> sunMap = dao.findMap(sumSql, parameters);

        // 查询针对自己的报表信息 （自己开的卡（包含特殊开卡））
        Map<String, Object> selfData = selfReport(userId, cardTreeId, IbmTypeEnum.SELF.name(), null,null);

        Map<String, Object> data = new HashMap<>(5);
        List<Map<String, Object>> listMap = basePage.getList();
        if (ContainerTool.notEmpty(listMap) || ContainerTool.notEmpty(selfData)) {
            if (ContainerTool.notEmpty(selfData)) {
                listMap.add(0, selfData);
            }
            IbmAdminAppUserService userService = new IbmAdminAppUserService();
            for (Map<String, Object> map : listMap) {
                map.put("PRICE_TOTAL_T_", NumberTool.doubleT(map.get("PRICE_TOTAL_T_")));
                String addUserId = map.get("USER_ID_").toString();
                String nickName ;
                if(IbmTypeEnum.ADMIN.name().equals(addUserId)){
                    nickName = "ADMIN";
                }else{
                    nickName = userService.listShow(addUserId).get("NICKNAME_").toString();
                }
                map.put("NICKNAME_",nickName);
            }
        }
        double totalPrice = NumberTool.doubleT(sunMap.get("totalPrice"));
        int totalPoint = NumberTool.getInteger(sunMap.get("totalPoint"));
        data.put("rows", listMap);
        data.put("index", pageIndex);
        data.put("total", basePage.getTotalCount());
        data.put("totalPrice", totalPrice);
        data.put("totalPoint", totalPoint);
        return data;
    }

}
