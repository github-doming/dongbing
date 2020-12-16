package com.ibm.follow.servlet.cloud.ibm_client_ha.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_client_ha.entity.IbmClientHa;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmClientHaService extends BaseServicePlus {

    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 保存{ay_table_class}对象数据
     *
     * @param entity IbmClientHa对象数据
     */
    public String save(IbmClientHa entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibm_client_ha的 IBM_CLIENT_HA_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibm_client_ha set state_='DEL' where IBM_CLIENT_HA_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBM_CLIENT_HA_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_client_ha的 IBM_CLIENT_HA_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "update ibm_client_ha set state_='DEL' where IBM_CLIENT_HA_ID_ in(" + stringBuilder.toString()
                    + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除ibm_client_ha的 IBM_CLIENT_HA_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibm_client_ha where IBM_CLIENT_HA_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBM_CLIENT_HA_ID_主键id数组的数据
     *
     * @param idArray 要删除ibm_client_ha的 IBM_CLIENT_HA_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "delete from ibm_client_ha where IBM_CLIENT_HA_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmClientHa实体信息
     *
     * @param entity IbmClientHa实体
     */
    public void update(IbmClientHa entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibm_client_ha表主键查找IbmClientHa实体
     *
     * @param id ibm_client_ha 主键
     * @return IbmClientHa实体
     */
    public IbmClientHa find(String id) throws Exception {
        return (IbmClientHa) this.dao.find(IbmClientHa.class, id);

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
        String sqlCount = "SELECT count(*) FROM ibm_client_ha where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_client_ha  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_client_ha  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
        }
        return dao.page(sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 获取分页IbmClientHa数据
     *
     * @param sortFieldName 排序字段名
     * @param sortOrderName 排序顺序
     * @param pageIndex     页面索引
     * @param pageSize      页面大小
     * @return 分页IbmClientHa数据
     */
    public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
            throws Exception {
        String sqlCount = "SELECT count(*) FROM ibm_client_ha where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibm_client_ha  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibm_client_ha  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
        }
        return dao.page(IbmClientHa.class, sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 按照更新顺序查询所有可用Map信息
     *
     * @return 可用Map信息
     */
    public List<Map<String, Object>> findAll() throws Exception {
        String sql = "SELECT * FROM ibm_client_ha  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findMapList(sql, null);
    }

    /**
     * 按照更新顺序查询所有可用IbmClientHa数据信息
     *
     * @return 可用<IbmClientHa>数据信息
     */
    public List findObjectAll() throws Exception {
        String sql = "SELECT * FROM ibm_client_ha  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findObjectList(IbmClientHa.class, sql);
    }

    /**
     * 保存客户端盘口会员
     *
     * @param clientId        客户端主键
     * @param handicapAgentId 盘口代理主键
     * @param existHaId       存在盘口代理主键
     * @param clientCode      客户端编码
     * @param handicapCode    盘口编码
     * @return 客户端盘口会员id
     */
    public String save(String clientId, String handicapAgentId, String existHaId, String clientCode,
                       String handicapCode) throws Exception {
        IbmClientHa clientHa = new IbmClientHa();
        clientHa.setHandicapAgentId(handicapAgentId);
        clientHa.setExistHaId(existHaId);
        clientHa.setClientId(clientId);
        clientHa.setClientCode(clientCode);
        clientHa.setHandicapCode(handicapCode);
        clientHa.setCreateTime(new Date());
        clientHa.setCreateTimeLong(System.currentTimeMillis());
        clientHa.setUpdateTimeLong(System.currentTimeMillis());
        clientHa.setState(IbmStateEnum.OPEN.name());
        return this.save(clientHa);
    }

    /**
     * 通过盘口代理主键查询客户端已存在盘口代理主键
     *
     * @param handicapAgentId 盘口代理id
     * @return 客户端已存在盘口代理
     */
    public Map<String, Object> findExistHaId(String handicapAgentId) throws SQLException {
        String sql = "select EXIST_HA_ID_,CLIENT_CODE_ from ibm_client_ha where HANDICAP_AGENT_ID_= ? and STATE_ = ? ";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql, parameterList);
    }

    /**
     * 通过盘口代理id修改查询并关闭已存在盘口代理信息
     *
     * @param handicapAgentId 盘口代理id
     * @param desc            更新描述
     */
    public void updateByHaId(String handicapAgentId, Date nowTime, String desc) throws SQLException {
        String sql = "update ibm_client_ha set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=?,DESC_ = ? "
                + " where HANDICAP_AGENT_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(6);
        parameterList.add(nowTime);
        parameterList.add(nowTime.getTime());
        parameterList.add(IbmStateEnum.CLOSE.name());
        parameterList.add(desc);
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.OPEN.name());
        super.dao.execute(sql, parameterList);
    }

    /**
     * 批量修改
     *
     * @param handicapAgentIds 盘口代理ids
     */
    public void updateByHaIds(List<String> handicapAgentIds) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("update ibm_client_ha set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,STATE_ = ?,DESC_ = ?"
                + " where STATE_=? and HANDICAP_AGENT_ID_ in(");
        List<Object> parameterList = new ArrayList<>();
        parameterList.add(new Date());
        parameterList.add(System.currentTimeMillis());
        parameterList.add(IbmStateEnum.CLOSE.name());
        parameterList.add("切换客户机登出");
        parameterList.add(IbmStateEnum.OPEN.name());
        for (String handicapAgentId : handicapAgentIds) {
            sql.append("?,");
            parameterList.add(handicapAgentId);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        super.dao.execute(sql.toString(), parameterList);
    }

    /**
     * 通过盘口代理id获取已存在代理信息
     *
     * @param handicapAgentId 盘口代理
     * @return 已存在代理信息
     */
    public Map<String, Object> findByHaId(String handicapAgentId) throws SQLException {
        String sql = "select CLIENT_ID_,CLIENT_CODE_,HANDICAP_CODE_ from ibm_client_ha where HANDICAP_AGENT_ID_=? and STATE_=? limit 1";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(handicapAgentId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findMap(sql, parameterList);
    }

    /**
     * 获取存在代理ID列表
     *
     * @param clientId     客户端主键
     * @param handicapCode 盘口编码
     * @return 存在代理ID列表
     */
    public List<String> listExitsId(String clientId, String handicapCode) throws SQLException {
        String sql = "SELECT ich.EXIST_HA_ID_ FROM ibm_client_ha ich WHERE ich.CLIENT_ID_=? AND ich.HANDICAP_CODE_=? AND ich.STATE_=?";
        List<Object> parameterList = new ArrayList<>(3);
        parameterList.add(clientId);
        parameterList.add(handicapCode);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("EXIST_HA_ID_", sql, parameterList);
    }

    /**
     * 心跳检测登出
     *
     * @param existHaId 存在代理id
     * @param nowTime   更新时间
     * @return 盘口代理主键
     */
    public String logout2Heartbeat(String existHaId, Date nowTime) throws SQLException {
        String sql = "select HANDICAP_AGENT_ID_ from ibm_client_ha where "
                + " EXIST_HA_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(existHaId);
        parameterList.add(IbmStateEnum.OPEN.name());
        String handicapAgentId = super.dao.findString("HANDICAP_AGENT_ID_", sql, parameterList);
        if (ContainerTool.isEmpty(handicapAgentId)) {
            return null;
        }
        updateByHaId(handicapAgentId, nowTime, "心跳检测-中心端存在客户端不存在的数据");
        return handicapAgentId;
    }

    /**
     * 获取盘口代理id
     *
     * @param existHaId 已存在盘口代理id
     * @return
     */
    public String findHaId(String existHaId) throws SQLException {
        String sql = "select HANDICAP_AGENT_ID_ from ibm_client_ha where "
                + " EXIST_HA_ID_ = ? and STATE_ = ?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(existHaId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findString("HANDICAP_AGENT_ID_", sql, parameterList);
    }

    /**
     * 获取盘口代理ids
     *
     * @param clientId 中心端客户端主键
     * @return
     */
    public List<String> listHaIds(Object clientId) throws SQLException {
        String sql = "select HANDICAP_AGENT_ID_ from ibm_client_ha where CLIENT_ID_=? and STATE_=?";
        List<Object> parameterList = new ArrayList<>(2);
        parameterList.add(clientId);
        parameterList.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("HANDICAP_AGENT_ID_", sql, parameterList);
    }

    /**
     * 获取代理使用中的客户端ids
     *
     * @return
     */
    public List<String> findUsingClientIds() throws SQLException {
        String sql = "select CLIENT_ID_ from ibm_client_ha where STATE_=? GROUP BY CLIENT_ID_";
        List<Object> parameters = new ArrayList<>();
        parameters.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("CLIENT_ID_", sql, parameters);
    }

    /**
     * 获取需要清除的盘口代理ids
     *
     * @param expiredHaClientIds 客户端ids
     * @return
     */
    public List<String> findHaIdByClientIds(Set<Object> expiredHaClientIds) throws SQLException {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        sql.append("select HANDICAP_AGENT_ID_ from ibm_client_ha where STATE_=? and CLIENT_ID_ in(");
        parameters.add(IbmStateEnum.OPEN.name());
        for (Object clientId : expiredHaClientIds) {
            sql.append("?,");
            parameters.add(clientId);
        }
        sql.replace(sql.length() - 1, sql.length(), ")");
        return super.dao.findStringList("HANDICAP_AGENT_ID_", sql.toString(), parameters);
    }

    /**
     * 获取盘口代理ids
     *
     * @param clientCode
     * @param handicapCode
     * @return
     */
    public List<String> findHaIds(Object clientCode, String handicapCode) throws SQLException {
        String sql = "select HANDICAP_AGENT_ID_ from ibm_client_ha where CLIENT_CODE_=? and HANDICAP_CODE_=? and STATE_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(clientCode);
        parameters.add(handicapCode);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findStringList("HANDICAP_AGENT_ID_",sql, parameters);
    }

    /**
     * 获取盘口代理ids
     *
     * @param clientId
     * @return
     */
    public List<String> findHaIds(Object clientId) throws SQLException {
        String sql = "select HANDICAP_AGENT_ID_ from ibm_client_ha where CLIENT_CODE_=? and STATE_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(clientId);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.dao.findStringList("HANDICAP_AGENT_ID_", sql, parameters);
    }

    /**
     * 批量新增
     *
     * @param haInfos    代理信息
     * @param clientId   客户端id
     * @param clientCode 客户端编码
     * @return
     */
    public List<String> save(JSONArray haInfos, String clientId, String clientCode) throws SQLException {
        List<String> handicapAgentIds = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        Date nowTime = new Date();
        sql.append("insert into ibm_client_ha(IBM_CLIENT_HA_ID_,HANDICAP_AGENT_ID_,EXIST_HA_ID_,CLIENT_ID_,CLIENT_CODE_,HANDICAP_CODE_,"
                + "CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
        List<Object> parameterList = new ArrayList<>();
        for (int i = 0; i < haInfos.size(); i++) {
            sql.append("(?,?,?,?,?,?,?,?,?,?,?),");
            JSONObject resultObj = haInfos.getJSONObject(i);
            String handicapAgentId = resultObj.getString("HANDICAP_AGENT_ID_");
            parameterList.add(UUID.randomUUID().toString().replace("-", ""));
            parameterList.add(handicapAgentId);
            parameterList.add(resultObj.getString("EXIST_HA_ID_"));
            parameterList.add(clientId);
            parameterList.add(clientCode);
            parameterList.add(resultObj.getString("HANDICAP_CODE_"));
            parameterList.add(nowTime);
            parameterList.add(nowTime.getTime());
            parameterList.add(nowTime);
            parameterList.add(nowTime.getTime());
            parameterList.add(IbmStateEnum.OPEN.name());

            handicapAgentIds.add(handicapAgentId);
        }
        sql.deleteCharAt(sql.length() - 1);
        super.dao.execute(sql.toString(), parameterList);

        return handicapAgentIds;
    }

	/**
	 * 修改代理客户端信息
	 * @param clientHmInfo	会员客户端信息
	 * @param existHaId		已存在代理id
	 * @param clientId		客户端id
	 * @param clientCode		客户端编码
	 */
	public void update(Map<String, Object> clientHmInfo, String existHaId, String clientId, String clientCode) throws SQLException {
		String sql="update ibm_client_ha set EXIST_HA_ID_=?,CLIENT_ID_=?,CLIENT_CODE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?"
				+ " where IBM_CLIENT_HA_ID_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(existHaId);
		parameters.add(clientId);
		parameters.add(clientCode);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(clientHmInfo.get("IBM_CLIENT_HA_ID_"));
		super.dao.execute(sql,parameters);
	}
}
