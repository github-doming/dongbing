package c.x.platform.admin.feng.edu.edu_school_class.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_school_class vo类
 */

public class EduSchoolClassCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// school_id
	private String schoolId;
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolId() {
		return this.schoolId;
	}

	// class_id
	private String classId;
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassId() {
		return this.classId;
	}

}