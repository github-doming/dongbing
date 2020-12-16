package com.ibm.old.v1.cloud.ibm_cloud_receipt.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.service.BaseService;
import com.ibm.old.v1.cloud.ibm_cloud_receipt.t.entity.IbmCloudReceiptT;
import com.ibm.old.v1.common.doming.enums.IbmMethodEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmCloudReceiptTService extends BaseService {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmCloudReceiptT对象数据
	 */
	public String save(IbmCloudReceiptT entity) throws Exception {
		String cloudReceiptId = dao.save(entity);
		super.doTransactionPost();
		return cloudReceiptId;
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_cloud_receipt的 IBM_CLOUD_RECEIPT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_cloud_receipt set state_='DEL' where IBM_CLOUD_RECEIPT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_CLOUD_RECEIPT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cloud_receipt的 IBM_CLOUD_RECEIPT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_cloud_receipt set state_='DEL' where IBM_CLOUD_RECEIPT_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_cloud_receipt的 IBM_CLOUD_RECEIPT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_cloud_receipt where IBM_CLOUD_RECEIPT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_CLOUD_RECEIPT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_cloud_receipt的 IBM_CLOUD_RECEIPT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_cloud_receipt where IBM_CLOUD_RECEIPT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmCloudReceiptT实体信息
	 *
	 * @param entity IbmCloudReceiptT实体
	 */
	public void update(IbmCloudReceiptT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_cloud_receipt表主键查找IbmCloudReceiptT实体
	 *
	 * @param id ibm_cloud_receipt 主键
	 * @return IbmCloudReceiptT实体
	 */
	public IbmCloudReceiptT find(String id) throws Exception {
		return (IbmCloudReceiptT) this.dao.find(IbmCloudReceiptT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_cloud_receipt where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_receipt  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_receipt  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmCloudReceiptT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmCloudReceiptT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_cloud_receipt where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_cloud_receipt  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_cloud_receipt  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmCloudReceiptT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_receipt  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmCloudReceiptT数据信息
	 *
	 * @return 可用<IbmCloudReceiptT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_cloud_receipt  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmCloudReceiptT.class, sql);
	}

	/**
	 * 查询消息回执id
	 *
	 * @param clientExistHmId 客户端已存在盘口会员主键
	 * @return 消息回执id
	 */
	public String findId(String clientExistHmId, String messageMethod) throws SQLException {
		String sql = "select IBM_CLOUD_RECEIPT_ID_ as messageReceiptId from ibm_cloud_receipt where CLIENT_EXIST_HM_ID_=? and MESSAGE_METHOD_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(clientExistHmId);
		parameterList.add(messageMethod);
		parameterList.add(IbmStateEnum.DEL.name());
		return this.dao.findString("messageReceiptId", sql, parameterList);
	}

	/**
	 * 获取对象
	 *
	 * @param clientExistHmId 客户端已存在盘口会员主键
	 * @param messageMethod   消息方法
	 * @return 中心端客户端消息回执
	 */
	public IbmCloudReceiptT find(String clientExistHmId, String messageMethod) throws Exception {
		String sql =
				"select * from ibm_cloud_receipt where CLIENT_EXIST_HM_ID_= ? and MESSAGE_METHOD_=? and STATE_ = ? "
						+ " AND MESSAGE_METHOD_ = ? and UPDATE_TIME_LONG_ > unix_timestamp(DATE_SUB(now(), INTERVAL 5 MINUTE)) * 1000";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(clientExistHmId);
		parameterList.add(messageMethod);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.PROCESS.name());
		return (IbmCloudReceiptT) this.dao.findObject(IbmCloudReceiptT.class, sql, parameterList);
	}

	/**
	 * 获取回执状态
	 *
	 * @param receiptId 消息回执主键
	 * @return 回执状态
	 */
	public IbmStateEnum findReceiptState(String receiptId) throws Exception {
		super.doTransactionPost();
		String sql = "select RECEIPT_STATE_ from ibm_cloud_receipt where IBM_CLOUD_RECEIPT_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(receiptId);
		parameterList.add(IbmStateEnum.DEL.name());
		return IbmStateEnum.getReceiptState(super.dao.findString("RECEIPT_STATE_", sql, parameterList));
	}

	/**
	 * 获取处理结果
	 *
	 * @param receiptId 消息回执主键
	 * @return 处理结果
	 */
	public String findProcessResult(String receiptId) throws SQLException {
		String sql = "select PROCESS_RESULT_ from ibm_cloud_receipt where IBM_CLOUD_RECEIPT_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(receiptId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("PROCESS_RESULT_", sql, parameterList);
	}

	/**
	 * 获取待修改的消息回执对象
	 *
	 * @param receiptId 回执消息id
	 * @param state     回执方法
	 * @return 待修改的消息回执对象
	 */
	public IbmCloudReceiptT findRevise(String receiptId, IbmStateEnum state) throws Exception {
		String sql = "SELECT * FROM ibm_cloud_receipt WHERE IBM_CLOUD_RECEIPT_ID_ = ? AND RECEIPT_STATE_ != ? AND "
				+ " RECEIPT_STATE_ != ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(receiptId);
		parameterList.add(IbmStateEnum.FINISH.name());
		parameterList.add(state.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return (IbmCloudReceiptT) super.dao.findObject(IbmCloudReceiptT.class, sql, parameterList);

	}

	/**
	 * 更新一个新的消息回执
	 *
	 * @param receiptId 回执消息id
	 * @param method    执行方法
	 * @param nowTime   更新时间
	 */
	public void updateNewMessageReceipt(String receiptId, IbmMethodEnum method, Date nowTime,String className) throws SQLException {
		String sql = "UPDATE `ibm_cloud_receipt` SET PROCESS_RESULT_ = NULL, DESC_=?  RECEIPT_STATE_ = ?, UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ? WHERE IBM_CLOUD_RECEIPT_ID_ = ? AND MESSAGE_METHOD_ = ? AND RECEIPT_STATE_ = ?";

		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(className+"更新一个新的消息回执");
		parameterList.add(IbmStateEnum.SEND.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(receiptId);
		parameterList.add(method.name());
		parameterList.add(IbmStateEnum.PROCESS.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 新建一个消息回执
	 *
	 * @param existHmId        存在客户端盘口会员id
	 * @param handicapMemberId 盘口会员id
	 * @param method           执行方法
	 * @param nowTime          当前时间
	 * @return 消息回执id
	 */
	public String newMessageReceipt(String existHmId, String handicapMemberId, IbmMethodEnum method, Date nowTime)
			throws Exception {
		IbmCloudReceiptT receiptT = new IbmCloudReceiptT();
		receiptT.setClientExistHmId(existHmId);
		receiptT.setHandicapMemberId(handicapMemberId);
		receiptT.setMessageMethod(method.name());
		receiptT.setReceiptState(IbmStateEnum.SEND.name());
		receiptT.setCreateTime(nowTime);
		receiptT.setCreateTimeLong(nowTime.getTime());
		receiptT.setUpdateTime(nowTime);
		receiptT.setUpdateTimeLong(nowTime.getTime());
		receiptT.setState(IbmStateEnum.OPEN.name());
		return save(receiptT);
	}
}
