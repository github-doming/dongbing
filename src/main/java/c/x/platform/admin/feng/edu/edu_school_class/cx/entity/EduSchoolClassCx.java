package c.x.platform.admin.feng.edu.edu_school_class.cx.entity;

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
 * The persistent class for the database table edu_school_class 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "edu_school_class")
public class EduSchoolClassCx implements Serializable {

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

	// school_id
	@Column(name = "school_id")
	private String schoolId;

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolId() {
		return this.schoolId;
	}

	// class_id
	@Column(name = "class_id")
	private String classId;

	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassId() {
		return this.classId;
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