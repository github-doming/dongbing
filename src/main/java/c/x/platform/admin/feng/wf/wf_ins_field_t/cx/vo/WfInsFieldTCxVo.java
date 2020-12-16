package c.x.platform.admin.feng.wf.wf_ins_field_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_ins_field_t vo类
 */

public class WfInsFieldTCxVo implements Serializable {

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

	// field_value
	private String fieldValue;
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getFieldValue() {
		return this.fieldValue;
	}

	// ins_form_id
	private String insFormId;
	public void setInsFormId(String insFormId) {
		this.insFormId = insFormId;
	}
	public String getInsFormId() {
		return this.insFormId;
	}

}