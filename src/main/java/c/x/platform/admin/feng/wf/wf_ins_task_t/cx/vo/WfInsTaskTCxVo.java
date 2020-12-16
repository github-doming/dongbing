package c.x.platform.admin.feng.wf.wf_ins_task_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_ins_task_t vo类
 */

public class WfInsTaskTCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// name
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	// 任务执行人
	private String assignee;
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getAssignee() {
		return this.assignee;
	}

	// start_time
	private String startTime;
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartTime() {
		return this.startTime;
	}

	// start_time_dt
	private String startTimeDt;
	public void setStartTimeDt(String startTimeDt) {
		this.startTimeDt = startTimeDt;
	}
	public String getStartTimeDt() {
		return this.startTimeDt;
	}

	// end_time
	private String endTime;
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEndTime() {
		return this.endTime;
	}

	// end_time_dt
	private String endTimeDt;
	public void setEndTimeDt(String endTimeDt) {
		this.endTimeDt = endTimeDt;
	}
	public String getEndTimeDt() {
		return this.endTimeDt;
	}

	// ins_process_id
	private String insProcessId;
	public void setInsProcessId(String insProcessId) {
		this.insProcessId = insProcessId;
	}
	public String getInsProcessId() {
		return this.insProcessId;
	}

}