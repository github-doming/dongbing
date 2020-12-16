package com.ibm.follow.servlet.cloud.ibm_config.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_config.entity.IbmConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmConfigService extends BaseServicePlus {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmConfig对象数据
	 */
	public String save(IbmConfig entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_config的 IBM_CONFIG_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_config set state_='DEL' where IBM_CONFIG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CONFIG_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_config的 IBM_CONFIG_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_config set state_='DEL' where IBM_CONFIG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_config的 IBM_CONFIG_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_config where IBM_CONFIG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CONFIG_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_config的 IBM_CONFIG_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_config where IBM_CONFIG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmConfig实体信息
	 *
	 * @param entity IbmConfig实体
	 */
	public void update(IbmConfig entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_config表主键查找IbmConfig实体
	 *
	 * @param id ibm_config 主键
	 * @return IbmConfig实体
	 */
	public IbmConfig find(String id) throws Exception {
		return (IbmConfig) this.dao.find(IbmConfig.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_config where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_config  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_config  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmConfig数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmConfig数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_config where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_config  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_config  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmConfig.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_config  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmConfig数据信息
	 *
	 * @return 可用<IbmConfig>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_config  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmConfig.class, sql);
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
	 * 获取中心端配置值
	 *
	 * @param configKey 配置键
	 * @return 配置值
	 */
	public String findConfigValue(String configKey) throws SQLException {
		String sql = "SELECT CONFIG_VALUE_ FROM ibm_config  where CONFIG_KEY_  = ?  and state_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(configKey);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findString("CONFIG_VALUE_", sql, parameters);
	}

	public Map<Object, Object> mapEventSize() throws SQLException {
		String sql = "SELECT CONFIG_KEY_ as key_,CONFIG_VALUE_ as value_ FROM ibm_config  where CONFIG_KEY_ like '%_EVENT'  and state_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);

	}

	/**
	 * 获取所有盘口游戏封盘时间
	 *
	 * @return 封盘时间
	 */
	public Map<Object, Object> mapAllSealTime() throws SQLException {
		String sql = "SELECT CONFIG_KEY_ as key_,CONFIG_VALUE_ as value_ FROM ibm_config  where CONFIG_KEY_ like '%#SEAL_TIME'  and state_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}

	/**
	 * 获取所有盘口游戏封盘时间
	 *
	 * @return 封盘时间
	 */
	public Map<Object, Object> mapSealTime(String configId) throws SQLException {
		String sql = "SELECT CONFIG_KEY_ as key_,CONFIG_VALUE_ as value_ FROM ibm_config  where IBM_CONFIG_ID_=? AND CONFIG_KEY_ like '%#SEAL_TIME'  and state_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(configId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}


	/**
	 * 获取所有盘口游戏封盘时间
	 *
	 * @param handicapCode 盘口编码
	 * @param gameCode     游戏编码
	 * @return 封盘时间
	 */
	public PageCoreBean<Map<String, Object>> pageSealTimeInfo(String handicapCode, String gameCode,Integer pageIndex, Integer pageSize) throws SQLException {
		String sql = "SELECT IBM_CONFIG_ID_,CONFIG_KEY_,CONFIG_VALUE_,STATE_ FROM ibm_config  where CONFIG_KEY_ like ?  and STATE_!= ? ORDER BY CONFIG_KEY_";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameters = new ArrayList<>(1);
		if (StringTool.notEmpty(handicapCode) && StringTool.notEmpty(gameCode)) {
			parameters.add(handicapCode.concat("#").concat(gameCode).concat("#SEAL_TIME"));
		} else if (StringTool.notEmpty(handicapCode)) {
			parameters.add(handicapCode.concat("#%#SEAL_TIME"));
		} else if (StringTool.notEmpty(gameCode)) {
			parameters.add("%#".concat(gameCode).concat("#SEAL_TIME"));
		} else {
			parameters.add("%#SEAL_TIME");
		}
		parameters.add(IbmStateEnum.DEL.name());

		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameters, pageIndex, pageSize, sqlCount, parameters);
	}

	/**
	 * 修改value值
	 *
	 * @param key   配置key
	 * @param value 配置value
	 */
	public void updateSealTime(String key, String value) throws SQLException {
		String sql = "update ibm_config set CONFIG_VALUE_=?,UPDATE_TIME_LONG_=? where CONFIG_KEY_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(value);
		parameters.add(System.currentTimeMillis());
		parameters.add(key);
		super.dao.execute(sql, parameters);
	}
}
