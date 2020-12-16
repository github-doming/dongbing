package com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.entity.IbmCloudClientHmT;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmCloudClientHmTService extends BaseServicePlus {



	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmCloudClientHmT对象数据
	 */
	public String save(IbmCloudClientHmT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_cloud_client_hm的 IBM_CLOUD_CLIENT_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cloud_client_hm set state_='DEL' where IBM_CLOUD_CLIENT_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLOUD_CLIENT_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cloud_client_hm的 IBM_CLOUD_CLIENT_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_cloud_client_hm set state_='DEL' where IBM_CLOUD_CLIENT_HM_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_cloud_client_hm的 IBM_CLOUD_CLIENT_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cloud_client_hm where IBM_CLOUD_CLIENT_HM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLOUD_CLIENT_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cloud_client_hm的 IBM_CLOUD_CLIENT_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_cloud_client_hm where IBM_CLOUD_CLIENT_HM_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCloudClientHmT实体信息
	 *
	 * @param entity IbmCloudClientHmT实体
	 */
	public void update(IbmCloudClientHmT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cloud_client_hm表主键查找IbmCloudClientHmT实体
	 *
	 * @param id ibm_cloud_client_hm 主键
	 * @return IbmCloudClientHmT实体
	 */
	public IbmCloudClientHmT find(String id) throws Exception {
		return (IbmCloudClientHmT) this.dao.find(IbmCloudClientHmT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_cloud_client_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_client_hm  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmCloudClientHmT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmCloudClientHmT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cloud_client_hm where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_client_hm  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmCloudClientHmT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmCloudClientHmT数据信息
	 *
	 * @return 可用<IbmCloudClientHmT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_client_hm  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmCloudClientHmT.class, sql);
	}

	/**
	 * 更新中心端客户端盘口会员状态
	 *
	 * @param clientCode 客户端code
	 * @param exitsData  存在数据
	 */
	public void updateState(String clientCode, JSONArray exitsData,String className) throws SQLException {
		//关闭所有的已开启的客户端会员
		Date nowTime=new Date();
		String sql = "update ibm_cloud_client_hm set state_ = ? ,DESC_ =? , UPDATE_TIME_=? , UPDATE_TIME_LONG_ =? where CLIENT_CODE_ = ? and state_!='DEL' ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(className+"关闭所有的已开启的客户端会员");
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		parameters.add(clientCode);
		int result = super.dao.execute(sql, parameters);
		if (result == 0 ||  ContainerTool.isEmpty(exitsData)) {
			return;
		}
		//开启回传的客户端会员
		sql = "update ibm_cloud_client_hm set state_ = ? , DESC_ =?,UPDATE_TIME_=? ,UPDATE_TIME_LONG_ =? where CLIENT_CODE_ = ? and state_ = ? ";
		parameters.clear();
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(className +"开启回传的客户端会员");
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		parameters.add(clientCode);
		parameters.add(IbmStateEnum.DEL.name());
		StringBuilder builder = new StringBuilder(" and CLIENT_EXIST_HM_ID_ in (");
		for (int i = 0, size = exitsData.size(); i < size; i++) {
			JSONObject jObj = exitsData.getJSONObject(i);
			builder.append("?,");
			parameters.add(jObj.getString("CLIENT_EXIST_HM_ID_"));
		}
		builder.deleteCharAt(builder.lastIndexOf(",")).append(") ");
		sql += builder.toString();
		builder = new StringBuilder(" and HANDICAP_MEMBER_ID_ in (");
		for (int i = 0, size = exitsData.size(); i < size; i++) {
			JSONObject jObj = exitsData.getJSONObject(i);
			builder.append("?,");
			parameters.add(jObj.getString("HANDICAP_MEMBER_ID_"));
		}
		builder.deleteCharAt(builder.lastIndexOf(",")).append(") ");
		sql += builder.toString();
		super.dao.execute(sql, parameters);
	}

	/**
	 * 通过盘口会员主键查询客户端已存在盘口会员主键
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 客户端已存在盘口会员
	 */
	public IbmCloudClientHmT findByHandicapMemberId(String handicapMemberId) throws Exception {
		String sql = "select * from ibm_cloud_client_hm where HANDICAP_MEMBER_ID_= ? and STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return (IbmCloudClientHmT) super.dao.findObject(IbmCloudClientHmT.class, sql, parameterList);
	}

	/**
	 * 根据客户端code逻辑删除已有客户端信息
	 *
	 * @param clientCode 客户端code
	 */
	public void delByClientCode(String clientCode,String className) throws SQLException {
		Date nowTime=new Date();
		String sql = "update ibm_cloud_client_hm set state_='DEL', DESC_=?, UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where CLIENT_CODE_= ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(className+"根据客户端code逻辑删除已有客户端信息");
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(clientCode);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取客户端存在盘口会员idMap
	 * @param handicapMemberIds 盘口会员列表
	 * @return 客户端存在盘口会员idMap
	 */
	public Map<Object, Object> mapClientExistHmId(Set<String> handicapMemberIds) throws SQLException {
		String sql = "select HANDICAP_MEMBER_ID_ as key_,CLIENT_EXIST_HM_ID_ as value_ from ibm_cloud_client_hm where 1=1 ";
		StringBuilder sqlBuild = new StringBuilder(" and HANDICAP_MEMBER_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		for (String handicapMemberId : handicapMemberIds) {
			sqlBuild.append(" ?, ");
			parameterList.add(handicapMemberId);
		}
		sqlBuild.deleteCharAt(sqlBuild.lastIndexOf(",")).append(" )").append(" and STATE_ != ?");
		sql += sqlBuild.toString();
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 通过盘口会员主键查询客户端已存在盘口会员主键
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 客户端已存在盘口会员
	 */
	public String findExistHmId(String handicapMemberId) throws Exception {
		super.doTransactionPost();
		String sql = "select CLIENT_EXIST_HM_ID_ from ibm_cloud_client_hm where HANDICAP_MEMBER_ID_= ? and STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return  super.dao.findString("CLIENT_EXIST_HM_ID_", sql, parameterList);
	}

	/**
	 * 获取该客户端下所有盘口会员id
	 * @param clientCode
	 * @return
	 */
    public List<String> listHandicapMemberId(String clientCode) throws SQLException {
    	String sql = "SELECT HANDICAP_MEMBER_ID_ FROM ibm_cloud_client_hm WHERE CLIENT_CODE_ = ? AND STATE_ != ? ";
    	List<Object> parameterList = new ArrayList<>();
    	parameterList.add(clientCode);
    	parameterList.add(IbmStateEnum.DEL.name());
    	return super.dao.findStringList("HANDICAP_MEMBER_ID_",sql,parameterList);
    }
	/**
	 *
	 * @param clientExistHmId		已存在盘口会员id
	 * @return
	 */
	public String findHandicapMemberId(String clientExistHmId) throws Exception {
		String sql = "select HANDICAP_MEMBER_ID_ from ibm_cloud_client_hm where CLIENT_EXIST_HM_ID_= ? and STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(clientExistHmId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("HANDICAP_MEMBER_ID_", sql, parameterList);
	}
	/**
	 * 获取客户端编码和已存在盘口会员
	 * @param handicapMemberIds	盘口会员ids
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findExistHmIds(List<String> handicapMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT CLIENT_EXIST_HM_ID_,HANDICAP_MEMBER_ID_ FROM ibm_cloud_client_hm WHERE STATE_!=? AND HANDICAP_MEMBER_ID_ IN(");
		List<Object> parameterList = new ArrayList<>(handicapMemberIds.size()+1);
		parameterList.add(IbmStateEnum.DEL.name());
		for (String handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(" )").append(" ORDER BY CLIENT_CODE_");
		return super.dao.findMapList(sql.toString(), parameterList);
	}

	/**
	 * 获取客户端存在盘口会员idMap
	 * @param handicapMemberId 盘口会员id
	 * @return 客户端存在盘口会员idMap
	 */
	public String findClientExistHmId(String handicapMemberId) throws SQLException {
		String sql = "select CLIENT_EXIST_HM_ID_ from ibm_cloud_client_hm where HANDICAP_MEMBER_ID_ = ? and STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("CLIENT_EXIST_HM_ID_",sql,parameterList);
	}
}
