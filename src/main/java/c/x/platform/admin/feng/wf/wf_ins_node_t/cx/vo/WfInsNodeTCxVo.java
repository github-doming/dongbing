package c.x.platform.admin.feng.wf.wf_ins_node_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_ins_node_t vo类
 */

public class WfInsNodeTCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// node_name
	private String nodeName;
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeName() {
		return this.nodeName;
	}

	// status
	private String status;
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}

	// to_node_name
	private String toNodeName;
	public void setToNodeName(String toNodeName) {
		this.toNodeName = toNodeName;
	}
	public String getToNodeName() {
		return this.toNodeName;
	}

	// start_time_dt
	private String startTimeDt;
	public void setStartTimeDt(String startTimeDt) {
		this.startTimeDt = startTimeDt;
	}
	public String getStartTimeDt() {
		return this.startTimeDt;
	}

	// start_time
	private String startTime;
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStartTime() {
		return this.startTime;
	}

	// end_time_dt
	private String endTimeDt;
	public void setEndTimeDt(String endTimeDt) {
		this.endTimeDt = endTimeDt;
	}
	public String getEndTimeDt() {
		return this.endTimeDt;
	}

	// end_time
	private String endTime;
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEndTime() {
		return this.endTime;
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