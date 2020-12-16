package c.x.platform.admin.feng.edu.edu_class_teacher.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_class_teacher vo类
 */

public class EduClassTeacherCxVo implements Serializable {

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

	// teacher_id
	private String teacherId;
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherId() {
		return this.teacherId;
	}

}