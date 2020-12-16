package com.ibm.old.v1.cloud.ibm_handicap_member.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_handicap_member 
 * vo类
 * @author Robot
 */

public class IbmHandicapMemberTVo implements Serializable {

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
	 * IBM_盘口会员主键IBM_HANDICAP_MEMBER_ID_
	 */
	private String ibmHandicapMemberId;
	public void setIbmHandicapMemberId(String ibmHandicapMemberId) {
		this.ibmHandicapMemberId=ibmHandicapMemberId;
	}
	public String getIbmHandicapMemberId() {
		return this.ibmHandicapMemberId ;
	}
	
	/**
	 * 盘口用户主键HANDICAP_USER_ID_
	 */
	private String handicapUserId;
	public void setHandicapUserId(String handicapUserId) {
		this.handicapUserId=handicapUserId;
	}
	public String getHandicapUserId() {
		return this.handicapUserId ;
	}
	
	/**
	 * 盘口主键
	 */
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public String getHandicapId() {
		return this.handicapId ;
	}
	
	/**
	 * 盘口详情主键
	 */
	private String handicapItemId;
	public void setHandicapItemId(String handicapItemId) {
		this.handicapItemId=handicapItemId;
	}
	public String getHandicapItemId() {
		return this.handicapItemId ;
	}
	
	/**
	 * 盘口编码
	 */
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public String getHandicapCode() {
		return this.handicapCode ;
	}
	
	/**
	 * 用户主键USER_ID_
	 */
	private String appUserId;
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
	
	/**
	 * 会员账户
	 */
	private String memberAccount;
	public void setMemberAccount(String memberAccount) {
		this.memberAccount=memberAccount;
	}
	public String getMemberAccount() {
		return this.memberAccount ;
	}
	
	/**
	 * 会员密码
	 */
	private String memberPassword;
	public void setMemberPassword(String memberPassword) {
		this.memberPassword=memberPassword;
	}
	public String getMemberPassword() {
		return this.memberPassword ;
	}
	
	/**
	 * 会员信息
	 */
	private String memberInfo;
	public void setMemberInfo(String memberInfo) {
		this.memberInfo=memberInfo;
	}
	public String getMemberInfo() {
		return this.memberInfo ;
	}
	
	/**
	 * 登陆状态
	 */
	private String loginState;
	public void setLoginState(String loginState) {
		this.loginState=loginState;
	}
	public String getLoginState() {
		return this.loginState ;
	}
	
	/**
	 * 使用频次
	 */
	private String frequency;
	public void setFrequency(String frequency) {
		this.frequency=frequency;
	}
	public String getFrequency() {
		return this.frequency ;
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