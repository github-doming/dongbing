package com.ibs.plan.module.client.ibsc_config.service;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.ibsc_config.entity.IbscConfig;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.*;

/**
* IBSC客户端_配置 服务类
 * @author Robot
 */
public class IbscConfigService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_配置 对象数据
	 * @param entity IbscConfig对象数据
	 */
	public String save(IbscConfig entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsc_config 的 IBSC_CONFIG_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_config set state_='DEL' where IBSC_CONFIG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_CONFIG_ID_主键id数组的数据
	 * @param idArray 要删除 ibsc_config 的 IBSC_CONFIG_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_config set state_='DEL' where IBSC_CONFIG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsc_config  的 IBSC_CONFIG_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_config where IBSC_CONFIG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_CONFIG_ID_主键id数组的数据
	 * @param idArray 要删除ibsc_config 的 IBSC_CONFIG_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_config where IBSC_CONFIG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscConfig实体信息
	 * @param entity IBSC客户端_配置 实体
	 */
	public void update(IbscConfig entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_config表主键查找 IBSC客户端_配置 实体
	 * @param id ibsc_config 主键
	 * @return IBSC客户端_配置 实体
	 */
	public IbscConfig find(String id) throws Exception {
		return (IbscConfig) dao.find(IbscConfig.class,id);

	}

	public long findSealTime(String handicapCode, String gameCode) throws SQLException {
		return NumberTool.getLong(findValue(handicapCode.concat("#").concat(gameCode),"SEAL_TIME"));
	}
	/**
	 * 获取盘口游戏类型
	 * @param handicapCode
	 * @param gameCode
	 * @return
	 */
	public String findHandicapGameType(HandicapUtil.Code handicapCode, GameUtil.Code gameCode) throws SQLException {
		return findValue(handicapCode.name().concat("#").concat(gameCode.name()),"GAME_TYPE");
	}
	/**
	 * 获取value
	 * @param key			key
	 * @param configType	配置类型
	 * @return
	 */
	public String findValue(String key, String configType) throws SQLException {
		String sql="select CLIENT_CONFIG_VALUE_ from ibsc_config where CLIENT_CONFIG_KEY_=? and CONFIG_TYPE_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(key);
		parameters.add(configType);
		return super.dao.findString("CLIENT_CONFIG_VALUE_",sql,parameters);
	}

	/**
	 * 根据key获取实体类
	 *
	 * @param key 配置信息key
	 * @return
	 */
	public IbscConfig findByKey(String key) throws Exception {
		String sql = "select * from ibsc_config where CLIENT_CONFIG_KEY_=?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(key);
		return  super.dao.findObject(IbscConfig.class, sql, parameters);
	}


	/**
	 * 容量信息
	 *
	 * @return 容量信息
	 */
	public Map<String, Object> capacity() throws SQLException {
		List<Map<String, Object>> capacityInfos = listByType("CAPACITY");
		Map<String, Object> capacity = new HashMap<>(5);
		for (Map<String, Object> capacityInfo : capacityInfos) {
			String configKey = capacityInfo.get("CLIENT_CONFIG_KEY_").toString();
			Object configValue = capacityInfo.get("CLIENT_CONFIG_VALUE_");
			String[] keys = configKey.split("#");
			capacity.put(keys[0],configValue);
		}
		return capacity;

	}

	/**
	 * 根据配置TYPE 获取配置信息
	 *
	 * @param configType 配置TYPE
	 * @return 配置信息
	 */
	public List<Map<String, Object>> listByType(String configType) throws SQLException {
		String sql = "select CLIENT_CONFIG_KEY_ ,CLIENT_CONFIG_VALUE_ from ibsc_config where CONFIG_TYPE_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(configType);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 根据配置TYPE 获取配置信息
	 *
	 * @param configType 配置TYPE
	 * @return 配置信息
	 */
	public Map<String, String> mapByType(String configType) throws SQLException {
		String sql = "select CLIENT_CONFIG_KEY_ as key_,CLIENT_CONFIG_VALUE_ as value_ from ibsc_config where CONFIG_TYPE_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(configType);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}

	/**
	 * 根据配置TYPE 删除数据
	 *
	 * @param configType 配置TYPE
	 * @param nowTime 删除时间
	 */
	public void delByType(String configType, Date nowTime) throws SQLException {
		String sql = "UPDATE ibsc_config SET STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE "
				+ " CONFIG_TYPE_ = ? AND STATE_ = ?";
		List<Object> parameters = new ArrayList<>(5);
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(configType);
		parameters.add(IbsStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}
	/**
	 * 存在封盘时间信息的进行修改
	 *
	 * @param sealTime       消息封盘时间
	 * @param clientSealTime 客户端封盘时间
	 */
	public void updateByKeys(JSONObject sealTime, Map<String, String> clientSealTime) throws SQLException {
		StringBuilder sql=new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		List<Object> containKey=new ArrayList<>();
		StringBuilder join=new StringBuilder();
		sql.append("update ibsc_config set UPDATE_TIME_LONG_=?,CLIENT_CONFIG_VALUE_ = CASE CLIENT_CONFIG_KEY_");
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
		String sql = "update ibsc_config set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where"
				+ " CLIENT_CONFIG_KEY_ = ? and state_ = ?";
		List<Object> parameters = new ArrayList<>(8);
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(handicapCode.name().concat("_CAPACITY_MAX"));
		parameters.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql,parameters);
	}

	/**
	 * 获取所有封盘时间信息
	 *
	 * @return
	 */
	public Map<String, String> findAllSealTime() throws SQLException {
		String sql = "SELECT CLIENT_CONFIG_KEY_ as key_,CLIENT_CONFIG_VALUE_ as value_ FROM ibmc_config where CLIENT_CONFIG_KEY_ like '%#SEAL_TIME' ";
		return findKVMap(sql, null);
	}
}
