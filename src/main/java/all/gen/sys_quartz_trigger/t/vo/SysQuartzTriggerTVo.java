package all.gen.sys_quartz_trigger.t.vo;

import java.io.Serializable;
/**
 * The vo class for the database table sys_quartz_trigger 
 * vo类
 */

public class SysQuartzTriggerTVo implements Serializable {

	private static final long serialVersionUID = 1L;
		
//索引
	private String idx;
	public void setIdx(String idx) {
		this.idx=idx;
	}
	public String getIdx() {
		return this.idx ;
	}
	
//SYS_QUARTZ_TRIGGER_ID_
	private String sysQuartzTriggerId;
	public void setSysQuartzTriggerId(String sysQuartzTriggerId) {
		this.sysQuartzTriggerId=sysQuartzTriggerId;
	}
	public String getSysQuartzTriggerId() {
		return this.sysQuartzTriggerId ;
	}
	
//计划名称
	private String sysTriggerName;
	public void setSysTriggerName(String sysTriggerName) {
		this.sysTriggerName=sysTriggerName;
	}
	public String getSysTriggerName() {
		return this.sysTriggerName ;
	}
	
//计划状态
	private String triggerStateCn;
	public void setTriggerStateCn(String triggerStateCn) {
		this.triggerStateCn=triggerStateCn;
	}
	public String getTriggerStateCn() {
		return this.triggerStateCn ;
	}
	
//计划状态TRIGGER_STATE_
	private String triggerState;
	public void setTriggerState(String triggerState) {
		this.triggerState=triggerState;
	}
	public String getTriggerState() {
		return this.triggerState ;
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
	
//计划ID
	private String triggerId;
	public void setTriggerId(String triggerId) {
		this.triggerId=triggerId;
	}
	public String getTriggerId() {
		return this.triggerId ;
	}
	
//计划名称
	private String triggerName;
	public void setTriggerName(String triggerName) {
		this.triggerName=triggerName;
	}
	public String getTriggerName() {
		return this.triggerName ;
	}
	
//计划类型
	private String triggerType;
	public void setTriggerType(String triggerType) {
		this.triggerType=triggerType;
	}
	public String getTriggerType() {
		return this.triggerType ;
	}
	
//表达式
	private String cronExpression;
	public void setCronExpression(String cronExpression) {
		this.cronExpression=cronExpression;
	}
	public String getCronExpression() {
		return this.cronExpression ;
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


}