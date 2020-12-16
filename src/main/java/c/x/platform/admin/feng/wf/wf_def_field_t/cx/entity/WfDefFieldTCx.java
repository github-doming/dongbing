package c.x.platform.admin.feng.wf.wf_def_field_t.cx.entity;

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
 * The persistent class for the database table wf_def_field_t 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wf_def_field_t")
public class WfDefFieldTCx implements Serializable {

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

	// field_name
	@Column(name = "field_name")
	private String fieldName;

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldName() {
		return this.fieldName;
	}

	// def_form_id
	@Column(name = "def_form_id")
	private Long defFormId;

	public void setDefFormId(Long defFormId) {
		this.defFormId = defFormId;
	}
	public Long getDefFormId() {
		return this.defFormId;
	}

	// type
	@Column(name = "type")
	private String type;

	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
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