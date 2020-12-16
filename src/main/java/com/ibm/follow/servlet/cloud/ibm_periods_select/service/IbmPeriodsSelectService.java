package com.ibm.follow.servlet.cloud.ibm_periods_select.service;

import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_periods_select.entity.IbmPeriodsSelect;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: ibm_periods_select
 * @Author: lxl
 * @Date: 2019-10-11 11:42
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
public class IbmPeriodsSelectService  extends BaseService {
    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 保存{table_class}对象数据
     * @param entity IbmPeriodsSelect对象数据
     * @return String
     */
    public String save(IbmPeriodsSelect entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     * @param id 要删除ibm_periods_select的 IBM_PERIODS_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_periods_select set STATE_='DEL' where IBM_PERIODS_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }
    /**
     * 查询数据
     * @return 所有数据
     */
    public List<Map<String, Object>> findAll() throws SQLException {
        String sql = "select BETWEEN_TIME_ONE,BETWEEN_TIME_TWO from ibm_periods_select where STATE_ != ? order by UPDATE_TIME_ DESC";
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        return dao.findMapList(sql,parameters);
    }

    /**
     * 查询初始化数据
     * @return 所有数据
     */
    public List<Map<String, Object>> findInitAll() throws SQLException {
        String sql = "select * from ibm_periods_select where STATE_ != ? order by BETWEEN_TIME_ONE desc";
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        return dao.findMapList(sql,parameters);
    }


    /**
     * 根据时间查询期数判断条件
     * @param findTime IbmPeriodsSelect对象数据
     * @return 所有根据条件查询的数据
     */
    public List<Map<String, Object>> findPeriodsRule(Long findTime) throws SQLException {
        String sql = "SELECT IBM_PERIODS_ID_ FROM ibm_periods_select WHERE BETWEEN_TIME_ONE <= ? AND " +
                "BETWEEN_TIME_TWO >= ? OR TIME_PERIODS > ? AND STATE_ != ? ";
        List<Object> parameters = new ArrayList<>();
        parameters.add(findTime);
        parameters.add(findTime);
        parameters.add(findTime);
        parameters.add(IbmStateEnum.DEL.name());
        return dao.findMapList(sql,parameters);
    }

    /**
     * 根据时间查询期数
     * @param findTime IbmPeriodsSelect对象数据
     * @return 所有根据条件查询的数据
     */
    public List<Map<String, Object>> findPeriods(Long findTime) throws SQLException {
        String sql = "select BETWEEN_DAYS,BETWEEN_TIME_ONE,BETWEEN_TIME_TWO from ibm_periods_select where" +
                " BETWEEN_TIME_TWO < ? and STATE_ != ? order by BETWEEN_TIME_ONE DESC";
        List<Object> parameters = new ArrayList<>();
        parameters.add(findTime);
        parameters.add(IbmStateEnum.DEL.name());
        return dao.findMapList(sql,parameters);
    }

    /**
     * 根据时间查询期数
     * @return 所有根据条件查询的数据
     */
    public List<Map<String, Object>> findIntervalTime() throws SQLException {
        String sql = "SELECT BETWEEN_TIME_ONE,BETWEEN_TIME_TWO,BETWEEN_DAYS FROM ibm_periods_select WHERE STATE_ != ? ";
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.DEL.name());
        return dao.findMapList(sql,parameters);
    }
}
