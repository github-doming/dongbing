package com.ibm.old.v1.cloud.ibm_handicap_item.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_handicap_item 
 * vo类
 * @author Robot
 */

public class IbmHandicapItemTVo implements Serializable {

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
	 * IBM_盘口详情主键IBM_HANDICAP_ITEM_ID_
	 */
	private String ibmHandicapItemId;
	public void setIbmHandicapItemId(String ibmHandicapItemId) {
		this.ibmHandicapItemId=ibmHandicapItemId;
	}
	public String getIbmHandicapItemId() {
		return this.ibmHandicapItemId ;
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
	 * 盘口地址
	 */
	private String handicapUrl;
	public void setHandicapUrl(String handicapUrl) {
		this.handicapUrl=handicapUrl;
	}
	public String getHandicapUrl() {
		return this.handicapUrl ;
	}
	
	/**
	 * 校验码
	 */
	private String handicapCaptcha;
	public void setHandicapCaptcha(String handicapCaptcha) {
		this.handicapCaptcha=handicapCaptcha;
	}
	public String getHandicapCaptcha() {
		return this.handicapCaptcha ;
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