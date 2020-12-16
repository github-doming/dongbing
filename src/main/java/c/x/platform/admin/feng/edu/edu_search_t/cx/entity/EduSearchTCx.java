package c.x.platform.admin.feng.edu.edu_search_t.cx.entity;

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
 * The persistent class for the database table edu_search_t 实体类
 */
@AnnotationEntity
@AnnotationTable(name = "edu_search_t")
public class EduSearchTCx implements Serializable {

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

	// 驾证类型
	@Column(name = "license_type")
	private Long licenseType;

	public void setLicenseType(Long licenseType) {
		this.licenseType = licenseType;
	}
	public Long getLicenseType() {
		return this.licenseType;
	}

	// 价格以分为单位
	@Column(name = "price")
	private Long price;

	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getPrice() {
		return this.price;
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

	// 分数(好评)
	@Column(name = "score")
	private Long score;

	public void setScore(Long score) {
		this.score = score;
	}
	public Long getScore() {
		return this.score;
	}

	// 班级id
	@Column(name = "edu_class_id")
	private Long eduClassId;

	public void setEduClassId(Long eduClassId) {
		this.eduClassId = eduClassId;
	}
	public Long getEduClassId() {
		return this.eduClassId;
	}

	// 老师id
	@Column(name = "edu_teacher_id")
	private Long eduTeacherId;

	public void setEduTeacherId(Long eduTeacherId) {
		this.eduTeacherId = eduTeacherId;
	}
	public Long getEduTeacherId() {
		return this.eduTeacherId;
	}

	// uid
	@Column(name = "uid")
	private String uid;

	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUid() {
		return this.uid;
	}

	// 距离
	@Column(name = "distance")
	private Long distance;

	public void setDistance(Long distance) {
		this.distance = distance;
	}
	public Long getDistance() {
		return this.distance;
	}

	// 纬度
	@Column(name = "lat")
	private Double lat;

	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLat() {
		return this.lat;
	}

	// 经度
	@Column(name = "lng")
	private Double lng;

	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLng() {
		return this.lng;
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

	// street_id
	@Column(name = "street_id")
	private String streetId;

	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}
	public String getStreetId() {
		return this.streetId;
	}

	// 是否删除
	@Column(name = "is_del")
	private Integer isDel;

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getIsDel() {
		return this.isDel;
	}

	// time_description
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

	// 最多汽车数
	@Column(name = "max_automoblie")
	private Long maxAutomoblie;

	public void setMaxAutomoblie(Long maxAutomoblie) {
		this.maxAutomoblie = maxAutomoblie;
	}
	public Long getMaxAutomoblie() {
		return this.maxAutomoblie;
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