package com.ibm.old.v1.cloud.ibm_handicap.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_handicap 
 * vo类
 * @author Robot
 */

public class IbmHandicapTVo implements Serializable {

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
	 * IBM_盘口主键IBM_HANDICAP_ID_
	 */
	private String ibmHandicapId;
	public void setIbmHandicapId(String ibmHandicapId) {
		this.ibmHandicapId=ibmHandicapId;
	}
	public String getIbmHandicapId() {
		return this.ibmHandicapId ;
	}
	
	/**
	 * 盘口名称
	 */
	private String handicapName;
	public void setHandicapName(String handicapName) {
		this.handicapName=handicapName;
	}
	public String getHandicapName() {
		return this.handicapName ;
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
	 * 盘口说明
	 */
	private String handicapExplain;
	public void setHandicapExplain(String handicapExplain) {
		this.handicapExplain=handicapExplain;
	}
	public String getHandicapExplain() {
		return this.handicapExplain ;
	}
	
	/**
	 * 盘口类型
	 */
	private String handicapType;
	public void setHandicapType(String handicapType) {
		this.handicapType=handicapType;
	}
	public String getHandicapType() {
		return this.handicapType ;
	}
	
	/**
	 * 盘口价值
	 */
	private String handicapWorthT;
	public void setHandicapWorthT(String handicapWorthT) {
		this.handicapWorthT=handicapWorthT;
	}
	public String getHandicapWorthT() {
		return this.handicapWorthT ;
	}
	
	/**
	 * 盘口版本
	 */
	private String handicapVersion;
	public void setHandicapVersion(String handicapVersion) {
		this.handicapVersion=handicapVersion;
	}
	public String getHandicapVersion() {
		return this.handicapVersion ;
	}
	
	/**
	 * 方案图标
	 */
	private String handicapIcon;
	public void setHandicapIcon(String handicapIcon) {
		this.handicapIcon=handicapIcon;
	}
	public String getHandicapIcon() {
		return this.handicapIcon ;
	}
	
	/**
	 * 次序
	 */
	private String sn;
	public void setSn(String sn) {
		this.sn=sn;
	}
	public String getSn() {
		return this.sn ;
	}
	
	/**
	 * 创建者CREATE_USER_
	 */
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
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
	 * 更新者UPDATE_USER_
	 */
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
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