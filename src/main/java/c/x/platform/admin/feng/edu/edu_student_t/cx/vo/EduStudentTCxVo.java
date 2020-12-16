package c.x.platform.admin.feng.edu.edu_student_t.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_student_t vo类
 */

public class EduStudentTCxVo implements Serializable {

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

}