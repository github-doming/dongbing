package c.x.platform.admin.feng.wf.wf_define_business_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_define_business_t vo类
 */

public class WfDefineBusinessTCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// business_name
	private String businessName;
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessName() {
		return this.businessName;
	}

	// def_process_id
	private String defProcessId;
	public void setDefProcessId(String defProcessId) {
		this.defProcessId = defProcessId;
	}
	public String getDefProcessId() {
		return this.defProcessId;
	}

	// def_form_id
	private String defFormId;
	public void setDefFormId(String defFormId) {
		this.defFormId = defFormId;
	}
	public String getDefFormId() {
		return this.defFormId;
	}

}