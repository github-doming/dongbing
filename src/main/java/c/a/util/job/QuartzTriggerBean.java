package c.a.util.job;
import java.util.Date;
/**
 * 
 * Trigger计划bean
 * 
 * @Description:
 * @ClassName: QuartzTriggerBean
 * @date 2017年3月21日 上午11:44:00
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class QuartzTriggerBean {
	// 自动运行的任务
	private boolean auto = false;
	// id
	private String triggerId;
	// 启动时间
	private Date timeStartTrigger;
	// 间隔时间
	private Long intervalInMillis;
	// 重复次数
	private int countRepeatTrigger;
	// 别名
	private String alias;
	// 定时器名称
	private String jobName;
	// 计划名称
	private String triggerName;
	// 类名
	protected String jobClassName;
	// 时间表达式
	protected String cronExpression;
	// job描述
	protected String jobDescription;

	// trigger描述
	protected String triggerDescription;

	// 定时器参数，以逗号分割
	protected String params;
	// 状态
	protected TriggerStateEnum triggerState;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClass) {
		this.jobClassName = jobClass;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String description) {
		this.jobDescription = description;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Long getIntervalInMillis() {
		return intervalInMillis;
	}
	public void setIntervalInMillis(long intervalInMillis) {
		this.intervalInMillis = intervalInMillis;
	}
	public Date getTimeStartTrigger() {
		return timeStartTrigger;
	}
	public void setTimeStartTrigger(Date timeStartTrigger) {
		this.timeStartTrigger = timeStartTrigger;
	}
	public int getCountRepeatTrigger() {
		return countRepeatTrigger;
	}
	public void setCountRepeatTrigger(int countRepeatTrigger) {
		this.countRepeatTrigger = countRepeatTrigger;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerId() {
		return triggerId;
	}
	public void setTriggerId(String id) {
		this.triggerId = id;
	}
	public boolean isAuto() {
		return auto;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public String getTriggerDescription() {
		return triggerDescription;
	}
	public void setTriggerDescription(String triggerDescription) {
		this.triggerDescription = triggerDescription;
	}
	public TriggerStateEnum getTriggerState() {
		return triggerState;
	}
	public void setTriggerState(TriggerStateEnum triggerState) {
		this.triggerState = triggerState;
	}


}
