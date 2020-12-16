package com.ibm.old.v1.cloud.ibm_sys_handicap.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table ibm_sys_handicap 
 * vo类
 * @author Robot
 */

public class IbmSysHandicapTVo implements Serializable {

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
	 * IBM_中心端盘口会员日志主键IBM_CLOUD_LOG_HM_ID_
	 */
	private String ibmSysHandicapId;
	public void setIbmSysHandicapId(String ibmSysHandicapId) {
		this.ibmSysHandicapId=ibmSysHandicapId;
	}
	public String getIbmSysHandicapId() {
		return this.ibmSysHandicapId ;
	}
	
	/**
	 * 盘口主键HANDICAP_ID_
	 */
	private String handicapId;
	public void setHandicapId(String handicapId) {
		this.handicapId=handicapId;
	}
	public String getHandicapId() {
		return this.handicapId ;
	}
	/**
	 * 盘口编码HANDICAP_CODE_
	 */
	private String handicapCode;
	public void setHandicapCode(String handicapCode) {
		this.handicapCode=handicapCode;
	}
	public String getHandicapCode() {
		return this.handicapCode ;
	}
	
	/**
	 * 盘口检测时间HANDICAP_DETECTION_TIME_
	 */
	private String handicapDetectionTime;
	public void setHandicapDetectionTime(String handicapDetectionTime) {
		this.handicapDetectionTime=handicapDetectionTime;
	}
	public String getHandicapDetectionTime() {
		return this.handicapDetectionTime ;
	}
	
	/**
	 * 上次校验时间LAST_CHECK_TIME_
	 */
	private String lastCheckTime;
	public void setLastCheckTime(String lastCheckTime) {
		this.lastCheckTime=lastCheckTime;
	}
	public String getLastCheckTime() {
		return this.lastCheckTime ;
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