package com.ibm.old.v1.cloud.ibm_cloud_receipt_bet.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cloud_receipt_bet 
 * vo类
 * @author Robot
 */

public class IbmCloudReceiptBetTVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 索引
	 */
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
	/**
	 * IBM_中心端客户端投注消息回执主键IBM_CLOUD_CLIENT_MESSAGE_RECEIPT_BET_ID_
	 */
	private String ibmCloudReceiptBetId;
	public void setIbmCloudReceiptBetId(String ibmCloudReceiptBetId) {
		this.ibmCloudReceiptBetId=ibmCloudReceiptBetId;
	}
	public String getIbmCloudReceiptBetId() {
		return this.ibmCloudReceiptBetId ;
	}
	
	/**
	 * 客户端已存在盘口会员主键
	 */
	private String clientExistHmId;
	public void setClientExistHmId(String clientExistHmId) {
		this.clientExistHmId=clientExistHmId;
	}
	public String getClientExistHmId() {
		return this.clientExistHmId ;
	}
	
	/**
	 * IBM_执行投注项主键
	 */
	private String execBetItemIds;
	public void setExecBetItemIds(String execBetItemIds) {
		this.execBetItemIds=execBetItemIds;
	}
	public String getExecBetItemIds() {
		return this.execBetItemIds ;
	}
	
	/**
	 * 回执状态RECEIPT_STATE_
	 */
	private String receiptState;
	public void setReceiptState(String receiptState) {
		this.receiptState=receiptState;
	}
	public String getReceiptState() {
		return this.receiptState ;
	}
	
	/**
	 * 处理结果PROCESS_RESULT_
	 */
	private String processResult;
	public void setProcessResult(String processResult) {
		this.processResult=processResult;
	}
	public String getProcessResult() {
		return this.processResult ;
	}
	
	/**
	 * 创建时间CREATE_TIME_
	 */
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
	/**
	 * 创建时间数字型CREATE_TIME_LONG_
	 */
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
	/**
	 * 更新时间UPDATE_TIME_
	 */
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
	/**
	 * 更新时间数字型UPDATE_TIME_LONG_
	 */
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
	/**
	 * DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	 */
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
	/**
	 * 描述
	 */
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}
	}