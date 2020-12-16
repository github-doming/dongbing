package c.x.platform.admin.feng.wf.wf_instance_business_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table wf_instance_business_t vo类
 */

public class WfInstanceBusinessTCxVo implements Serializable {

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

	// ins_process_id
	private String insProcessId;
	public void setInsProcessId(String insProcessId) {
		this.insProcessId = insProcessId;
	}
	public String getInsProcessId() {
		return this.insProcessId;
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