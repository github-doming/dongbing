package c.x.platform.admin.feng.wf.wf_ins_node_t.cx.entity;

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
 * The persistent class for the database table wf_ins_node_t 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "wf_ins_node_t")
public class WfInsNodeTCx implements Serializable {

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

	// status
	@Column(name = "status")
	private String status;

	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
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

	@Temporal(TemporalType.TIMESTAMP)
	// start_time_dt
	@Column(name = "start_time_dt")
	private Date startTimeDt;

	public void setStartTimeDt(Date startTimeDt) {
		this.startTimeDt = startTimeDt;
	}
	public Date getStartTimeDt() {
		return this.startTimeDt;
	}

	// start_time
	@Column(name = "start_time")
	private Long startTime;

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getStartTime() {
		return this.startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	// end_time_dt
	@Column(name = "end_time_dt")
	private Date endTimeDt;

	public void setEndTimeDt(Date endTimeDt) {
		this.endTimeDt = endTimeDt;
	}
	public Date getEndTimeDt() {
		return this.endTimeDt;
	}

	// end_time
	@Column(name = "end_time")
	private Long endTime;

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Long getEndTime() {
		return this.endTime;
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