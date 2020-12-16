package c.x.platform.admin.feng.edu.edu_place_teacher.cx.entity;

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
 * The persistent class for the database table edu_place_teacher 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "edu_place_teacher")
public class EduPlaceTeacherCx implements Serializable {

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

	// place_id
	@Column(name = "place_id")
	private String placeId;

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getPlaceId() {
		return this.placeId;
	}

	// teacher_id
	@Column(name = "teacher_id")
	private String teacherId;

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherId() {
		return this.teacherId;
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