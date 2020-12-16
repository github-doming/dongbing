package c.x.platform.admin.feng.edu.edu_search_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_search_t vo类
 */

public class EduSearchTCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// name
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	// address
	private String address;
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
	}

	// 驾证类型
	private String licenseType;
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	public String getLicenseType() {
		return this.licenseType;
	}

	// 价格以分为单位
	private String price;
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPrice() {
		return this.price;
	}

	// 学习周期类型
	private String timeType;
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getTimeType() {
		return this.timeType;
	}

	// 分数(好评)
	private String score;
	public void setScore(String score) {
		this.score = score;
	}
	public String getScore() {
		return this.score;
	}

	// 班级id
	private String eduClassId;
	public void setEduClassId(String eduClassId) {
		this.eduClassId = eduClassId;
	}
	public String getEduClassId() {
		return this.eduClassId;
	}

	// 老师id
	private String eduTeacherId;
	public void setEduTeacherId(String eduTeacherId) {
		this.eduTeacherId = eduTeacherId;
	}
	public String getEduTeacherId() {
		return this.eduTeacherId;
	}

	// uid
	private String uid;
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUid() {
		return this.uid;
	}

	// 距离
	private String distance;
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDistance() {
		return this.distance;
	}

	// 纬度
	private String lat;
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLat() {
		return this.lat;
	}

	// 经度
	private String lng;
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLng() {
		return this.lng;
	}

	// description
	private String description;
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
	}

	// street_id
	private String streetId;
	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}
	public String getStreetId() {
		return this.streetId;
	}

	// 是否删除
	private String isDel;
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getIsDel() {
		return this.isDel;
	}

	// time_description
	private String timeDescription;
	public void setTimeDescription(String timeDescription) {
		this.timeDescription = timeDescription;
	}
	public String getTimeDescription() {
		return this.timeDescription;
	}

	// 面积（米）
	private String square;
	public void setSquare(String square) {
		this.square = square;
	}
	public String getSquare() {
		return this.square;
	}

	// max_student
	private String maxStudent;
	public void setMaxStudent(String maxStudent) {
		this.maxStudent = maxStudent;
	}
	public String getMaxStudent() {
		return this.maxStudent;
	}

	// max_teacher
	private String maxTeacher;
	public void setMaxTeacher(String maxTeacher) {
		this.maxTeacher = maxTeacher;
	}
	public String getMaxTeacher() {
		return this.maxTeacher;
	}

	// 最多汽车数
	private String maxAutomoblie;
	public void setMaxAutomoblie(String maxAutomoblie) {
		this.maxAutomoblie = maxAutomoblie;
	}
	public String getMaxAutomoblie() {
		return this.maxAutomoblie;
	}

}