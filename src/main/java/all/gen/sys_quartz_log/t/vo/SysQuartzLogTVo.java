package all.gen.sys_quartz_log.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_quartz_log 
 * vo类
 */

public class SysQuartzLogTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//SYS_QUARTZ_LOG_ID_
	private String sysQuartzLogId;
	public void setSysQuartzLogId(String sysQuartzLogId) {
		this.sysQuartzLogId=sysQuartzLogId;
	}
	public String getSysQuartzLogId() {
		return this.sysQuartzLogId ;
	}
	
//作业名称
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
	
//计划名称
	private String triggerName;
	public void setTriggerName(String triggerName) {
		this.triggerName=triggerName;
	}
	public String getTriggerName() {
		return this.triggerName ;
	}
	
//内容
	private String content;
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return this.content ;
	}
	
//定时计划执行持续时间RUN_TIME_
	private String runTime;
	public void setRunTime(String runTime) {
		this.runTime=runTime;
	}
	public String getRunTime() {
		return this.runTime ;
	}
	
//开始时间
	private String startTime;
	public void setStartTime(String startTime) {
		this.startTime=startTime;
	}
	public String getStartTime() {
		return this.startTime ;
	}
	
//开始时间
	private String startTimeLong;
	public void setStartTimeLong(String startTimeLong) {
		this.startTimeLong=startTimeLong;
	}
	public String getStartTimeLong() {
		return this.startTimeLong ;
	}
	
//结束时间
	private String endTime;
	public void setEndTime(String endTime) {
		this.endTime=endTime;
	}
	public String getEndTime() {
		return this.endTime ;
	}
	
//结束时间
	private String endTimeLong;
	public void setEndTimeLong(String endTimeLong) {
		this.endTimeLong=endTimeLong;
	}
	public String getEndTimeLong() {
		return this.endTimeLong ;
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


}