package com.ibs.plan.module.cloud.ibsp_config.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_config.entity.IbspConfig;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* IBSP_中心端配置 服务类
 * @author Robot
 */
public class IbspConfigService extends BaseServiceProxy {

	/**
	 * 保存IBSP_中心端配置 对象数据
	 * @param entity IbspConfig对象数据
	 */
	public String save(IbspConfig entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_config 的 IBSP_CONFIG_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_config set state_='DEL' where IBSP_CONFIG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSP_CONFIG_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_config 的 IBSP_CONFIG_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_config set state_='DEL' where IBSP_CONFIG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_config  的 IBSP_CONFIG_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_config where IBSP_CONFIG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSP_CONFIG_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_config 的 IBSP_CONFIG_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_config where IBSP_CONFIG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspConfig实体信息
	 * @param entity IBSP_中心端配置 实体
	 */
	public void update(IbspConfig entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_config表主键查找 IBSP_中心端配置 实体
	 * @param id ibsp_config 主键
	 * @return IBSP_中心端配置 实体
	 */
	public IbspConfig find(String id) throws Exception {
		return dao.find(IbspConfig.class,id);

	}

	/**
	 * 封盘时间
	 * @return 封盘时间
	 */
	public Map<String, Map<String, Object>> sealTime() throws SQLException {
		List<Map<String, Object>> sealTimeInfos = listByType("SEAL_TIME");
		Map<String, Map<String, Object>> sealTime = new HashMap<>(5);
		for (Map<String, Object> capacityInfo : sealTimeInfos) {
			String configKey = capacityInfo.get("CONFIG_KEY_").toString();
			Object configValue = capacityInfo.get("CONFIG_VALUE_");
			String[] keys = configKey.split("#");
			put(sealTime, keys[0], keys[1], configValue);
		}
		return sealTime;
	}

	/**
	 * 根据配置TYPE 获取配置信息
	 *
	 * @param configType 配置TYPE
	 * @return 配置信息
	 */
	public List<Map<String, Object>> listByType(String configType) throws SQLException {
		String sql = "select CONFIG_KEY_ ,CONFIG_VALUE_ from ibsp_config where CONFIG_TYPE_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(configType);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}


	/**
	 * 给 一个 MAP MAP 型集合中放入数据
	 *
	 * @param map   集合
	 * @param code  第一个键
	 * @param type  第二个键
	 * @param value 放入数据
	 */
	private void put(Map<String, Map<String, Object>> map, String code, String type, Object value) {
		if (!map.containsKey(code)) {
			Map<String, Object> data = new HashMap<>(2);
			map.put(code, data);
		}
		map.get(code).put(type, value);
	}

	/**
	 * 获取所有盘口游戏封盘时间
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @return 封盘时间
	 */
	public PageCoreBean<Map<String, Object>> pageSealTimeInfo(String handicapCode, String gameCode, Integer pageIndex, Integer pageSize) throws SQLException {
		String sql = "SELECT IBSP_CONFIG_ID_,CONFIG_KEY_,CONFIG_VALUE_,STATE_ FROM ibsp_config  where CONFIG_KEY_ like ?  and STATE_!= ? ORDER BY CONFIG_KEY_";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameters = new ArrayList<>(1);
		if (StringTool.notEmpty(handicapCode) && StringTool.notEmpty(gameCode)) {
			parameters.add(handicapCode.concat("#").concat(gameCode));
		} else if (StringTool.notEmpty(handicapCode)) {
			parameters.add(handicapCode.concat("#%"));
		} else if (StringTool.notEmpty(gameCode)) {
			parameters.add("%#".concat(gameCode));
		} else {
			parameters.add("%#%");
		}
		parameters.add(IbsStateEnum.DEL.name());

		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}


	/**
	 * 获取中心端配置值
	 *
	 * @param configKey 配置键
	 * @return 配置值
	 */
	public String findConfigValue(String configKey) throws SQLException {
		String sql = "SELECT CONFIG_VALUE_ FROM ibsp_config  where CONFIG_KEY_  = ?  and state_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(configKey);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findString("CONFIG_VALUE_", sql, parameters);
	}

	/**
	 * 获取封盘时间
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @return 封盘时间
	 */
	public long findSealTime(String handicapCode, String gameCode) throws SQLException {
		return NumberTool.getLong(findConfigValue(handicapCode.concat("#").concat(gameCode).concat("#SEAL_TIME")));
	}

	/**
	 * 获取所有盘口游戏封盘时间
	 *
	 * @return 封盘时间
	 */
	public Map<Object, Object> mapSealTime(String configId) throws SQLException {
		String sql = "SELECT CONFIG_KEY_ as key_,CONFIG_VALUE_ as value_ FROM ibsp_config  where IBSP_CONFIG_ID_=? AND CONFIG_KEY_ like '%#SEAL_TIME'  and state_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(configId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}

	/**
	 * 获取所有盘口游戏封盘时间
	 *
	 * @return 封盘时间
	 */
	public Map<Object, Object> mapAllSealTime() throws SQLException {
		String sql = "SELECT CONFIG_KEY_ as key_,CONFIG_VALUE_ as value_ FROM ibsp_config  where CONFIG_KEY_ like '%#SEAL_TIME'  and state_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}

}
