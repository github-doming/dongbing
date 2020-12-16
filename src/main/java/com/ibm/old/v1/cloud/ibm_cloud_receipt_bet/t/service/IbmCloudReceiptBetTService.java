package com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.entity.IbmCloudReceiptBetT;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import net.sf.json.JSONArray;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmCloudReceiptBetTService extends BaseService {



	/**
	 * 保存{ay_table_class}对象数据
	 * @param entity IbmCloudReceiptBetT对象数据
	 */
	public String save(IbmCloudReceiptBetT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_cloud_receipt_bet的 IBM_CLOUD_RECEIPT_BET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cloud_receipt_bet set state_='DEL' where IBM_CLOUD_RECEIPT_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLOUD_RECEIPT_BET_ID_主键id数组的数据
	 * @param idArray 要删除ibm_cloud_receipt_bet的 IBM_CLOUD_RECEIPT_BET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_cloud_receipt_bet set state_='DEL' where IBM_CLOUD_RECEIPT_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除ibm_cloud_receipt_bet的 IBM_CLOUD_RECEIPT_BET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cloud_receipt_bet where IBM_CLOUD_RECEIPT_BET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLOUD_RECEIPT_BET_ID_主键id数组的数据
	 * @param idArray 要删除ibm_cloud_receipt_bet的 IBM_CLOUD_RECEIPT_BET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_cloud_receipt_bet where IBM_CLOUD_RECEIPT_BET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCloudReceiptBetT实体信息
	 * @param entity IbmCloudReceiptBetT实体
	 */
	public void update(IbmCloudReceiptBetT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cloud_receipt_bet表主键查找IbmCloudReceiptBetT实体
	 * @param id ibm_cloud_receipt_bet 主键
	 * @return IbmCloudReceiptBetT实体
	 */
	public IbmCloudReceiptBetT find(String id) throws Exception {
		return (IbmCloudReceiptBetT) this.dao.find(IbmCloudReceiptBetT. class,id);

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
		String sqlCount = "SELECT count(*) FROM ibm_cloud_receipt_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_receipt_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_receipt_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmCloudReceiptBetT数据
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex 页面索引
	 * @param pageSize 页面大小
	 * @return 分页IbmCloudReceiptBetT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex,
			Integer pageSize) throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cloud_receipt_bet where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_receipt_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_receipt_bet  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmCloudReceiptBetT. class,sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_receipt_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmCloudReceiptBetT数据信息
	 * @return 可用<IbmCloudReceiptBetT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_receipt_bet  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmCloudReceiptBetT. class,sql);
	}

	/**
	 * 更新消息回执结果
	 * @param cloudReceiptBetId  消息回执id
	 * @param processResult 		处理结果
	 * @return 更新结果
	 */
	public boolean updateResult(String cloudReceiptBetId, List<Map<String, Object>> processResult,String className) throws SQLException {
		String sql = "UPDATE `ibm_cloud_receipt_bet` SET PROCESS_RESULT_ = CONCAT_WS(',',PROCESS_RESULT_,?),"
				+ " RECEIPT_STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? ,DESC_=?"
				+ " WHERE IBM_CLOUD_RECEIPT_BET_ID_ = ? and (RECEIPT_STATE_ = ? or RECEIPT_STATE_ = ?)";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(JSONArray.fromObject(processResult).toString());
		parameterList.add(IbmStateEnum.FINISH.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(className+"更新消息回执结果");
		parameterList.add(cloudReceiptBetId);
		parameterList.add(IbmStateEnum.SUCCESS.name());
		parameterList.add(IbmStateEnum.FINISH.name());
		return super.dao.execute(sql,parameterList) > 0;
	}
}
