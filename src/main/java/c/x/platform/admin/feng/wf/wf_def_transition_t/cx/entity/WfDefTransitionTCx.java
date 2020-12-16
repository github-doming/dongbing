package c.x.platform.admin.feng.wf.wf_def_transition_t.cx.entity;

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
 * The persistent class for the database table wf_def_transition_t 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wf_def_transition_t")
public class WfDefTransitionTCx implements Serializable {

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

	// transition_name
	@Column(name = "transition_name")
	private String transitionName;

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}
	public String getTransitionName() {
		return this.transitionName;
	}

	// to_node_name
	@Column(name = "to_node_name")
	private String toNodeName;

	public void setToNodeName(String toNodeName) {
		this.toNodeName = toNodeName;
	}
	public String getToNodeName() {
		return this.toNodeName;
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

	// from_node_name
	@Column(name = "from_node_name")
	private String fromNodeName;

	public void setFromNodeName(String fromNodeName) {
		this.fromNodeName = fromNodeName;
	}
	public String getFromNodeName() {
		return this.fromNodeName;
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