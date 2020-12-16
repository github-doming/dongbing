package com.ibm.follow.servlet.client.ibmc_config.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.client.ibmc_config.entity.IbmcConfig;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmcConfigService extends BaseServicePlus {


    /**
     * 保存{ay_table_class}对象数据
     *
     * @param entity IbmcConfig对象数据
     */
    public String save(IbmcConfig entity) throws Exception {
        return dao.save(entity);
    }

    /**
     * 逻辑删除
     *
     * @param id 要删除ibmc_config的 IBMC_CONFIG_ID_主键id
     */
    public void del(String id) throws Exception {
        String sql = "update ibmc_config set state_='DEL' where IBMC_CONFIG_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 逻辑删除IBMC_CONFIG_ID_主键id数组的数据
     *
     * @param idArray 要删除ibmc_config的 IBMC_CONFIG_ID_数组
     */
    public void delAll(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql =
                    "update ibmc_config set state_='DEL' where IBMC_CONFIG_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 物理删除
     *
     * @param id 要删除ibmc_config的 IBMC_CONFIG_ID_
     */
    public void delPhysical(String id) throws Exception {
        String sql = "delete from ibmc_config where IBMC_CONFIG_ID_=?";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        dao.execute(sql, parameters);
    }

    /**
     * 物理删除IBMC_CONFIG_ID_主键id数组的数据
     *
     * @param idArray 要删除ibmc_config的 IBMC_CONFIG_ID_数组
     */
    public void delAllPhysical(String[] idArray) throws Exception {
        if (idArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String id : idArray) {
                stringBuilder.append("'").append(id).append("'").append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            String sql = "delete from ibmc_config where IBMC_CONFIG_ID_ in(" + stringBuilder.toString() + ")";
            dao.execute(sql, null);
        }
    }

    /**
     * 更新IbmcConfig实体信息
     *
     * @param entity IbmcConfig实体
     */
    public void update(IbmcConfig entity) throws Exception {
        dao.update(entity);
    }

    /**
     * 根据ibmc_config表主键查找IbmcConfig实体
     *
     * @param id ibmc_config 主键
     * @return IbmcConfig实体
     */
    public IbmcConfig find(String id) throws Exception {
        return (IbmcConfig) this.dao.find(IbmcConfig.class, id);

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
        String sqlCount = "SELECT count(*) FROM ibmc_config where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibmc_config  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibmc_config  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
        }
        return dao.page(sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 获取分页IbmcConfig数据
     *
     * @param sortFieldName 排序字段名
     * @param sortOrderName 排序顺序
     * @param pageIndex     页面索引
     * @param pageSize      页面大小
     * @return 分页IbmcConfig数据
     */
    public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
            throws Exception {
        String sqlCount = "SELECT count(*) FROM ibmc_config where state_!='DEL'";
        String sql;
        if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
            sql = "SELECT * FROM ibmc_config  where state_!='DEL' order by UPDATE_TIME_ desc";
        } else {
            sql = "SELECT * FROM ibmc_config  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
        }
        return dao.page(IbmcConfig.class, sql, null, pageIndex, pageSize, sqlCount);
    }

    /**
     * 按照更新顺序查询所有可用Map信息
     *
     * @return 可用Map信息
     */
    public List<Map<String, Object>> findAll() throws Exception {
        String sql = "SELECT * FROM ibmc_config  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findMapList(sql, null);
    }

    /**
     * 按照更新顺序查询所有可用IbmcConfig数据信息
     *
     * @return 可用<IbmcConfig>数据信息
     */
    public List findObjectAll() throws Exception {
        String sql = "SELECT * FROM ibmc_config  where state_!='DEL' order by UPDATE_TIME_ desc";
        return this.dao.findObjectList(IbmcConfig.class, sql);
    }

    /**
     * 获取最大容量
     *
     * @param handicapCode 盘口code
     * @return 最大容量及，盘口最大容量	《CAPACITY_MAX，$handicapCode$_CAPACITY_MAX》
     * @throws SQLException sql执行错误
     */
    public Map<Object, Object> findMaxCapacity(String handicapCode) throws SQLException {
        Map<Object, Object> capacity = new HashMap<>(4);
        capacity.putAll(findMaxCapacity());
        capacity.putAll(findHandicapCapacity(handicapCode));
        return capacity;
    }

    /**
     * * 获取的最大容量
     * * @return 最大容量	《CAPACITY_MAX，$handicapCode$_CAPACITY_MAX》
     */
    public Map<String, Object> findAllMaxCapacity() throws SQLException {
        Map<String, Object> capacity = new HashMap<>(HandicapUtil.codes().length + 1);
        //最大容量
        capacity.putAll(findMaxCapacity());
        //盘口最大容量
        for (HandicapUtil.Code code : HandicapUtil.codes()) {
            capacity.put(code.name(), findHandicapCapacity(code.name()));
        }
        return capacity;
    }

    /**
     * 获取最大容量
     *
     * @return 最大容量及，盘口最大容量	《CAPACITY_MAX，$handicapCode$_CAPACITY_MAX》
     * @throws SQLException sql执行错误
     */
    public Map<String, Object> findMaxCapacity() throws SQLException {
        String sql = "SELECT CLIENT_CONFIG_KEY_ as key_,CLIENT_CONFIG_VALUE_ as value_ FROM ibmc_config  where "
                + " CLIENT_CONFIG_KEY_ = ? and state_ = ?";
        List<Object> parameters = new ArrayList<>(2);
        parameters.add("CAPACITY_MAX");
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findKVMap(sql, parameters);
    }

    /**
     * 获取盘口最大容量
     *
     * @param handicapCode 盘口code
     * @return 盘口最大容量    《$handicapCode$_CAPACITY_MAX》
     */
    private Map<Object, Object> findHandicapCapacity(String handicapCode) throws SQLException {
        String sql = "SELECT CLIENT_CONFIG_KEY_ as key_,CLIENT_CONFIG_VALUE_ as value_ FROM ibmc_config  where"
                + " CLIENT_CONFIG_KEY_ in (?,?,?) and state_ = ?";
        List<Object> parameters = new ArrayList<>(4);
        parameters.add(handicapCode.concat("_CAPACITY_MAX"));
        parameters.add(handicapCode.concat("_MEMBER_CAPACITY_MAX"));
        parameters.add(handicapCode.concat("_AGENT_CAPACITY_MAX"));
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findKVMap(sql, parameters);
    }



    /**
     * 获取封盘时间
     *
     * @param handicapCode 盘口编码
     * @param gameCode     游戏编码
     * @return 封盘时间
     */
    public Long findSealTime(String handicapCode, String gameCode) throws SQLException {
        return NumberTool.getLong(findConfigValue(handicapCode.concat("#").concat(gameCode).concat("#SEAL_TIME")));
    }

    /**
     * 获取封盘时间集合
     *
     * @return 封盘时间集合
     */
    public Map<Object, Object> mapSealTime() throws SQLException {
        return mapKeyValue("#SEAL_TIME");
    }

    /**
     * 获取模糊查询的key-value集合
     *
     * @param likeKey 模糊key
     * @return key-value集合
     */
    private Map<Object, Object> mapKeyValue(String likeKey) throws SQLException {
        String sql = "SELECT CLIENT_CONFIG_KEY_ as key_,CLIENT_CONFIG_VALUE_ as value_ FROM ibmc_config where "
                + " CLIENT_CONFIG_KEY_ like '%" + likeKey + "%' and state_ = ? ";
        List<Object> parameters = new ArrayList<>(2);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.findKVMap(sql, parameters);

    }

    /**
     * 获取客户端配置值
     *
     * @param configKey 配置键
     * @return 配置值
     */
    public String findConfigValue(String configKey) throws SQLException {
        String sql = "SELECT CLIENT_CONFIG_VALUE_ FROM ibmc_config  where CLIENT_CONFIG_KEY_  = ?  and state_ = ?";
        List<Object> parameters = new ArrayList<>(2);
        parameters.add(configKey);
        parameters.add(IbmStateEnum.OPEN.name());
        return super.dao.findString("CLIENT_CONFIG_VALUE_", sql, parameters);
    }

    /**
     * 清除客户端数据
     */
    public void cancelClientInfo() throws SQLException {
        String tableName = "ibmc_agent_member_info,ibmc_exist_ha,ibmc_exist_hm,ibmc_ha_follow_bet,ibmc_ha_game_set,"
                + "ibmc_ha_info,ibmc_ha_set,ibmc_handicap_agent,ibmc_handicap_member,ibmc_hm_bet,ibmc_hm_bet_error,"
                + "ibmc_hm_bet_fail,ibmc_hm_bet_info,ibmc_hm_bet_item,ibmc_hm_game_set,ibmc_hm_info,ibmc_hm_set";
        List<Object> parameters = new ArrayList<>(4);
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(new Date());
        parameters.add(System.currentTimeMillis());
        parameters.add(IbmStateEnum.DEL.name());
        for (String table : tableName.split(",")) {
            String sql = "update " + table + " set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where STATE_!=?";
            super.dao.execute(sql, parameters);
        }
    }

    /**
     * 根据key获取实体类
     *
     * @param key 配置信息key
     * @return
     */
    public IbmcConfig findByKey(String key) throws Exception {
        String sql = "select * from ibmc_config where CLIENT_CONFIG_KEY_=?";
        List<Object> parameters = new ArrayList<>(1);
        parameters.add(key);
        return (IbmcConfig) super.dao.findObject(IbmcConfig.class, sql, parameters);
    }

    /**
     * 根据盘口code获取到最大容量信息
     *
     * @param handicapCode 盘口code
     * @return
     */
    public Map<String, Object> findByHandicapCode(String handicapCode) throws SQLException {
        String sql = "select CLIENT_CONFIG_KEY_ as key_,CLIENT_CONFIG_VALUE_ as value_ from ibmc_config where CLIENT_CONFIG_KEY_ like '" + handicapCode + "%CAPACITY_MAX'";
        return findKVMap(sql, null);
    }

    /**
     * 获取所有封盘时间信息
     *
     * @return
     */
    public Map<Object, Object> findAllSealTime() throws SQLException {
        String sql = "SELECT CLIENT_CONFIG_KEY_ as key_,CLIENT_CONFIG_VALUE_ as value_ FROM ibmc_config where CLIENT_CONFIG_KEY_ like '%#SEAL_TIME' ";
        return findKVMap(sql, null);
    }

    /**
     * 存在封盘时间信息的进行修改
     *
     * @param sealTime       消息封盘时间
     * @param clientSealTime 客户端封盘时间
     */
    public void updateByKeys(JSONObject sealTime, Map<Object, Object> clientSealTime) throws SQLException {
        StringBuilder sql=new StringBuilder();
        List<Object> parameters = new ArrayList<>();
        List<Object> containKey=new ArrayList<>();
        StringBuilder join=new StringBuilder();
        sql.append("update ibmc_config set UPDATE_TIME_LONG_=?,CLIENT_CONFIG_VALUE_ = CASE CLIENT_CONFIG_KEY_");
        parameters.add(System.currentTimeMillis());
        for(Object key:clientSealTime.keySet()){
            if(!sealTime.containsKey(key)){
                continue;
            }
            sql.append(" WHEN ? THEN ?");
            parameters.add(key);
            parameters.add(sealTime.get(key));
            join.append("?,");
            containKey.add(key);
        }
        sql.append("END WHERE CLIENT_CONFIG_KEY_ in(");
        join.replace(join.length()-1,join.length(),")");
        sql.append(join);
        parameters.addAll(containKey);
        super.dao.execute(sql.toString(),parameters);
    }

    /**
     * 删除盘口容量信息
     * @param handicapCode  盘口code
     */
    public void updateByHandicapCode(HandicapUtil.Code handicapCode) throws SQLException {
        String sql = "update ibmc_config set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where"
                + " CLIENT_CONFIG_KEY_ in (?,?,?) and state_ = ?";
        List<Object> parameters = new ArrayList<>(8);
        parameters.add(IbmStateEnum.DEL.name());
        parameters.add(new Date());
        parameters.add(System.currentTimeMillis());
        parameters.add(handicapCode.name().concat("_CAPACITY_MAX"));
        parameters.add(handicapCode.name().concat("_MEMBER_CAPACITY_MAX"));
        parameters.add(handicapCode.name().concat("_AGENT_CAPACITY_MAX"));
        parameters.add(IbmStateEnum.OPEN.name());
        super.dao.execute(sql,parameters);
    }

    /**
     * 获取容量信息
     * @return
     * @throws SQLException
     */
    public Map<String, Object> findVrMaxCapacity() throws SQLException {
        Map<String, Object> capacity = new HashMap<>(1);
        //最大容量
        capacity.putAll(findMaxCapacity());
        Map<Object, Object> vrCapacityInfo = findHandicapCapacity("VR");
        vrCapacityInfo.put("VR_CAPACITY_MAX",99);
        vrCapacityInfo.put("VR_AGENT_CAPACITY_MAX",99);
        capacity.put("VR", vrCapacityInfo);
        return capacity;
    }
}
