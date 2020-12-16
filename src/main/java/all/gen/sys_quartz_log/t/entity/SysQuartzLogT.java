package all.gen.sys_quartz_log.t.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the database table sys_quartz_log 
 * 定时器日志的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_quartz_log")
public class SysQuartzLogT implements Serializable {

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
		
			
//SYS_QUARTZ_LOG_ID_
@Column(name="SYS_QUARTZ_LOG_ID_")
	private String sysQuartzLogId;
	
	public void setSysQuartzLogId(String sysQuartzLogId) {
		this.sysQuartzLogId=sysQuartzLogId;
	}
	public String getSysQuartzLogId() {
		return this.sysQuartzLogId ;
	}
			
			
//作业名称
@Column(name="JOB_NAME_")
	private String jobName;
	
	public void setJobName(String jobName) {
		this.jobName=jobName;
	}
	public String getJobName() {
		return this.jobName ;
	}
			
			
//类名
@Column(name="JOB_CLASS_NAME_")
	private String jobClassName;
	
	public void setJobClassName(String jobClassName) {
		this.jobClassName=jobClassName;
	}
	public String getJobClassName() {
		return this.jobClassName ;
	}
			
			
//计划名称
@Column(name="TRIGGER_NAME_")
	private String triggerName;
	
	public void setTriggerName(String triggerName) {
		this.triggerName=triggerName;
	}
	public String getTriggerName() {
		return this.triggerName ;
	}
			
			
//内容
@Column(name="CONTENT_")
	private String content;
	
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
			
			
//定时计划执行持续时间RUN_TIME_
@Column(name="RUN_TIME_")
	private Long runTime;
	
	public void setRunTime(Long runTime) {
		this.runTime=runTime;
	}
	public Long getRunTime() {
		return this.runTime ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//开始时间
@Column(name="START_TIME_")
	private Date startTime;
	
	public void setStartTime(Date startTime) {
		this.startTime=startTime;
	}
	public Date getStartTime() {
		return this.startTime ;
	}
			
			
//开始时间
@Column(name="START_TIME_LONG_")
	private Long startTimeLong;
	
	public void setStartTimeLong(Long startTimeLong) {
		this.startTimeLong=startTimeLong;
	}
	public Long getStartTimeLong() {
		return this.startTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//结束时间
@Column(name="END_TIME_")
	private Date endTime;
	
	public void setEndTime(Date endTime) {
		this.endTime=endTime;
	}
	public Date getEndTime() {
		return this.endTime ;
	}
			
			
//结束时间
@Column(name="END_TIME_LONG_")
	private Long endTimeLong;
	
	public void setEndTimeLong(Long endTimeLong) {
		this.endTimeLong=endTimeLong;
	}
	public Long getEndTimeLong() {
		return this.endTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//创建时间
@Column(name="CREATE_TIME_")
	private Date createTime;
	
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
	}
	public Date getCreateTime() {
		return this.createTime ;
	}
			
			
//创建时间
@Column(name="CREATE_TIME_LONG_")
	private Long createTimeLong;
	
	public void setCreateTimeLong(Long createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public Long getCreateTimeLong() {
		return this.createTimeLong ;
	}
			
		@Temporal( TemporalType.TIMESTAMP)
		
//更新时间
@Column(name="UPDATE_TIME_")
	private Date updateTime;
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
	}
	public Date getUpdateTime() {
		return this.updateTime ;
	}
			
			
//更新时间
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

}