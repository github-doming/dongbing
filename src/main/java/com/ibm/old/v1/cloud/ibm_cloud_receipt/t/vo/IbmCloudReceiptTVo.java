package com.ibm.old.v1.cloud.ibm_cloud_receipt.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_cloud_receipt 
 * vo类
 * @author Robot
 */

public class IbmCloudReceiptTVo implements Serializable {

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
	 * IBM_中心端客户端消息回执主键
	 */
	private String ibmCloudReceiptId;
	public void setIbmCloudReceiptId(String ibmCloudReceiptId) {
		this.ibmCloudReceiptId=ibmCloudReceiptId;
	}
	public String getIbmCloudReceiptId() {
		return this.ibmCloudReceiptId ;
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
	 * 盘口会员主键
	 */
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}
	
	/**
	 * 消息方法
	 */
	private String messageMethod;
	public void setMessageMethod(String messageMethod) {
		this.messageMethod=messageMethod;
	}
	public String getMessageMethod() {
		return this.messageMethod ;
	}
	
	/**
	 * 回执状态
	 */
	private String receiptState;
	public void setReceiptState(String receiptState) {
		this.receiptState=receiptState;
	}
	public String getReceiptState() {
		return this.receiptState ;
	}
	
	/**
	 * 处理结果
	 */
	private String processResult;
	public void setProcessResult(String processResult) {
		this.processResult=processResult;
	}
	public String getProcessResult() {
		return this.processResult ;
	}
	
	/**
	 * 创建时间
	 */
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
	/**
	 * 创建时间
	 */
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
	/**
	 * 更新时间
	 */
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
	/**
	 * 更新时间
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