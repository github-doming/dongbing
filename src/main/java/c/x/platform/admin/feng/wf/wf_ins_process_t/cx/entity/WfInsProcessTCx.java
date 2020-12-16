package c.x.platform.admin.feng.wf.wf_ins_process_t.cx.entity;

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
 * The persistent class for the database table wf_ins_process_t 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wf_ins_process_t")
public class WfInsProcessTCx implements Serializable {

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

	// process_name
	@Column(name = "process_name")
	private String processName;

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessName() {
		return this.processName;
	}

	// def_process_id
	@Column(name = "def_process_id")
	private Long defProcessId;

	public void setDefProcessId(Long defProcessId) {
		this.defProcessId = defProcessId;
	}
	public Long getDefProcessId() {
		return this.defProcessId;
	}

	// process_key
	@Column(name = "process_key")
	private String processKey;

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getProcessKey() {
		return this.processKey;
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