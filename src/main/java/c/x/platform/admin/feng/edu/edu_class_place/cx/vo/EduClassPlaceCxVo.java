package c.x.platform.admin.feng.edu.edu_class_place.cx.vo;

import java.io.Serializable;
/**
 * The vo class for the database table edu_class_place vo类
 */

public class EduClassPlaceCxVo implements Serializable {

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

	// place_id
	private String placeId;
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getPlaceId() {
		return this.placeId;
	}

}