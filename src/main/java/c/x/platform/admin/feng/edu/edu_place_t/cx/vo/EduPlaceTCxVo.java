package c.x.platform.admin.feng.edu.edu_place_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_place_t vo类
 */

public class EduPlaceTCxVo implements Serializable {

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

	// description
	private String description;
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
	}

	// 学习周期描述
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

	// max_automoblie
	private String maxAutomoblie;
	public void setMaxAutomoblie(String maxAutomoblie) {
		this.maxAutomoblie = maxAutomoblie;
	}
	public String getMaxAutomoblie() {
		return this.maxAutomoblie;
	}

	// 学习周期类型
	private String timeType;
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getTimeType() {
		return this.timeType;
	}

}