package c.a.util.job;
/**
 * 定时器日志
 * 
 * @Description:
 * @ClassName: QuartzLogBean
 * @date 2017年3月21日 上午11:42:46
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class QuartzLogBean {
	// id
	protected String logId;
	// 定时器名称
	protected String jobName;
	// 日志内容
	protected String content;
	// 开始时间
	protected java.util.Date startTime;
	// 结束时间
	protected java.util.Date endTime;
	// 持续时间M
	protected Long runTime;
	// 状态 正常0 停止1 完成2 出错3 阻塞4 不存在-1
	protected Integer status;
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public java.util.Date getStartTime() {
		return startTime;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public Integer getstatus() {
		return status;
	}
	public void setstatus(Integer status) {
		this.status = status;
	}
	public Long getRunTime() {
		return runTime;
	}
	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}
}
