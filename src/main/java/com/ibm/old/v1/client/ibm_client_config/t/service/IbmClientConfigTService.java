package com.ibm.old.v1.client.ibm_client_config.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.client.ibm_client_config.t.entity.IbmClientConfigT;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmClientConfigTService extends BaseServicePlus {


	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientConfigT对象数据
	 */
	public String save(IbmClientConfigT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_config的 IBM_CLIENT_CONFIG_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_config set state_='DEL' where IBM_CLIENT_CONFIG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLIENT_CONFIG_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_config的 IBM_CLIENT_CONFIG_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_client_config set state_='DEL' where IBM_CLIENT_CONFIG_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_config的 IBM_CLIENT_CONFIG_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_config where IBM_CLIENT_CONFIG_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLIENT_CONFIG_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_config的 IBM_CLIENT_CONFIG_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_client_config where IBM_CLIENT_CONFIG_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientConfigT实体信息
	 *
	 * @param entity IbmClientConfigT实体
	 */
	public void update(IbmClientConfigT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_config表主键查找IbmClientConfigT实体
	 *
	 * @param id ibm_client_config 主键
	 * @return IbmClientConfigT实体
	 */
	public IbmClientConfigT find(String id) throws Exception {
		return (IbmClientConfigT) this.dao.find(IbmClientConfigT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client_config where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_config  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_config  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientConfigT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientConfigT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_config where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_config  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_config  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmClientConfigT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_config  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientConfigT数据信息
	 *
	 * @return 可用<IbmClientConfigT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_config  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientConfigT.class, sql);
	}

	/**
	 * 获取最大容量
	 *
	 * @param handicapCode 盘口code
	 * @return 最大容量及，盘口最大容量	《CAPACITY_MAX，$handicapCode$_CAPACITY_MAX》
	 * @throws SQLException sql执行错误
	 */
	public Map<Object, Object> findMaxCapacity(String handicapCode) throws SQLException {
		String sql = "SELECT CLIENT_CONFIG_KEY_ as key_,CLIENT_CONFIG_VALUE_ as value_ FROM ibm_client_config  where"
				+ " CLIENT_CONFIG_KEY_ in (?,?) and state_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add("CAPACITY_MAX");
		parameters.add(handicapCode + "_CAPACITY_MAX");
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}

	/**
	 * 注销客户端
	 */
	public void logout() throws SQLException {
		String sql = "update ibm_client_exist_hm set state_='DEL' ";
		dao.execute(sql, null);
		 sql = "update ibm_client_hm set state_='DEL' ";
		dao.execute(sql, null);
		 sql = "update ibm_client_hm_set set state_='DEL' ";
		dao.execute(sql, null);
		 sql = "update ibm_client_hm_game_set set state_='DEL' ";
		dao.execute(sql, null);
		 sql = "update ibm_client_exist_bet set state_='DEL' ";
		dao.execute(sql, null);
		 sql = "update ibm_client_bet set state_='DEL' ";
		dao.execute(sql, null);
		sql = "update ibm_client_check set state_='DEL' ";
		dao.execute(sql, null);
	}
	/**
	 * 获取盘口游戏封盘时间
	 * @param handicapCode 	盘口code
	 * @param gameCode		游戏code
	 * @return
	 * @throws SQLException
	 */
	public String findHandicapGameSeal(String handicapCode,String gameCode) throws SQLException {
		String sql="SELECT CLIENT_CONFIG_VALUE_ FROM ibm_client_config  where"
				+ " CLIENT_CONFIG_KEY_=? and state_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(handicapCode.concat("_").concat(gameCode).concat("_SEAL"));
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("CLIENT_CONFIG_VALUE_",sql,parameters);
	}

	/**
	 * 查看配置值
	 * @param key 配置键
	 * @return 配置值
	 */
	public String findValue(String key) throws SQLException {
		String sql="SELECT CLIENT_CONFIG_VALUE_ FROM ibm_client_config  where"
				+ " CLIENT_CONFIG_KEY_= ? and state_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(key);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("CLIENT_CONFIG_VALUE_",sql,parameters);
	}
}
