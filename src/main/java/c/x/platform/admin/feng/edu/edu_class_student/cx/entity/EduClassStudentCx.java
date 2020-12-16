package c.x.platform.admin.feng.edu.edu_class_student.cx.entity;

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
 * The persistent class for the database table edu_class_student 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "edu_class_student")
public class EduClassStudentCx implements Serializable {

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

	// class_id
	@Column(name = "class_id")
	private String classId;

	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassId() {
		return this.classId;
	}

	// student_id
	@Column(name = "student_id")
	private String studentId;

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentId() {
		return this.studentId;
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