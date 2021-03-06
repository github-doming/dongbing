package com.ibm.old.v1.client.ibm_client_bet_error.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_client_bet_error 
 * vo类
 * @author Robot
 */

public class IbmClientBetErrorTVo implements Serializable {

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
	 * IBM_客户端错误投注信息主键
	 */
	private String ibmClientBetErrorId;
	public void setIbmClientBetErrorId(String ibmClientBetErrorId) {
		this.ibmClientBetErrorId=ibmClientBetErrorId;
	}
	public String getIbmClientBetErrorId() {
		return this.ibmClientBetErrorId ;
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
	 * IBM_客户端已存投注信息主键
	 */
	private String clientExistBetId;
	public void setClientExistBetId(String clientExistBetId) {
		this.clientExistBetId=clientExistBetId;
	}
	public String getClientExistBetId() {
		return this.clientExistBetId ;
	}
	
	/**
	 * 执行投注项
	 */
	private String execBetItemId;
	public void setExecBetItemId(String execBetItemId) {
		this.execBetItemId=execBetItemId;
	}
	public String getExecBetItemId() {
		return this.execBetItemId ;
	}
	
	/**
	 * 消息回执投注
	 */
	private String cloudReceiptBetId;
	public void setCloudReceiptBetId(String cloudReceiptBetId) {
		this.cloudReceiptBetId=cloudReceiptBetId;
	}
	public String getCloudReceiptBetId() {
		return this.cloudReceiptBetId ;
	}
	
	/**
	 * 盘口会员
	 */
	private String handicapMemberId;
	public void setHandicapMemberId(String handicapMemberId) {
		this.handicapMemberId=handicapMemberId;
	}
	public String getHandicapMemberId() {
		return this.handicapMemberId ;
	}
	
	/**
	 * 游戏编码
	 */
	private String gameCode;
	public void setGameCode(String gameCode) {
		this.gameCode=gameCode;
	}
	public String getGameCode() {
		return this.gameCode ;
	}
	
	/**
	 * 期数
	 */
	private String period;
	public void setPeriod(String period) {
		this.period=period;
	}
	public String getPeriod() {
		return this.period ;
	}
	
	/**
	 * 错误投注信息
	 */
	private String errorBetInfo;
	public void setErrorBetInfo(String errorBetInfo) {
		this.errorBetInfo=errorBetInfo;
	}
	public String getErrorBetInfo() {
		return this.errorBetInfo ;
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