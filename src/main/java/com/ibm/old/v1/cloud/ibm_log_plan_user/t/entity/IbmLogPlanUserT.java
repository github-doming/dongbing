package com.ibm.old.v1.cloud.ibm_log_plan_user.t.entity;

import c.a.util.core.annotation.AnnotationEntity;
import c.a.util.core.annotation.AnnotationTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the database table ibm_log_plan_user 
 * IBM_用户方案操作日志表IBM_LOG_PLAN_USER的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "ibm_log_plan_user")
public class IbmLogPlanUserT implements Serializable {

	private static final long serialVersionUID = 1L;
				
			
//索引
@Column(name="IDX_")
	private Long idx;
	
	public void setIdx(Long idx) {
		this.idx=idx;
	}
	public Long getIdx() {
		return this.idx ;
	}
		@Id
	// 根据底层数据库
	@GeneratedValue(strategy = GenerationType.AUTO)
		
			
//IBM_用户方案操作日志主键IBM_LOG_PLAN_USER_ID_
@Column(name="IBM_LOG_PLAN_USER_ID_")
	private String ibmLogPlanUserId;
	
	public void setIbmLogPlanUserId(String ibmLogPlanUserId) {
		this.ibmLogPlanUserId=ibmLogPlanUserId;
	}
	public String getIbmLogPlanUserId() {
		return this.ibmLogPlanUserId ;
	}
			
			
//方案主键PLAN_ID_
@Column(name="PLAN_ID_")
	private String planId;
	
	public void setPlanId(String planId) {
		this.planId=planId;
	}
	public String getPlanId() {
		return this.planId ;
	}
			
			
//用户主键APP_USER_ID_
@Column(name="APP_USER_ID_")
	private String appUserId;
	
	public void setAppUserId(String appUserId) {
		this.appUserId=appUserId;
	}
	public String getAppUserId() {
		return this.appUserId ;
	}
			
			
//游戏主键GAME_ID_
@Column(name="GAME_ID_")
	private String gameId;
	
	public void setGameId(String gameId) {
		this.gameId=gameId;
	}
	public String getGameId() {
		return this.gameId ;
	}
			
			
//操作类型HANDLE_TYPE_
@Column(name="HANDLE_TYPE_")
	private String handleType;
	
	public void setHandleType(String handleType) {
		this.handleType=handleType;
	}
	public String getHandleType() {
		return this.handleType ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间CREATE_TIME_
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间数字型CREATE_TIME_LONG_
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间UPDATE_TIME_
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//更新时间数字型UPDATE_TIME_LONG_
@Column(name="UPDATE_TIME_LONG_")
	private Long updateTimeLong;
	
	public void setUpdateTimeLong(Long updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public Long getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
			
			
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
@Column(name="STATE_")
	private String state;
	
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
			
			
//描述
@Column(name="DESC_")
	private String desc;
	
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}

	private String tableNameMy;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTableNameMy() {
		return tableNameMy;
	}

	public void setTableNameMy(String tableNameMy) {
		this.tableNameMy = tableNameMy;
	}

	@Override
	public String toString() {
		return "IbmLogPlanUserT{" +
				"idx=" + idx +
				", ibmLogPlanUserId='" + ibmLogPlanUserId + '\'' +
				", planId='" + planId + '\'' +
				", appUserId='" + appUserId + '\'' +
				", gameId='" + gameId + '\'' +
				", handleType='" + handleType + '\'' +
				", createTime=" + createTime +
				", createTimeLong=" + createTimeLong +
				", updateTime=" + updateTime +
				", updateTimeLong=" + updateTimeLong +
				", state='" + state + '\'' +
				", desc='" + desc + '\'' +
				", tableNameMy='" + tableNameMy + '\'' +
				'}';
	}
}