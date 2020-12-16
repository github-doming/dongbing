package com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.entity.IbmClientHandicapCapacity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmClientHandicapCapacityService extends BaseService {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmClientHandicapCapacity对象数据
	 */
	public String save(IbmClientHandicapCapacity entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_client_handicap_capacity的 IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_client_handicap_capacity set state_='DEL' where IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_handicap_capacity的 IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_client_handicap_capacity set state_='DEL' where IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_ in("
							+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_client_handicap_capacity的 IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_client_handicap_capacity where IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_client_handicap_capacity的 IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_client_handicap_capacity where IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_ in("
					+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmClientHandicapCapacity实体信息
	 *
	 * @param entity IbmClientHandicapCapacity实体
	 */
	public void update(IbmClientHandicapCapacity entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_client_handicap_capacity表主键查找IbmClientHandicapCapacity实体
	 *
	 * @param id ibm_client_handicap_capacity 主键
	 * @return IbmClientHandicapCapacity实体
	 */
	public IbmClientHandicapCapacity find(String id) throws Exception {
		return (IbmClientHandicapCapacity) this.dao.find(IbmClientHandicapCapacity.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_client_handicap_capacity where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_handicap_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_handicap_capacity  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmClientHandicapCapacity数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmClientHandicapCapacity数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_client_handicap_capacity where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_client_handicap_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_client_handicap_capacity  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmClientHandicapCapacity.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_handicap_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmClientHandicapCapacity数据信息
	 *
	 * @return 可用<IbmClientHandicapCapacity>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_client_handicap_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmClientHandicapCapacity.class, sql);
	}

	/**
	 * 更新容量
	 *
	 * @param clientId            客户端主键
	 * @param clientCode          客户端编码
	 * @param handicapCode        盘口编码
	 * @param clientCapacityId    客户端容量id
	 * @param capacityHandicapMax 盘口最大容量
	 * @param capacityHandicap    盘口使用容量
	 * @param capacityHaMax       盘口代理最大容量
	 * @param capacityHa          盘口代理使用容量
	 * @param capacityHmMax       盘口会员最大容量
	 * @param capacityHm          盘口会员使用容量
	 * @param nowTime             当前时间
	 * @return 客户端盘口容量记录主键
	 */
	public String updateCapacity(String clientId, String clientCode, String handicapCode, String clientCapacityId,
								 int capacityHandicapMax, int capacityHandicap, int capacityHaMax, int capacityHa, int capacityHmMax,
								 int capacityHm, Date nowTime) throws Exception {
		String sql = "SELECT IBM_CLIENT_HANDICAP_CAPACITY_ID_ FROM ibm_client_handicap_capacity "
				+ " WHERE CLIENT_CAPACITY_ID_ = ? and CLIENT_ID_ = ? AND HANDICAP_CODE_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(clientCapacityId);
		parameterList.add(clientId);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.DEL.name());
		String id = super.dao.findString("IBM_CLIENT_HANDICAP_CAPACITY_ID_", sql, parameterList);

		String state = capacityHandicapMax > capacityHa + capacityHm ? IbmStateEnum.OPEN.name() : IbmStateEnum.FULL.name();
		if (StringTool.isEmpty(id)) {
			IbmClientHandicapCapacity handicapCapacity = new IbmClientHandicapCapacity();
			handicapCapacity.setClientCapacityId(clientCapacityId);
			handicapCapacity.setClientId(clientId);
			handicapCapacity.setClientCode(clientCode);
			handicapCapacity.setHandicapCode(handicapCode);
			handicapCapacity.setCapacityHandicapMax(capacityHandicapMax);
			handicapCapacity.setCapacityHandicap(capacityHandicap);
			handicapCapacity.setCapacityHaMax(capacityHaMax);
			handicapCapacity.setCapacityHa(capacityHa);
			handicapCapacity.setCapacityHmMax(capacityHmMax);
			handicapCapacity.setCapacityHm(capacityHm);
			handicapCapacity.setCreateTime(nowTime);
			handicapCapacity.setCreateTimeLong(nowTime.getTime());
			handicapCapacity.setState(state);
			id = this.save(handicapCapacity);
		} else {
			sql = "update ibm_client_handicap_capacity set CAPACITY_HANDICAP_MAX_=?,CAPACITY_HANDICAP_=?,CAPACITY_HA_MAX_=?,CAPACITY_HA_=?,"
					+ "CAPACITY_HM_MAX_=?,CAPACITY_HM_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_ = ? where IBM_CLIENT_HANDICAP_CAPACITY_ID_=?";
			parameterList.clear();
			parameterList.add(capacityHandicapMax);
			parameterList.add(capacityHandicap);
			parameterList.add(capacityHaMax);
			parameterList.add(capacityHa);
			parameterList.add(capacityHmMax);
			parameterList.add(capacityHm);
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(state);
			parameterList.add(id);
			super.dao.execute(sql, parameterList);
		}
		return id;
	}

	/**
	 * 更新容量
	 *
	 * @param clientId            客户端主键
	 * @param handicapCode        盘口编码
	 * @param handicapCapacityMax 盘口最大容量
	 * @param haCapacityMax       盘口代理最大容量
	 * @param haCapacity          盘口代理使用容量
	 * @param hmCapacityMax       盘口会员最大容量
	 * @param hmCapacity          盘口会员使用容量
	 * @param nowTime             当前时间
	 */
	public void updateCapacity(String clientId, String handicapCode, int handicapCapacityMax, int haCapacityMax,
							   int haCapacity, int hmCapacityMax, int hmCapacity, Date nowTime) throws SQLException {
		String sql = "update ibm_client_handicap_capacity set CAPACITY_HANDICAP_MAX_=?,CAPACITY_HANDICAP_=?, "
				+ " CAPACITY_HA_MAX_=?,CAPACITY_HA_=?,CAPACITY_HM_MAX_=?,CAPACITY_HM_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?, "
				+ " DESC_ = ? where CLIENT_ID_ = ? and HANDICAP_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(12);
		parameterList.add(handicapCapacityMax);
		parameterList.add(haCapacity + hmCapacity);
		parameterList.add(haCapacityMax);
		parameterList.add(haCapacity);
		parameterList.add(hmCapacityMax);
		parameterList.add(hmCapacity);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("客户端心跳检测-更新容量");
		parameterList.add(clientId);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 注销客户端
	 *
	 * @param clientId 客户端主键
	 * @param time     注销时间
	 */
	public void cancelClient(Object clientId, Date time) throws SQLException {
		String sql = "UPDATE ibm_client_handicap_capacity SET STATE_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE CLIENT_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(time);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("注销客户端");
		parameterList.add(clientId);
		parameterList.add(IbmStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 清除过期客户端的容量信息
	 *
	 * @param expiredClientIds 过期客户端ids
	 */
	public void clearClientInfo(Set<Object> expiredClientIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameterList = new ArrayList<>();
		sql.append("update ibm_client_handicap_capacity set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,DESC_=? where STATE_!=? and CLIENT_ID_ in (");
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("客户端管理清除容量信息");
		parameterList.add(IbmStateEnum.DEL.name());
		for (Object clientId : expiredClientIds) {
			sql.append("?,");
			parameterList.add(clientId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 获取客户端盘口容量信息
	 *
	 * @param clientCode 客户端编码
	 */
	public PageCoreBean<Map<String, Object>> findByClientCode(String clientCode, int pageIndex, int pageSize) throws SQLException {
		String sql = "select CLIENT_CODE_,HANDICAP_CODE_,CAPACITY_HANDICAP_MAX_,CAPACITY_HANDICAP_,CAPACITY_HA_MAX_,CAPACITY_HA_,CAPACITY_HM_MAX_,CAPACITY_HM_"
				+ " from ibm_client_handicap_capacity where CLIENT_CODE_=? and STATE_=?";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(clientCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 获取盘口容量信息
	 *
	 * @param clientCode   客户端编码
	 * @param handicapCode 盘口编码
	 * @return
	 */
	public Map<String, Object> findHandicapCapacityInfo(String clientCode, String handicapCode) throws SQLException {
		String sql = "select CLIENT_CODE_,HANDICAP_CODE_,CAPACITY_HANDICAP_MAX_,CAPACITY_HA_MAX_,CAPACITY_HM_MAX_ from ibm_client_handicap_capacity"
				+ " where CLIENT_CODE_=? and HANDICAP_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(clientCode);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 获取盘口容量信息
	 *
	 * @param handicapCode 盘口code
	 * @param customerType 客户类型
	 * @return
	 */
	public Map<String, Object> findHandicapCapacity(String handicapCode, IbmTypeEnum customerType) throws SQLException {
		String sql = "select SUM(%s) as capacityMax ,SUM(%s) as capacityUse from ibm_client_handicap_capacity WHERE HANDICAP_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(handicapCode);
		parameterList.add(IbmStateEnum.DEL.name());
		switch (customerType) {
			case MEMBER:
				return super.dao.findMap(String.format(sql, "CAPACITY_HM_MAX_", "CAPACITY_HM_"), parameterList);
			case AGENT:
				return super.dao.findMap(String.format(sql, "CAPACITY_HA_MAX_", "CAPACITY_HA_"), parameterList);
			default:
				return new HashMap<>();
		}
	}
}
