package c.x.platform.admin.feng.wf.wf_def_transition_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_def_transition_t vo类
 */

public class WfDefTransitionTCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// transition_name
	private String transitionName;
	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}
	public String getTransitionName() {
		return this.transitionName;
	}

	// to_node_name
	private String toNodeName;
	public void setToNodeName(String toNodeName) {
		this.toNodeName = toNodeName;
	}
	public String getToNodeName() {
		return this.toNodeName;
	}

	// def_process_id
	private String defProcessId;
	public void setDefProcessId(String defProcessId) {
		this.defProcessId = defProcessId;
	}
	public String getDefProcessId() {
		return this.defProcessId;
	}

	// from_node_name
	private String fromNodeName;
	public void setFromNodeName(String fromNodeName) {
		this.fromNodeName = fromNodeName;
	}
	public String getFromNodeName() {
		return this.fromNodeName;
	}

}