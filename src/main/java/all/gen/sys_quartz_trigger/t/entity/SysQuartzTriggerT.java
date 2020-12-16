package all.gen.sys_quartz_trigger.t.entity;

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
 * The persistent class for the database table sys_quartz_trigger 
 * 定时器计划的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_quartz_trigger")
public class SysQuartzTriggerT implements Serializable {

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
		
			
//SYS_QUARTZ_TRIGGER_ID_
@Column(name="SYS_QUARTZ_TRIGGER_ID_")
	private String sysQuartzTriggerId;
	
	public void setSysQuartzTriggerId(String sysQuartzTriggerId) {
		this.sysQuartzTriggerId=sysQuartzTriggerId;
	}
	public String getSysQuartzTriggerId() {
		return this.sysQuartzTriggerId ;
	}
			
			
//计划名称
@Column(name="SYS_TRIGGER_NAME_")
	private String sysTriggerName;
	
	public void setSysTriggerName(String sysTriggerName) {
		this.sysTriggerName=sysTriggerName;
	}
	public String getSysTriggerName() {
		return this.sysTriggerName ;
	}
			
			
//计划状态
@Column(name="TRIGGER_STATE_CN_")
	private String triggerStateCn;
	
	public void setTriggerStateCn(String triggerStateCn) {
		this.triggerStateCn=triggerStateCn;
	}
	public String getTriggerStateCn() {
		return this.triggerStateCn ;
	}
			
			
//计划状态TRIGGER_STATE_
@Column(name="TRIGGER_STATE_")
	private String triggerState;
	
	public void setTriggerState(String triggerState) {
		this.triggerState=triggerState;
	}
	public String getTriggerState() {
		return this.triggerState ;
	}
			
			
//是否绑定到QUARTZ
@Column(name="QUARTZ_")
	private String quartz;
	
	public void setQuartz(String quartz) {
		this.quartz=quartz;
	}
	public String getQuartz() {
		return this.quartz ;
	}
			
			
//是否绑定到QUARTZ
@Column(name="QUARTZ_CN_")
	private String quartzCn;
	
	public void setQuartzCn(String quartzCn) {
		this.quartzCn=quartzCn;
	}
	public String getQuartzCn() {
		return this.quartzCn ;
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
			
			
//计划ID
@Column(name="TRIGGER_ID_")
	private String triggerId;
	
	public void setTriggerId(String triggerId) {
		this.triggerId=triggerId;
	}
	public String getTriggerId() {
		return this.triggerId ;
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
			
			
//计划类型
@Column(name="TRIGGER_TYPE_")
	private String triggerType;
	
	public void setTriggerType(String triggerType) {
		this.triggerType=triggerType;
	}
	public String getTriggerType() {
		return this.triggerType ;
	}
			
			
//表达式
@Column(name="CRON_EXPRESSION_")
	private String cronExpression;
	
	public void setCronExpression(String cronExpression) {
		this.cronExpression=cronExpression;
	}
	public String getCronExpression() {
		return this.cronExpression ;
	}
			
			
//JSON_
@Column(name="JSON_")
	private String json;
	
	public void setJson(String json) {
		this.json=json;
	}
	public String getJson() {
		return this.json ;
	}
			
			
//创建者CREATE_USER_
@Column(name="CREATE_USER_")
	private String createUser;
	
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
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
			
			
//更新者UPDATE_USER_
@Column(name="UPDATE_USER_")
	private String updateUser;
	
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
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