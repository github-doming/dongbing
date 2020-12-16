package c.x.platform.admin.feng.wf.wf_def_process_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_def_process_t vo类
 */

public class WfDefProcessTCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// process_name
	private String processName;
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessName() {
		return this.processName;
	}

	// process_key
	private String processKey;
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getProcessKey() {
		return this.processKey;
	}

}