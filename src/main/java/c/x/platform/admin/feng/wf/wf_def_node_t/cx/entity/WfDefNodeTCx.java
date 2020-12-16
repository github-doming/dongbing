package c.x.platform.admin.feng.wf.wf_def_node_t.cx.entity;

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
 * The persistent class for the database table wf_def_node_t 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wf_def_node_t")
public class WfDefNodeTCx implements Serializable {

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

	// node_name
	@Column(name = "node_name")
	private String nodeName;

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeName() {
		return this.nodeName;
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

	// assignee_user
	@Column(name = "assignee_user")
	private String assigneeUser;

	public void setAssigneeUser(String assigneeUser) {
		this.assigneeUser = assigneeUser;
	}
	public String getAssigneeUser() {
		return this.assigneeUser;
	}

	// assignee_group
	@Column(name = "assignee_group")
	private String assigneeGroup;

	public void setAssigneeGroup(String assigneeGroup) {
		this.assigneeGroup = assigneeGroup;
	}
	public String getAssigneeGroup() {
		return this.assigneeGroup;
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