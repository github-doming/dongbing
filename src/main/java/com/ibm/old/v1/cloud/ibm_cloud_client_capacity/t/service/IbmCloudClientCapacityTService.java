package com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_cloud_client.t.entity.IbmCloudClientT;
import com.ibm.old.v1.cloud.ibm_cloud_client.t.service.IbmCloudClientTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.entity.IbmCloudClientCapacityT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmCloudClientCapacityTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmCloudClientCapacityT对象数据
	 */
	public String save(IbmCloudClientCapacityT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_cloud_client_capacity的 IBM_CLOUD_CLIENT_CAPACITY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cloud_client_capacity set state_='DEL' where IBM_CLOUD_CLIENT_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLOUD_CLIENT_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cloud_client_capacity的 IBM_CLOUD_CLIENT_CAPACITY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_cloud_client_capacity set state_='DEL' where IBM_CLOUD_CLIENT_CAPACITY_ID_ in("
					+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_cloud_client_capacity的 IBM_CLOUD_CLIENT_CAPACITY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cloud_client_capacity where IBM_CLOUD_CLIENT_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLOUD_CLIENT_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cloud_client_capacity的 IBM_CLOUD_CLIENT_CAPACITY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_cloud_client_capacity where IBM_CLOUD_CLIENT_CAPACITY_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCloudClientCapacityT实体信息
	 *
	 * @param entity IbmCloudClientCapacityT实体
	 */
	public void update(IbmCloudClientCapacityT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cloud_client_capacity表主键查找IbmCloudClientCapacityT实体
	 *
	 * @param id ibm_cloud_client_capacity 主键
	 * @return IbmCloudClientCapacityT实体
	 */
	public IbmCloudClientCapacityT find(String id) throws Exception {
		return (IbmCloudClientCapacityT) this.dao.find(IbmCloudClientCapacityT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_cloud_client_capacity where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_client_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_client_capacity  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmCloudClientCapacityT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmCloudClientCapacityT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cloud_client_capacity where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_client_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_client_capacity  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmCloudClientCapacityT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmCloudClientCapacityT数据信息
	 *
	 * @return 可用<IbmCloudClientCapacityT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmCloudClientCapacityT.class, sql);
	}

	/**
	 * 更新客户端信息
	 *
	 * @param clientCode  客户端code
	 * @param exitsCount  已有数量
	 * @param capacityMax 最大容量
	 * @return 中心端客户端主键
	 */
	public IbmCloudClientCapacityT updateClientInfo(String clientCode, String exitsCount, String capacityMax)
			throws Exception {
		//已经存在此记录
		IbmCloudClientCapacityT clientCapacityT = this.findByClientCode(clientCode);
		if (clientCapacityT != null) {
			clientCapacityT.setClientCapacity(exitsCount);
			clientCapacityT.setClientCapacityMax(capacityMax);
			clientCapacityT.setUpdateTime(new Date());
			clientCapacityT.setUpdateTimeLong(clientCapacityT.getUpdateTime().getTime());
			if (exitsCount.equals(capacityMax)) {
				clientCapacityT.setState(IbmStateEnum.FULL.name());
			}else{
				clientCapacityT.setState(IbmStateEnum.OPEN.name());
			}
			this.update(clientCapacityT);
			return clientCapacityT;
		}
		//查找客户端注册信息
		IbmCloudClientTService clientTService = new IbmCloudClientTService();
		IbmCloudClientT clientT =clientTService.findByClientCode(clientCode);

		//保存此记录
		clientCapacityT = new IbmCloudClientCapacityT();
		clientCapacityT.setCloudClientId(clientT.getIbmCloudClientId());
		clientCapacityT.setClientCode(clientT.getClientCode());
		clientCapacityT.setClientCapacityMax(capacityMax);
		clientCapacityT.setClientCapacity(exitsCount);
		clientCapacityT.setCreateTime(new Date());
		clientCapacityT.setCreateTimeLong(clientCapacityT.getCreateTime().getTime());
		clientCapacityT.setUpdateTime(new Date());
		clientCapacityT.setUpdateTimeLong(clientCapacityT.getUpdateTime().getTime());
		clientCapacityT.setState(IbmStateEnum.OPEN.name());
		clientCapacityT.setIbmCloudClientCapacityId(this.save(clientCapacityT));
		return clientCapacityT;
	}

	/**
	 * 根据客户端code获取客户端容量信息
	 *
	 * @param clientCode 客户端code
	 * @return 客户端容量信息
	 */
	public IbmCloudClientCapacityT findByClientCode(String clientCode) throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client_capacity  where CLIENT_CODE_ = ? and state_!='DEL'";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(clientCode);
		return (IbmCloudClientCapacityT) super.dao.findObject(IbmCloudClientCapacityT.class, sql, parameters);
	}

	/**
	 * 根据客户端code逻辑删除已有客户端信息
	 * @param clientCode 客户端code
	 */
	public void delByClientCode(String clientCode,String className) throws SQLException {
		String sql = "update ibm_cloud_client_capacity set state_='DEL', DESC_=? where CLIENT_CODE_= ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(className+"根据客户端code逻辑删除已有客户端信息");
		parameterList.add(clientCode);
		super.dao.execute(sql,parameterList);
	}
}
