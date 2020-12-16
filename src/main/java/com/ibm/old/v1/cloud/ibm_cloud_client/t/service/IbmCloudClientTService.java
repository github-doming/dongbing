package com.ibm.old.v1.cloud.ibm_cloud_client.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_cloud_client.t.entity.IbmCloudClientT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmCloudClientTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmCloudClientT对象数据
	 */
	public String save(IbmCloudClientT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_cloud_client的 IBM_CLOUD_CLIENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cloud_client set state_='DEL' where IBM_CLOUD_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();

		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLOUD_CLIENT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cloud_client的 IBM_CLOUD_CLIENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_cloud_client set state_='DEL' where IBM_CLOUD_CLIENT_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_cloud_client的 IBM_CLOUD_CLIENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cloud_client where IBM_CLOUD_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLOUD_CLIENT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cloud_client的 IBM_CLOUD_CLIENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_cloud_client where IBM_CLOUD_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCloudClientT实体信息
	 *
	 * @param entity IbmCloudClientT实体
	 */
	public void update(IbmCloudClientT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cloud_client表主键查找IbmCloudClientT实体
	 *
	 * @param id ibm_cloud_client 主键
	 * @return IbmCloudClientT实体
	 */
	public IbmCloudClientT find(String id) throws Exception {
		return (IbmCloudClientT) this.dao.find(IbmCloudClientT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_cloud_client where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_client  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_client  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmCloudClientT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmCloudClientT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cloud_client where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_client  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_client  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmCloudClientT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmCloudClientT数据信息
	 *
	 * @return 可用<IbmCloudClientT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmCloudClientT.class, sql);
	}
	/**
	 * 根据客户端code获取客户端信息
	 *
	 * @param clientCode 客户端code
	 * @return 客户端信息
	 */
	public IbmCloudClientT findByClientCode(String clientCode) throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client  where CLIENT_CODE_ = ? and state_!='DEL'";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(clientCode);
		return (IbmCloudClientT) super.dao.findObject(IbmCloudClientT.class, sql, parameters);
	}

	/**
	 * 更新注册客户端状态
	 *
	 * @param registerClientId 注册客户端id
	 * @param state            状态
	 */
	public void updateState(String registerClientId, String state,String className) throws SQLException {
		Date nowTime=new Date();
		String sql = "update ibm_register_client set state_ = ? , DESC_ =? ,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_REGISTER_CLIENT_ID_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(state);
		parameters.add(className+"更新注册客户端状态");
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		parameters.add(registerClientId);
		super.dao.execute(sql, parameters);
	}
	/**
	 * 找出一台可用的客户端code
	 *
	 * @param handicapCode 盘口code
	 * @return 客户端code
	 */
	public String findReadyClient(String handicapCode) throws SQLException {
		String sql = " SELECT icc.CLIENT_CODE_ FROM `ibm_cloud_client` icc "
				+ " LEFT JOIN ibm_cloud_client_capacity iccc ON icc.IBM_CLOUD_CLIENT_ID_ = iccc.CLOUD_CLIENT_ID_ "
				+ " LEFT JOIN ibm_cloud_client_handicap_capacity icchc ON icc.IBM_CLOUD_CLIENT_ID_ = icchc.CLOUD_CLIENT_ID_ "
				+ " AND icchc.HANDICAP_CODE_ = ? WHERE icc.STATE_ = ? "
				+ " AND (iccc.STATE_ = ? OR iccc.STATE_ IS NULL) AND (icchc.STATE_ =  ?  OR icchc.STATE_ IS NULL) LIMIT 1 ";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(handicapCode);
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("CLIENT_CODE_", sql, parameters);

	}
}
