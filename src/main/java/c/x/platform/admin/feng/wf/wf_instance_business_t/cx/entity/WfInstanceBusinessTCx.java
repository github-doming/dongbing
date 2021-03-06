package c.x.platform.admin.feng.wf.wf_instance_business_t.cx.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import c.a.util.core.annotation.AnnotationEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import c.a.util.core.annotation.AnnotationTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the database table wf_instance_business_t 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wf_instance_business_t")
public class WfInstanceBusinessTCx implements Serializable {

	private static final long serialVersionUID = 1L;
	// 自己制定ID
	// @Id
	// 根据底层数据库
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// id
	@Column(name = "id")
	private String id;

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// business_name
	@Column(name = "business_name")
	private String businessName;

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessName() {
		return this.businessName;
	}

	// ins_process_id
	@Column(name = "ins_process_id")
	private Long insProcessId;

	public void setInsProcessId(Long insProcessId) {
		this.insProcessId = insProcessId;
	}
	public Long getInsProcessId() {
		return this.insProcessId;
	}

	// ins_form_id
	@Column(name = "ins_form_id")
	private Long insFormId;

	public void setInsFormId(Long insFormId) {
		this.insFormId = insFormId;
	}
	public Long getInsFormId() {
		return this.insFormId;
	}

	private String table_name;
	/*
	 * 不映射
	 * 
	 * @return
	 */
	@Transient
	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String tableName) {
		table_name = tableName;
	}

}