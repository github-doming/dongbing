package all.gen.sys_quartz_job.t.entity;

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
 * The persistent class for the database table sys_quartz_job 
 * 定时器作业的实体类
 */
@AnnotationEntity
@AnnotationTable(name = "sys_quartz_job")
public class SysQuartzJobT implements Serializable {

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
		
			
//SYS_QUARTZ_JOB_ID_
@Column(name="SYS_QUARTZ_JOB_ID_")
	private String sysQuartzJobId;
	
	public void setSysQuartzJobId(String sysQuartzJobId) {
		this.sysQuartzJobId=sysQuartzJobId;
	}
	public String getSysQuartzJobId() {
		return this.sysQuartzJobId ;
	}
			
			
//作业名称
@Column(name="SYS_JOB_NAME_")
	private String sysJobName;
	
	public void setSysJobName(String sysJobName) {
		this.sysJobName=sysJobName;
	}
	public String getSysJobName() {
		return this.sysJobName ;
	}
			
			
//作业状态JOB_STATE_
@Column(name="JOB_STATE_")
	private String jobState;
	
	public void setJobState(String jobState) {
		this.jobState=jobState;
	}
	public String getJobState() {
		return this.jobState ;
	}
			
			
//作业状态
@Column(name="JOB_STATE_CN_")
	private String jobStateCn;
	
	public void setJobStateCn(String jobStateCn) {
		this.jobStateCn=jobStateCn;
	}
	public String getJobStateCn() {
		return this.jobStateCn ;
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
			
			
//作业名称JOB_NAME_
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
			
			
//编码CODE_
@Column(name="CODE_")
	private String code;
	
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return this.code ;
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