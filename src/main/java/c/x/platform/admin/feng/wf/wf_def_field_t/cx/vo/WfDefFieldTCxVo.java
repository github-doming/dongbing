package c.x.platform.admin.feng.wf.wf_def_field_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_def_field_t vo类
 */

public class WfDefFieldTCxVo implements Serializable {

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

	// def_form_id
	private String defFormId;
	public void setDefFormId(String defFormId) {
		this.defFormId = defFormId;
	}
	public String getDefFormId() {
		return this.defFormId;
	}

	// type
	private String type;
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}

}