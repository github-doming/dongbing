package com.ibm.old.v1.cloud.ibm_cloud_client_handicap_capacity.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.core.controller.mq.CloseClientController;
import com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.entity.IbmCloudClientCapacityT;
import com.ibm.old.v1.cloud.ibm_cloud_client_capacity.t.service.IbmCloudClientCapacityTService;
import com.ibm.old.v1.cloud.ibm_cloud_client_handicap_capacity.t.entity.IbmCloudClientHandicapCapacityT;
import com.ibm.old.v1.cloud.ibm_handicap.t.entity.IbmHandicapT;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmCloudClientHandicapCapacityTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmCloudClientHandicapCapacityT对象数据
	 */
	public String save(IbmCloudClientHandicapCapacityT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_cloud_client_handicap_capacity的 IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cloud_client_handicap_capacity set state_='DEL' where IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_主键id数组的数据
	 * @param idArray 要删除ibm_cloud_client_handicap_capacity的 IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_cloud_client_handicap_capacity set state_='DEL' where IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_cloud_client_handicap_capacity的 IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cloud_client_handicap_capacity where IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_主键id数组的数据
	 * @param idArray 要删除ibm_cloud_client_handicap_capacity的 IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_cloud_client_handicap_capacity where IBM_CLOUD_CLIENT_HANDICAP_CAPACITY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCloudClientHandicapCapacityT实体信息
	 * @param entity IbmCloudClientHandicapCapacityT实体
	 */
	public void update(IbmCloudClientHandicapCapacityT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cloud_client_handicap_capacity表主键查找IbmCloudClientHandicapCapacityT实体
	 * @param id ibm_cloud_client_handicap_capacity 主键
	 * @return IbmCloudClientHandicapCapacityT实体
	 */
	public IbmCloudClientHandicapCapacityT find(String id) throws Exception {
		return (IbmCloudClientHandicapCapacityT) this.dao.find(IbmCloudClientHandicapCapacityT. class,id);

	}

	/**
	 * 获取分页Map数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cloud_client_handicap_capacity where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_client_handicap_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_client_handicap_capacity  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmCloudClientHandicapCapacityT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmCloudClientHandicapCapacityT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cloud_client_handicap_capacity where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_client_handicap_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_client_handicap_capacity  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmCloudClientHandicapCapacityT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client_handicap_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmCloudClientHandicapCapacityT数据信息
	 * @return 可用<IbmCloudClientHandicapCapacityT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client_handicap_capacity  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmCloudClientHandicapCapacityT. class,sql);
	}

	/**
	 * 更新客户端盘口信息
	 *
	 * @param clientCode          客户端code
	 * @param handicapCode        盘口code
	 * @param handicapExitsCount  盘口已存在数量
	 * @param handicapCapacityMax 盘口最大容量
	 * @return IBM_中心端客户端盘口主键
	 */
	public String updateClientHandicapInfo(String clientCode, String handicapCode, String handicapExitsCount,
			String handicapCapacityMax) throws Exception {
		IbmCloudClientHandicapCapacityT clientHandicapCapacityT = this.findByCode(clientCode, handicapCode);
		//已经存在此记录
		if (clientHandicapCapacityT != null) {
			clientHandicapCapacityT.setCapacity(handicapExitsCount);
			clientHandicapCapacityT.setCapacityMax(handicapCapacityMax);
			clientHandicapCapacityT.setUpdateTime(new Date());
			clientHandicapCapacityT.setUpdateTimeLong(clientHandicapCapacityT.getUpdateTime().getTime());
			if (handicapExitsCount.equals(handicapCapacityMax)){
				clientHandicapCapacityT.setState(IbmStateEnum.FULL.name());
			}else{
				clientHandicapCapacityT.setState(IbmStateEnum.OPEN.name());
			}
			this.update(clientHandicapCapacityT);
			return clientHandicapCapacityT.getIbmCloudClientHandicapCapacityId();
		}
		//查找客户端注册信息
		IbmCloudClientCapacityTService clientCapacityTService = new IbmCloudClientCapacityTService();
		IbmCloudClientCapacityT clientCapacityT = clientCapacityTService.findByClientCode(clientCode);

		IbmHandicapTService handicapTService = new IbmHandicapTService();
		IbmHandicapT handicapT = handicapTService.findByCode(handicapCode);

		clientHandicapCapacityT = new IbmCloudClientHandicapCapacityT();
		clientHandicapCapacityT.setCloudClientCapacityId(clientCapacityT.getIbmCloudClientCapacityId());
		clientHandicapCapacityT.setCloudClientId(clientCapacityT.getCloudClientId());
		clientHandicapCapacityT.setClientCode(clientCode);
		clientHandicapCapacityT.setHandicapId(handicapT.getIbmHandicapId());
		clientHandicapCapacityT.setHandicapCode(handicapCode);
		clientHandicapCapacityT.setCapacityMax(handicapCapacityMax);
		clientHandicapCapacityT.setCapacity(handicapExitsCount);
		clientHandicapCapacityT.setCreateTime(new Date());
		clientHandicapCapacityT.setCreateTimeLong(clientHandicapCapacityT.getCreateTime().getTime());
		clientHandicapCapacityT.setUpdateTime(new Date());
		clientHandicapCapacityT.setUpdateTimeLong(clientHandicapCapacityT.getUpdateTime().getTime());
		clientHandicapCapacityT.setState(IbmStateEnum.OPEN.name());
		clientHandicapCapacityT.setDesc(CloseClientController.class.getName()+"更新客户端盘口信息");
		return this.save(clientHandicapCapacityT);
	}

	/**
	 * 根据客户端code获取盘口code获取客户端盘口信息
	 *
	 * @param clientCode   客户端code
	 * @param handicapCode 盘口code
	 * @return 客户端盘口信息
	 */
	private IbmCloudClientHandicapCapacityT findByCode(String clientCode, String handicapCode) throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client_handicap_capacity  where CLIENT_CODE_ = ? and HANDICAP_CODE_ = ? and state_!='DEL'";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(clientCode);
		parameters.add(handicapCode);
		return (IbmCloudClientHandicapCapacityT) super.dao.findObject(IbmCloudClientHandicapCapacityT.class, sql, parameters);
	}

	/**
	 * 根据客户端code逻辑删除客户端已有信息
	 * @param clientCode 客户端code
	 */
	public void delByClientCode(String clientCode,String className) throws SQLException {
		String sql = "update ibm_cloud_client_handicap_capacity set state_='DEL',DESC_=? ,UPDATE_TIME_=?, UPDATE_TIME_LONG_=? where CLIENT_CODE_= ? ";
		Date nowTime=new Date();
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(className+"根据客户端code逻辑删除客户端已有信息");
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(clientCode);
		super.dao.execute(sql,parameterList);
	}
}
