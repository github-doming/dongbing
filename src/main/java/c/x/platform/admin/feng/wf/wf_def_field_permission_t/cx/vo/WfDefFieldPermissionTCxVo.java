package c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_def_field_permission_t vo类
 */

public class WfDefFieldPermissionTCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// field_name
	private String fieldName;
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return this.fieldName;
	}

	// is_show
	private String isShow;
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getIsShow() {
		return this.isShow;
	}

	// is_write
	private String isWrite;
	public void setIsWrite(String isWrite) {
		this.isWrite = isWrite;
	}
	public String getIsWrite() {
		return this.isWrite;
	}

	// def_form_id
	private String defFormId;
	public void setDefFormId(String defFormId) {
		this.defFormId = defFormId;
	}
	public String getDefFormId() {
		return this.defFormId;
	}

	// node_name
	private String nodeName;
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeName() {
		return this.nodeName;
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