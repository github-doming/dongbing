package c.x.platform.admin.feng.edu.edu_class_student.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_class_student vo类
 */

public class EduClassStudentCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// class_id
	private String classId;
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassId() {
		return this.classId;
	}

	// student_id
	private String studentId;
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentId() {
		return this.studentId;
	}

}