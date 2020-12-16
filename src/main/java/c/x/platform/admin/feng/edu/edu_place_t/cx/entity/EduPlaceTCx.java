package c.x.platform.admin.feng.edu.edu_place_t.cx.entity;

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
 * The persistent class for the database table edu_place_t 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "edu_place_t")
public class EduPlaceTCx implements Serializable {

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

	// name
	@Column(name = "name")
	private String name;

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	// address
	@Column(name = "address")
	private String address;

	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
	}

	// description
	@Column(name = "description")
	private String description;

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
	}

	// 学习周期描述
	@Column(name = "time_description")
	private String timeDescription;

	public void setTimeDescription(String timeDescription) {
		this.timeDescription = timeDescription;
	}
	public String getTimeDescription() {
		return this.timeDescription;
	}

	// 面积（米）
	@Column(name = "square")
	private Long square;

	public void setSquare(Long square) {
		this.square = square;
	}
	public Long getSquare() {
		return this.square;
	}

	// max_student
	@Column(name = "max_student")
	private Long maxStudent;

	public void setMaxStudent(Long maxStudent) {
		this.maxStudent = maxStudent;
	}
	public Long getMaxStudent() {
		return this.maxStudent;
	}

	// max_teacher
	@Column(name = "max_teacher")
	private Long maxTeacher;

	public void setMaxTeacher(Long maxTeacher) {
		this.maxTeacher = maxTeacher;
	}
	public Long getMaxTeacher() {
		return this.maxTeacher;
	}

	// max_automoblie
	@Column(name = "max_automoblie")
	private Long maxAutomoblie;

	public void setMaxAutomoblie(Long maxAutomoblie) {
		this.maxAutomoblie = maxAutomoblie;
	}
	public Long getMaxAutomoblie() {
		return this.maxAutomoblie;
	}

	// 学习周期类型
	@Column(name = "time_type")
	private Long timeType;

	public void setTimeType(Long timeType) {
		this.timeType = timeType;
	}
	public Long getTimeType() {
		return this.timeType;
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