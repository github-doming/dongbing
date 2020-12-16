package c.x.platform.admin.feng.edu.edu_place_teacher.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_place_teacher vo类
 */

public class EduPlaceTeacherCxVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}

	// place_id
	private String placeId;
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getPlaceId() {
		return this.placeId;
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