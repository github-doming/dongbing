package all.gen.sys_quartz_job.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_quartz_job 
 * vo类
 */

public class SysQuartzJobTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//SYS_QUARTZ_JOB_ID_
	private String sysQuartzJobId;
	public void setSysQuartzJobId(String sysQuartzJobId) {
		this.sysQuartzJobId=sysQuartzJobId;
	}
	public String getSysQuartzJobId() {
		return this.sysQuartzJobId ;
	}
	
//作业名称
	private String sysJobName;
	public void setSysJobName(String sysJobName) {
		this.sysJobName=sysJobName;
	}
	public String getSysJobName() {
		return this.sysJobName ;
	}
	
//作业状态JOB_STATE_
	private String jobState;
	public void setJobState(String jobState) {
		this.jobState=jobState;
	}
	public String getJobState() {
		return this.jobState ;
	}
	
//作业状态
	private String jobStateCn;
	public void setJobStateCn(String jobStateCn) {
		this.jobStateCn=jobStateCn;
	}
	public String getJobStateCn() {
		return this.jobStateCn ;
	}
	
//是否绑定到QUARTZ
	private String quartz;
	public void setQuartz(String quartz) {
		this.quartz=quartz;
	}
	public String getQuartz() {
		return this.quartz ;
	}
	
//是否绑定到QUARTZ
	private String quartzCn;
	public void setQuartzCn(String quartzCn) {
		this.quartzCn=quartzCn;
	}
	public String getQuartzCn() {
		return this.quartzCn ;
	}
	
//作业名称JOB_NAME_
	private String jobName;
	public void setJobName(String jobName) {
		this.jobName=jobName;
	}
	public String getJobName() {
		return this.jobName ;
	}
	
//类名
	private String jobClassName;
	public void setJobClassName(String jobClassName) {
		this.jobClassName=jobClassName;
	}
	public String getJobClassName() {
		return this.jobClassName ;
	}
	
//JSON_
	private String json;
	public void setJson(String json) {
		this.json=json;
	}
	public String getJson() {
		return this.json ;
	}
	
//创建者CREATE_USER_
	private String createUser;
	public void setCreateUser(String createUser) {
		this.createUser=createUser;
	}
	public String getCreateUser() {
		return this.createUser ;
	}
	
//创建时间
	private String createTime;
	public void setCreateTime(String createTime) {
		this.createTime=createTime;
	}
	public String getCreateTime() {
		return this.createTime ;
	}
	
//创建时间
	private String createTimeLong;
	public void setCreateTimeLong(String createTimeLong) {
		this.createTimeLong=createTimeLong;
	}
	public String getCreateTimeLong() {
		return this.createTimeLong ;
	}
	
//更新者UPDATE_USER_
	private String updateUser;
	public void setUpdateUser(String updateUser) {
		this.updateUser=updateUser;
	}
	public String getUpdateUser() {
		return this.updateUser ;
	}
	
//更新时间
	private String updateTime;
	public void setUpdateTime(String updateTime) {
		this.updateTime=updateTime;
	}
	public String getUpdateTime() {
		return this.updateTime ;
	}
	
//更新时间
	private String updateTimeLong;
	public void setUpdateTimeLong(String updateTimeLong) {
		this.updateTimeLong=updateTimeLong;
	}
	public String getUpdateTimeLong() {
		return this.updateTimeLong ;
	}
	
//DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用
	private String state;
	public void setState(String state) {
		this.state=state;
	}
	public String getState() {
		return this.state ;
	}
	
//描述
	private String desc;
	public void setDesc(String desc) {
		this.desc=desc;
	}
	public String getDesc() {
		return this.desc ;
	}
	
//编码CODE_
	private String code;
	public void setCode(String code) {
		this.code=code;
	}
	public String getCode() {
		return this.code ;
	}


}