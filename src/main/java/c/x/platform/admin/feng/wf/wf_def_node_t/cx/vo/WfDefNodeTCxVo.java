package c.x.platform.admin.feng.wf.wf_def_node_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_def_node_t vo类
 */

public class WfDefNodeTCxVo implements Serializable {

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

	// type
	private String type;
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}

	// assignee_user
	private String assigneeUser;
	public void setAssigneeUser(String assigneeUser) {
		this.assigneeUser = assigneeUser;
	}
	public String getAssigneeUser() {
		return this.assigneeUser;
	}

	// assignee_group
	private String assigneeGroup;
	public void setAssigneeGroup(String assigneeGroup) {
		this.assigneeGroup = assigneeGroup;
	}
	public String getAssigneeGroup() {
		return this.assigneeGroup;
	}

	// def_process_id
	private String defProcessId;
	public void setDefProcessId(String defProcessId) {
		this.defProcessId = defProcessId;
	}
	public String getDefProcessId() {
		return this.defProcessId;
	}

}